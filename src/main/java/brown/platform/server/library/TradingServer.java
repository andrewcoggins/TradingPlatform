package brown.platform.server.library;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import brown.auction.value.config.IValuationConfig;
import brown.auction.value.config.library.SpecValV2Config;
import brown.auction.value.config.library.SpecValV3Config;
import brown.auction.value.config.library.ValConfig;
import brown.auction.value.valuation.IValuation;
import brown.auction.value.valuation.library.SpecValValuation;
import brown.auction.value.valuation.library.ValuationType;
import brown.logging.library.Logging;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.accounting.library.Account;
import brown.platform.accounting.library.AccountManager;
import brown.platform.accounting.library.Order;
import brown.platform.market.library.Market;
import brown.platform.market.library.MarketManager;
import brown.platform.messages.library.AccountResetMessage;
import brown.platform.messages.library.BankUpdateMessage;
import brown.platform.messages.library.ErrorMessage;
import brown.platform.messages.library.GameReportMessage;
import brown.platform.messages.library.PrivateInformationMessage;
import brown.platform.messages.library.RegistrationMessage;
import brown.platform.messages.library.TradeMessage;
import brown.platform.messages.library.TradeRequestMessage;
import brown.platform.messages.library.ValuationInformationMessage;
import brown.platform.summary.library.AuctionSummarizer;
import brown.system.server.library.KryoServer;
import brown.system.setup.ISetup;

/**
 * The core server for the Trading Platform. Integrates all parts of the platform- 
 * manages server-side agent information including accounts and valuations,
 * mediates message passing between agents and markets, controls the dymanics of 
 * markets within a simulation, collects and summarizes information about 
 * agent accounts and utility at the end of simulations. 
 * 
 * @author acoggins, kerry, lcamery
 *
 */
public class TradingServer extends KryoServer {
  
  // Server stuff
  protected Server theServer;

  //keeps track of all tradeables
  protected List<ITradeable> allTradeables; 
  
  // Fields to keep track of agents
  private int agentCount;  
  protected Map<Integer, Integer> privateToPublic;
  protected Map<Integer, String> IDToName;
  protected Map<Integer, IValuation> privateValuations; 
  protected AccountManager acctManager;
  protected AuctionSummarizer summarizer; 

  // Track Markets / market config stuff
  protected MarketManager manager;
  protected ValConfig valueConfig; 
  protected List<ITradeable> initialGoods;
  protected Double initialMonies;  


