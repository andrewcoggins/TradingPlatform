package brown.server;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import brown.accounting.library.Account;
import brown.accounting.library.AccountManager;
import brown.accounting.library.Ledger;
import brown.market.library.Market;
import brown.market.library.MarketManager;
import brown.market.marketstate.library.Order;
import brown.messages.library.AccountResetMessage;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.ErrorMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.PrivateInformationMessage;
import brown.messages.library.RegistrationMessage;
import brown.messages.library.TradeMessage;
import brown.messages.library.TradeRequestMessage;
import brown.messages.library.ValuationInformationMessage;
import brown.setup.ISetup;
import brown.setup.Logging;
import brown.setup.library.Startup;
import brown.summary.AuctionSummarizer;
import brown.summary.ISimulationSummarizer;
import brown.tradeable.ITradeable;
import brown.value.config.ValConfig;
import brown.value.valuation.IValuation;
import brown.value.valuation.ValuationType;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public abstract class AbsServer {
  
  // Server stuff
  private final int PORT;
  protected Server theServer;

  //keeps track of all tradeables
  protected List<ITradeable> allTradeables; 
  
  // Fields to keep track of agents
  private int agentCount;  
  protected Map<Connection, Integer> connections;
  protected Map<Integer, Integer> privateToPublic;
  protected Map<Integer, IValuation> privateValuations; 
  protected AccountManager acctManager;
  protected ISimulationSummarizer summarizer; 

  // Track Markets / market config stuff
  protected MarketManager manager;
  protected ValConfig valueConfig; 
  protected List<ITradeable> initialGoods;
  protected Double initialMonies;  


  public AbsServer(int port, ISetup gameSetup) {
    this.PORT = port;
    this.agentCount = 0;
    this.connections = new ConcurrentHashMap<Connection, Integer>();
    this.privateToPublic = new ConcurrentHashMap<Integer, Integer>();
    this.privateValuations = new ConcurrentHashMap<Integer, IValuation>();
    this.acctManager = new AccountManager();
    this.manager = new MarketManager();

    // Kryo Stuff
    theServer = new Server(8192, 4096);
    theServer.start();    
    Kryo serverKryo = theServer.getKryo();
    Startup.start(serverKryo);
    if (gameSetup != null) {
      gameSetup.setup(serverKryo);
    }
    
    // Set up Server
    try {
      theServer.bind(PORT, PORT);
    } catch (IOException e) {
      Logging.log(e + " [X] Server failed to start due to port conflict");
      return;
    }

    final AbsServer aServer = this;
    theServer.addListener(new Listener() {
      public void received(Connection connection, Object message) {
        System.out.println("something received");
        if (connections.containsKey(connection)) {
          // If the connection is already contained, check if message is a trade
          int id = connections.get(connection);
          if (message instanceof TradeMessage) {
            Logging.log("[-] bid recieved from " + id);
            aServer.onBid(connection, id, (TradeMessage) message);
          }
        } else if (message instanceof RegistrationMessage) {
          // If connection is not contained, check if it is registration method
          Logging.log("[-] registration recieved from "
              + connection.getID());
          aServer.onRegistration(connection, (RegistrationMessage) message);
          return;
        }}});
    Logging.log("[-] server started");
  }


  protected void onRegistration(Connection connection, RegistrationMessage registration) { 
    if (registration.getID() == null) {
      Logging.log("[x] AbsServer-onRegistration: encountered null registration");
      return;
    }
    Integer theID = registration.getID();
    Collection<Integer> allIds = connections.values();
    if (!allIds.contains(theID)) {
      theID = new Integer((int) (Math.random() * 1000000000));
      while (allIds.contains(theID)) {
        theID = new Integer((int) (Math.random() * 1000000000));
      }
      privateToPublic.put(theID, agentCount++);
      connections.put(connection, theID);
      Logging.log("[-] registered " + theID);
      this.theServer.sendToTCP(connection.getID(), new RegistrationMessage(theID));
    } else {
      Logging.log("[x] AbsServer-onRegistration: encountered registration from existing agent");
    }
  }
  
  // Give agents valuations and give them initial goods
  protected void initializeAgents() {
    ValConfig marketConfig = this.valueConfig;
    for (Connection connection : this.connections.keySet()) {      
      Integer agentID = this.connections.get(connection);
      //set up agent account
      Account newAccount = new Account(agentID);
      newAccount.add(this.initialMonies);
      for (ITradeable t : this.initialGoods)
        newAccount.add(0.0, t);
      this.acctManager.setAccount(agentID, newAccount);
      theServer.sendToTCP(connection.getID(), new AccountResetMessage(agentID,this.initialGoods,this.initialMonies));
      
      // send agents private information
      if (marketConfig.type == ValuationType.Auction) {
        PrivateInformationMessage valueReg; 
        IValuation privateValuation = marketConfig.valueDistribution.sample();
        valueReg = new ValuationInformationMessage(agentID, this.allTradeables, privateValuation, marketConfig.valueDistribution);
        theServer.sendToTCP(connection.getID(), valueReg);
        //give the server private valuation info.
        this.privateValuations.put(agentID, privateValuation);
      } else if (marketConfig.type == ValuationType.Game) {
        //no explicit valuation, as in the lemonade game
        // GameInformationMessage or something, not used yet
      }
    }
  }

  /*
   * This will handle what happens when an agent sends in a bid in response to
   * a BidRequest for an auction
   */
  protected void onBid(Connection connection, Integer privateID, TradeMessage bid) {
    System.out.println("received");
    Market auction = this.manager.getMarket(bid.AuctionID);
    if (auction != null) {
      synchronized (auction) {
        // Handle bid through handleBid method
        if (!auction.handleBid(bid.safeCopy(privateID))) {
          this.theServer.sendToTCP(connection.getID(), new ErrorMessage(privateID, "Bid rejected by Activity Rule"));
        }
      }
    } else {
      Logging.log("[x] AbsServer onBid: Bid encountered with unknown auction ID");
      this.theServer.sendToTCP(connection.getID(), new ErrorMessage(privateID, "Bid send to unknown auction"));
    }
  }
  
  /**
   * Singular bank update
   * 
   * @param ID
   * @param oldA
   * @param newA
   */
  public void sendBankUpdate(Order anOrder, boolean to) {
    BankUpdateMessage bu;
    if (to) {
      // agent is receiving a good and losing money.
      bu = new BankUpdateMessage(anOrder.TO, anOrder.GOOD, null, -1 * anOrder.PRICE);
      theServer.sendToTCP(this.privateToConnection(anOrder.TO).getID(), bu);
    } else {
      // agent is losing a good and receiving money.
      bu = new BankUpdateMessage(anOrder.FROM, null, anOrder.GOOD, anOrder.PRICE);
      theServer.sendToTCP(this.privateToConnection(anOrder.FROM).getID(), bu);
    }
  }
  
  /**
   * Sends a auction update to every agent or closes out any finished
   * auctions about the state of all the public auctions
   */
  public void updateAllAuctions() {
    synchronized (this.manager) {;
      for (Market auction : this.manager.getAuctions()) {
        synchronized (auction) {          
          auction.tick();
          if (!auction.isInnerOver()) {
            for (Entry<Connection, Integer> id : this.connections.entrySet()) {
              // maybe send message here? sanitized ledger.
              TradeRequestMessage tr = auction.constructTradeRequest(id.getValue());
              this.theServer.sendToUDP(id.getKey().getID(), tr);
            }
          } else {
            List<Order> winners = auction.constructOrders();
            Ledger ledger = this.manager.getLedger(auction.getID());
            // Go through winners and execute orders
            for (Order winner : winners) {                      
              if (this.acctManager.containsAcct(winner.TO)) {
                Account accountTo = this.acctManager.getAccount(winner.TO);
                synchronized (accountTo.ID) {                  
                  // add order to ledger
                  ledger.add(winner.toTransaction());  
                  // new account
                  accountTo.add(-1 * winner.PRICE, winner.GOOD);
                  this.acctManager.setAccount(winner.TO, accountTo);
                  this.sendBankUpdate(winner, true);
                }
              }
              if (winner.FROM != null && this.acctManager.containsAcct(winner.FROM)) {
                Account accountFrom = this.acctManager.getAccount(winner.FROM);
                synchronized (accountFrom.ID) {   
                  // new account
                  accountFrom.remove(-1 * winner.PRICE, winner.GOOD);
                  this.acctManager.setAccount(winner.FROM, accountFrom);
                  this.sendBankUpdate(winner, false);
                }
              }
            }            
            // Send game report
            Map<Integer,GameReportMessage> reports = auction.constructReport();
            for (Integer agent : reports.keySet()) {      
              this.theServer.sendToTCP(this.privateToConnection(agent).getID(), reports.get(agent).sanitize(agent,this.privateToPublic));
            }
            if (!auction.isOverOuter()) {
              Logging.log("[*] Auction has been reset");
              auction.resetInnerMarket();              
            } else {
              this.manager.update(auction.getID());
              this.manager.close(auction.getID());
            }
          }
        }
      }
    }
  }  
  
  public synchronized void completeAuctions(int lag) throws InterruptedException { 
    //run every outer cycle of the auction until it is terminated per the outer termination condition.
    while (this.manager.anyMarketsOpen()) {
      Thread.sleep(lag);
      this.updateAllAuctions();
      Thread.sleep(lag);      
    }
    
  }
  
  public void resetSim() {
    this.acctManager.reset();
    this.manager.reset();
  }
  
  public void printUtilities() {
    Map<Integer,Double> toPrint = new HashMap<Integer,Double>();
    if (this.valueConfig.type == ValuationType.Auction) {
      // do something
    } else if (this.valueConfig.type == ValuationType.Game) {
      // for lemonade game right now
      for (Integer agent: this.connections.values()){
        toPrint.put(agent,this.acctManager.getAccount(agent).getMonies());
      }
    }  
    Logging.log("RESULTS (agent ID -> Utility):");
    
    List<Map.Entry<Integer, Double>> sortedByValue = toPrint.entrySet().stream().sorted(Map.Entry.<Integer, Double>comparingByValue().reversed()).collect(Collectors.toList());    
    int i = 1;
    for (Map.Entry<Integer,Double> a :sortedByValue){
      Logging.log(i + ". " + a.getKey()+ ", money: " + a.getValue());
      this.theServer.sendToTCP(this.privateToConnection(a.getKey()).getID(), new ErrorMessage(0, "Placed: " + Integer.toString(i)));
      i++;
    }
  }

  
  /*
   * Retrieves a connection (needed to send a message to a client) from the
   * agent's private ID
   */
  private Connection privateToConnection(Integer id) {
    for (Entry<Connection, Integer> ctp : connections.entrySet()) {
      if (ctp.getValue().intValue() == id.intValue()) {
        return ctp.getKey();
      }
    }
    Logging.log("[x] AbsServer PrivateToConnection: Connection not found");
    return null;
  }
      
  // do something with this later
  public void sendAllMarketUpdates(List<Market> markets) {   
  }
}
