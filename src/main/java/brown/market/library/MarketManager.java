package brown.market.library;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import brown.accounting.library.Ledger;
import brown.logging.Logging;
import brown.market.IMarketManager;
import brown.market.marketstate.library.MarketState;
import brown.market.preset.AbsMarketPreset;
import brown.server.library.SimulMarkets;
import brown.tradeable.ITradeable;

/**
 * Market manager stores and handles multiple markets 
 * @author lcamery
 *
 */

//TODO: unique IDs for every market ever
//TODO: make sure that if a bid is being sent to a market, that markets exists in the manager. 



public class MarketManager implements IMarketManager {
  // stores all ledgers in a simulation
	private List<Map<Market, Ledger>> ledgers;
	// stores all markets in a simulation
	private List<Map<Integer, Market>> markets;
	private PrevStateInfo information;
	private Integer index; 
	

	public MarketManager() {
		this.ledgers = new LinkedList<Map<Market, Ledger>>();
		this.markets = new LinkedList<Map<Integer, Market>>();	
		this.information = new BlankStateInfo();
		this.index = -1; 
	}

  public void addSimulMarket(SimulMarkets s, List<ITradeable> tradeables, List<Integer> agents) {
	  this.index++;
	  int id = 0; 
	  this.ledgers.add(new ConcurrentHashMap<Market, Ledger>());
	  this.markets.add(new ConcurrentHashMap<Integer, Market>());
	  for (AbsMarketPreset preset : s.getMarkets()) {
	    this.open(preset, id, tradeables, agents);
	    id++;
	  }
	}
	  
	/**
	 * Opens a market
	 * @param market
	 * @return
	 */
	public boolean open(AbsMarketPreset rules, Integer marketID, List<ITradeable> tradeables, List<Integer> agents) {
	  Market market = new Market(rules, new MarketState(marketID,tradeables,this.information));
	  Logging.log("Opening Market, Information: " + this.information);
	   if (ledgers.get(index).containsKey(market)) {
	      return false;
	   }
	   market.setGroupings(agents);
	   this.ledgers.get(index).put(market, new Ledger(market.getID()));
	   this.markets.get(index).put(market.getID(), market);
	   return true;
	}
	
	 /**
   * Closes a market 
   * @param server
   * @param ID
   * @param closingState
   */
  public void close(Integer ID) {
    this.markets.get(index).remove(ID);
  }

	/**
	 * Gets the ledger for this market ID
	 * @param ID
	 * @return
	 */
	public Ledger getLedger(Integer ID) {
		return ledgers.get(index).get(markets.get(index).get(ID));
	}

	/**
	 * Gets the market for this ID
	 * @param ID
	 * @return
	 */
	public Market getMarket(Integer ID) {
	  if (index != -1) {
	    return markets.get(index).get(ID);
	  }
	}

	
	public boolean validMarket() {
	  return false;
	}
	
	/**
	 * Gets all of the auctions
	 * @return
	 */
	public Collection<Market> getAuctions() {
		return this.markets.get(index).values();
	}

	// update information from a market
  public void update(Integer marketID) {
   this.information.combine(this.markets.get(index).get(marketID).constructSummaryState());
   Logging.log("Updating Market, Information: " + this.information.toString());
  }

  public boolean anyMarketsOpen() {
    boolean toReturn = false;
    for (Market m : this.getAuctions()) {
      if (!m.isOverOuter()){
        toReturn = true;
      }
    }
    return toReturn;    
  }

  public void reset() {
    this.ledgers.clear();
    this.markets.clear();
    this.index = -1;
    this.information = null;
  }

  public void initializeInfo(PrevStateInfo info) {
    this.information = info;
  }

  public void updateAllInfo() {
    for (Market market: this.markets.get(index).values()){
      this.information.combine(market.constructSummaryState());
      Logging.log("Updating Market, New Information: " + this.information.toString());      
    }
  }
}