  /**
   * Constructor for the server. AbsServer is a kryo server so it is initialized
   * with a port and a setup.
   * @param port the port of the server. 
   * @param gameSetup the setup of the server that registers relevant classes.
   */
  public TradingServer(int port, ISetup gameSetup) {
    super(port, gameSetup); 
    this.agentCount = 0;
    this.privateToPublic = new ConcurrentHashMap<Integer, Integer>();
    this.IDToName = new ConcurrentHashMap<Integer,String>();
    this.privateValuations = new HashMap<Integer, IValuation>();
    this.acctManager = new AccountManager();
    this.manager = new MarketManager();

    final TradingServer aServer = this;
    theServer.addListener(new Listener() {
      public void received(Connection connection, Object message) {
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


  /**
   * Method run when an agent registers with the server. Gives the agent a private ID and 
   * tracks the agent in the server.
   * @param connection a kryo connection
   * @param registration a registration message received from an agent.
   */
  private void onRegistration(Connection connection, RegistrationMessage registration) {
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
      if (registration.name != null) {
          this.IDToName.put(theID, registration.name);
      }
      Logging.log("[-] registered " + theID);
      connection.sendTCP(15000);
      connection.setTimeout(60000);
      this.theServer.sendToTCP(connection.getID(), new RegistrationMessage(theID));
    } else {
      Logging.log("[x] AbsServer-onRegistration: encountered registration from existing agent");
    }
  }
  
  /**
   *  Give agents valuations and initial goods and sends the agents private information.
   *  This depends on the type of auction being run.
   *  
   */
  protected void initializeAgents() {
    ValConfig marketConfig = this.valueConfig;
    Map<Integer,PrivateInformationMessage> toSend = new HashMap<Integer,PrivateInformationMessage>();    
    if (marketConfig.type == ValuationType.Game) {
      IValuationConfig gconfig = (IValuationConfig) marketConfig;
      List<Integer> agents = new ArrayList<Integer>(this.connections.values());
      
      // flip the true coin, and pass this information to the manager
      gconfig.initialize(agents);
      this.manager.initializeInfo(((ValConfig) gconfig).generateInfo());
      
      // make valuations all at once            
      toSend = gconfig.generateReport(agents);
     } else if (marketConfig.type == ValuationType.Auction) { 
       this.manager.initializeInfo(marketConfig.generateInfo());
       for (Connection connection : this.connections.keySet()) {
         // make valuations one by one
         IValuation privateValuation = marketConfig.valueDistribution.sample();
         toSend.put(this.connections.get(connection), 
             new ValuationInformationMessage(this.connections.get(connection), this.allTradeables, privateValuation.safeCopy(), marketConfig.valueDistribution));                  
         //give the server private valuation info.
         this.privateValuations.put(this.connections.get(connection), privateValuation.safeCopy());         
       }
     } else if (marketConfig.type == ValuationType.Spectrum) { 
       SpecValV2Config svconfig = (SpecValV2Config) marketConfig;
       List<Integer> agents = new ArrayList<Integer>(this.connections.values());
       
       // Initialize the model
       svconfig.initialize(agents);
       // Set the information so market has valuations
       this.manager.initializeInfo(svconfig.generateInfo());
       
       for (Integer agent: svconfig.agentToValue.keySet()){
         this.privateValuations.put(agent,new SpecValValuation(svconfig.agentToValue.get(agent)));
       }
         
       // Generate initial reports
       toSend = svconfig.generateReport(agents);              
    } else if (marketConfig.type == ValuationType.Spectrum_v2) {
      SpecValV3Config svconfig = (SpecValV3Config) marketConfig;
      List<Integer> agents = new ArrayList<Integer>(this.connections.values());
      
      // Initialize the model
      svconfig.initialize(agents);

      for (Integer agent: svconfig.agentToValue.keySet()){
        this.privateValuations.put(agent,new SpecValValuation(svconfig.agentToValue.get(agent)));
      }
      
      // Generate initial reports
      toSend = svconfig.generateReport(agents);     
    }
    for (Connection connection : this.connections.keySet()) {
      Integer agentID = this.connections.get(connection);               
      //set up agent account
      Account newAccount = new Account(agentID);
      newAccount.add(this.initialMonies);
      for (ITradeable t : this.initialGoods)
        newAccount.add(0.0, t);
      this.acctManager.setAccount(agentID, newAccount);
           
      theServer.sendToTCP(connection.getID(), new AccountResetMessage(agentID,this.initialGoods,this.initialMonies));      
      
      // send out the valuations
      if (marketConfig.type != ValuationType.Blank){
        theServer.sendToTCP(connection.getID(),toSend.get(agentID));
      }
    }
  }

  /*
   * This will handle what happens when an agent sends in a bid in response to
   * a BidRequest for an auction
   */
  private void onBid(Connection connection, Integer privateID, TradeMessage bid) {
    if (this.manager.MarketOpen(bid.AuctionID)) {
      Market auction = this.manager.getMarket(bid.AuctionID);
        synchronized (auction) {
          // Handle bid through handleBid method
          if (!auction.handleBid(bid.safeCopy(privateID))) {
            this.theServer.sendToTCP(connection.getID(), new ErrorMessage(privateID, "Bid rejected by Activity Rule"));
          }
        }
      }
      else {
       
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
  private void sendBankUpdate(Order anOrder, boolean to) {
    BankUpdateMessage bu;
    if (to) {
      // agent is receiving a good and losing money.
      bu = new BankUpdateMessage(anOrder.TO, anOrder.GOOD, null, -1 * anOrder.PRICE, anOrder.QUANTITY);
      theServer.sendToTCP(this.privateToConnection(anOrder.TO).getID(), bu);
    } else {
      // agent is losing a good and receiving money.
      bu = new BankUpdateMessage(anOrder.FROM, null, anOrder.GOOD, anOrder.PRICE, anOrder.QUANTITY);
      theServer.sendToTCP(this.privateToConnection(anOrder.FROM).getID(), bu);
    }
  }
  
  /**
   * Sends a auction update to every agent or closes out any finished
   * auctions about the state of all the public auctions
   */
  private void updateAllAuctions() {
    synchronized (this.manager) {
      for (Market auction : this.manager.getAuctions()) {
        synchronized (auction) { 
          if (!auction.isInnerOver()) {
            // indicates that the auction has incremented
            auction.tick();
            // sets reserve/round price.
            auction.setReserves();            
            for (Entry<Connection, Integer> id : this.connections.entrySet()) {
              // maybe send message here? sanitized ledger.
              TradeRequestMessage tr = auction.constructTradeRequest(id.getValue());
              tr = tr.sanitize(id.getValue(),this.privateToPublic);
              this.theServer.sendToTCP(id.getKey().getID(), tr);
            }
          } else {
            List<Order> winners = auction.constructOrders();
            // Go through winners and execute orders
            for (Order winner : winners) {                      
              if (this.acctManager.containsAcct(winner.TO)) {
                Account accountTo = this.acctManager.getAccount(winner.TO);
                synchronized (accountTo.ID) {                  
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
            Map<Integer, List<GameReportMessage>> reports = auction.constructReport();
            for (Integer agent : reports.keySet()) {  
              for (GameReportMessage report : reports.get(agent)) {
                this.theServer.sendToTCP(this.privateToConnection(agent).getID(), report.sanitize(agent,this.privateToPublic));                
              }
            }
            // record
            try {
              auction.record(this.privateValuations);
            } catch (IOException e) {
              Logging.log("IOException in record method");
            }
            auction.close();
          }
        }
      }
    }
  }  
  
  /**
   * Completes all auctions within a simulation by updating all auctions until no 
   * more markets are open
   * @param lag a lagtime between auctions, provided by server-side user.
   * @throws InterruptedException 
   */
  protected synchronized void completeAuctions(int lag) throws InterruptedException { 
    //run every outer cycle of the auction until it is terminated per the outer termination condition.
    while (this.manager.anyMarketsOpen()) {
      Thread.sleep(lag);
      this.updateAllAuctions();
      Thread.sleep(lag);      
    }
    this.manager.updateAllInfo();    
  }
  
  /**
   * Resets a simulation. This occurs between simulations, when multiple 
   * simulations are being run.
   */
  protected void resetSim() { 
    this.acctManager.reset();
    this.manager.reset();
  }
  
  /**
   * Prints the utilities of agents, and how they placed in auction at the 
   * end of all simulation.
   * provides an option to print utilities to an output file.
   * @param outputFile the name of the file to which output is written.
   */
  protected void printUtilities(String outputFile) {
    Map<Integer,Double> toPrint = new HashMap<Integer,Double>();
    if (this.valueConfig.type == ValuationType.Auction) {
      // do something
      Map<Integer, Double> totalUtil = this.summarizer.getTotalUtility();
      for (Entry<Integer, Double> util : totalUtil.entrySet()) {
        toPrint.put(util.getKey(), util.getValue());
//        Logging.log("Agent " + this.privateToPublic.get(util.getKey()) + " got " + util.getValue() + " total utility");
      }
    } else if (this.valueConfig.type == ValuationType.Game) {
      Map<Integer, Double> totalUtil = this.summarizer.getTotalUtility();
      for (Entry<Integer, Double> util : totalUtil.entrySet()) {
        toPrint.put(util.getKey(), util.getValue());
//        Logging.log("Agent " + this.privateToPublic.get(util.getKey()) + " got " + util.getValue() + " total utility");
      }      
    } else if (this.valueConfig.type == ValuationType.Blank){
      // for lemonade game right now
      for (Integer agent: this.connections.values()) {
        toPrint.put(agent,this.acctManager.getAccount(agent).getMonies());
      }   
    } else if (this.valueConfig.type == ValuationType.Spectrum) {
      Map<Integer, Double> totalUtil = this.summarizer.getTotalUtility();
      for (Entry<Integer, Double> util : totalUtil.entrySet()) {
        toPrint.put(util.getKey(), util.getValue());
      }      
    } else if (this.valueConfig.type == ValuationType.Spectrum_v2){
      Map<Integer, Double> totalUtil = this.summarizer.getTotalUtility();
      for (Entry<Integer, Double> util : totalUtil.entrySet()) {
        toPrint.put(util.getKey(), util.getValue());
      }      
    }
    Logging.log("RESULTS (agent ID -> Utility):");
    
    List<Map.Entry<Integer, Double>> sortedByValue = toPrint.entrySet().stream().sorted(Map.Entry.<Integer, Double>comparingByValue().reversed()).collect(Collectors.toList());    
    int i = 1;
    for (Map.Entry<Integer,Double> a :sortedByValue){
      Logging.log(i + ". " + this.IDToName.getOrDefault(a.getKey(), "") +", ID: " + a.getKey()+ ", Utility: " + a.getValue());
      this.theServer.sendToTCP(this.privateToConnection(a.getKey()).getID(), new ErrorMessage(0, "Placed: " + Integer.toString(i)));
      i++;
    }
    if (outputFile != null) {
      try {
        PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
        writer.write("RESULTS (agent ID -> Utility): \n");
        i = 1;
        for (Map.Entry<Integer,Double> a :sortedByValue){
          writer.write(i + ". " + this.IDToName.getOrDefault(a.getKey(), "") +", ID: " + a.getKey()+ ", Utility: " + a.getValue()+"\n");
          i++;
        }
        writer.close();        
      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        Logging.log("File not Found");
      } catch (UnsupportedEncodingException e) {
        // TODO Auto-generated catch block
        Logging.log("Unsupported Coding Exception");
      }
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
      
}
