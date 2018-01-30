package brown.market.library;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import brown.accounting.library.Ledger;
import brown.market.IMarketManager;
import brown.market.marketstate.library.MarketState;
import brown.market.preset.AbsMarketPreset;
import brown.tradeable.ITradeable;

/**
 * Market manager stores and handles multiple markets 
 * @author lcamery
 *
 */
public class MarketManager implements IMarketManager {
	private Map<Market, Ledger> ledgers;
	private Map<Integer, Market> markets;
	private PrevStateInfo information;
	
	/**
	 * For Kryo do not use
	 */
	public MarketManager() {
		this.ledgers = new ConcurrentHashMap<Market, Ledger>();
		this.markets = new ConcurrentHashMap<Integer, Market>();	
		this.information = null;
	}
	
	/**
	 * Closes a market and tells it to convert if applicable
	 * @param server
	 * @param ID
	 * @param closingState
	 */
	public void close(Integer ID) {
		this.markets.remove(ID);
	}

	/**
	 * Opens a market
	 * @param market
	 * @return
	 */
	public boolean open(AbsMarketPreset rules, Integer marketID, List<ITradeable> tradeables, List<Integer> agents){
	  Market market = new Market(rules, new MarketState(marketID,tradeables,this.information));
	   if (ledgers.containsKey(market)) {
	      return false;
	    }
	   market.setGroupings(agents);
	   this.ledgers.put(market, new Ledger(market.getID()));
	   this.markets.put(market.getID(), market);
	   return true;
	}


	/**
	 * Gets the ledger for this market ID
	 * @param ID
	 * @return
	 */
	public Ledger getLedger(Integer ID) {
		return ledgers.get(markets.get(ID));
	}

	/**
	 * Gets the market for this ID
	 * @param ID
	 * @return
	 */
	public Market getMarket(Integer ID) {
		return markets.get(ID);
	}

	/**
	 * Gets all of the auctions
	 * @return
	 */
	public Collection<Market> getAuctions() {
		return this.markets.values();
	}

	// update information from a market
  public void update(Integer marketID) {
   this.information.combine(this.markets.get(marketID).constructSummaryState());
  }
  
  public boolean anyMarketsOpen(){
    boolean toReturn = false;
    for (Market m : this.getAuctions()){
      if (!m.isOverOuter()){
        toReturn = true;
      }
    }
    return toReturn;    
  }

  public void reset() {
    this.ledgers.clear();
    this.markets.clear();
    this.information = null;
  }
}
