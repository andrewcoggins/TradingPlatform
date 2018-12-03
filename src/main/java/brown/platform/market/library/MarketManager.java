package brown.platform.market.library;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import brown.auction.marketstate.library.MarketState;
import brown.logging.library.PlatformLogging;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.accounting.library.Ledger;
import brown.platform.market.IMarketBlock;
import brown.platform.market.IMarketManager;

/**
 * Market manager stores and handles multiple markets 
 * @author acoggins
 *
 */
public class MarketManager implements IMarketManager {
    // stores all ledgers in a simulation
	private Map<Market, Ledger> openLedgers;
	// stores all markets in a simulation
	private Map<Integer, Market> openMarkets;
	private List<IMarketBlock> allMarkets;
	private boolean lock;
	
	/**
	 * Constructor for a market manager initializes ledgers and markets.
	 * ledgers tracks the ledgers for all markets at each time
	 * markets maps each market at each time to a unique id
	 * information is initially a blank prevstateinfo
	 * index is initially set to -1
	 * idCount keeps track of the number of markets in the MarketManager- 
	 * initially, this is 0.
	 */
	public MarketManager() {
		this.allMarkets = new LinkedList<IMarketBlock>();
		this.lock = false;
	}


   public void createSimultaneousMarket(List<AbsMarketRules> s) {
		if (!this.lock) {
			this.allMarkets.add(new SimultaneousMarket(s));
		} else {
			PlatformLogging.log("Creation denied: market manager locked.");
		}
	}
	  
	/**
	 * closes current market, opens a new one.
	 */
	public void openNewMarkets(AbsMarketRules rules, Integer marketID, List<ITradeable> tradeables, List<Integer> agents) {
	  Market market = new Market(rules.copy(), new MarketState(marketID,tradeables, null), new History());
	   this.openMarkets.clear();
	   this.openLedgers.clear();
	   this.openLedgers.put(market, new Ledger(market.getMarketID()));
	   this.openMarkets.put(market.getMarketID(), market);
	}


	@Override
  	public void close(Integer ID) {
  	  Market toClose = this.openMarkets.get(ID);
  	  toClose.close();
  	  this.openMarkets.put(ID, toClose);
  	}

	/**
	 * Gets the ledger for this market ID
	 * @param ID
	 * @return
	 */
  	@Override
	public Ledger getLedger(Integer ID) {
		return openLedgers.get(this.openMarkets.get(ID));
	}

	/**
	 * Gets the market for this ID
	 * @param ID
	 * @return
	 */
  	@Override
	public Market getMarket(Integer ID) {
	    return openMarkets.get(ID);
	}

	public boolean MarketOpen(Integer ID) {
  	if (openMarkets.containsKey(ID)) {
  		return openMarkets.get(ID).isOpen();
  	}
  	return false;
  }
	  
	
	/**
	 * Gets all of the auctions
	 * @return
	 */
  	@Override
	public Collection<Market> getAuctions() {
		return this.openMarkets.values();
	}

  
  	@Override
  	public boolean anyMarketsOpen() {
  	  boolean toReturn = false;
  	  for (Market m : this.getAuctions()) {
  	    if (!m.isOver()) {
  	      toReturn = true;
  	      break;
  	    }
  	  }
  	  return toReturn;
  	}

  	@Override
  	public void reset() {
  	  this.openLedgers.clear();
  	  this.openMarkets.clear();
  	  this.allMarkets.clear();
  	  this.lock = false;
  	}


  	public void lock() {
  	this.lock = true;
  }
}
