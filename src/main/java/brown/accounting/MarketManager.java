package brown.accounting;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import brown.channels.server.TwoSidedAuction;
import brown.market.library.Market;
import brown.server.AbsServer;
import brown.setup.Logging;
import brown.todeprecate.StateOfTheWorld;
import brown.tradeable.library.Tradeable;

/**
 * Market manager stores and handles multiple markets 
 * @author lcamery
 *
 */
public class MarketManager {
	private Map<Market, Ledger> ledgers;
	private Map<Integer, Market> tsauctions;
	
	private Map<Integer, TwoSidedAuction> twosided;

	/**
	 * for Kryo do not use.
	 */
	public MarketManager() {
		this.ledgers = new ConcurrentHashMap<Market, Ledger>();
		this.tsauctions = new ConcurrentHashMap<Integer, Market>();
		
		this.twosided = new ConcurrentHashMap<Integer,TwoSidedAuction>();
	}
	
	/**
	 * Process each market.
	 * @param server: a server
	 * @param market: market to be processed
	 * @param ledger: a ledger for the market.
	 * @param t: transaction to be processed in the markets.
	 * @param toReplace: account to replace. 
	 */
	//NOTE: changed from assets to tradeables, now use TO field of transaction.
	private void process(AbsServer server, Market market, Ledger ledger, 
			Transaction t, Account toReplace) {
		synchronized (t.TO) {
			Account acct = server.privateToAccount(t
					.TO);
			if (acct == null) {
				Logging.log("[X] agent without account "
						+ t.TO);
				return;
			}

			acct.remove(0, t.TRADEABLE);
			//possibly change this later
			//server.setAccount(t.TO, newAccount);
			if (toReplace == null) {
				server.sendBankUpdate(t.TO, acct,
						acct);
			}
		}

		if (toReplace != null) {
			Integer toReplaceID = toReplace.ID;
			if (toReplaceID == null) {
				toReplaceID = t.TO;
			}

			synchronized (toReplaceID) {
				Account oldAccount = server
						.privateToAccount(toReplaceID);
				if (oldAccount == null) {
					Logging.log("[X] agent without account "
							+ toReplaceID);
					return;
				}

				oldAccount.add(toReplace.getMonies(), toReplace.getGoods());
				server.setAccount(toReplaceID, oldAccount);
				server.sendBankUpdate(toReplaceID, oldAccount,
						oldAccount);
			}
		}
	}

	/**
	 * Closes a market TODO: Close pair markets together i.e. PMs
	 * 
	 * @param server
	 * @param market
	 * @param closingState
	 */
	//no.
//	public void convert(AbsServer server, Market market,
//			StateOfTheWorld closingState) {
//		synchronized (market) {
//			Ledger ledger = this.ledgers.get(market);
//			synchronized (ledger) {
//				for (Transaction t : ledger.getLatest()) {
//					List<Account> allReplacements = t.TRADEABLE.convert(closingState);
//					if (allReplacements != null) {
//						for (Account toReplace : allReplacements) {
//							this.process(server, market, ledger, t, toReplace);
//						}
//					}
//				}
//
//				ledgers.remove(market);
//				tsauctions.remove(market.getID());
//			}
//		}
//	}

	/**
	 * Closes a market and tells it to convert if applicable
	 * @param server
	 * @param ID
	 * @param closingState
	 */
	public void close(AbsServer server, Integer ID, StateOfTheWorld closingState) {
		Market market = tsauctions.get(ID);
		//TODO: market.close();
		//convert(server, market, closingState);
	}

	/**
	 * Opens a market
	 * @param market
	 * @return
	 */
	public boolean open(Market market) {
		if (ledgers.containsKey(market)) {
			return false;
		}
		this.ledgers.put(market, new Ledger(market.getID()));
		this.tsauctions.put(market.getID(), market);
		return true;
	}
	
	/**
	 * Registers a security created outside the market
	 * @param ID
	 * @param t
	 * @return
	 */
	public boolean register(Integer ID, Tradeable t) {
		Market tsa = tsauctions.get(ID);
		if (tsa == null) {
			return false;
		}
		synchronized(tsa) {
			//if (!tsa.getType().equals(t.getType())) {
			//	return false;
			//}
			
			Ledger ledger = this.ledgers.get(tsa);
			synchronized (ledger) {
				ledger.add(new Transaction(null, null, 0,0,t));
			}
		}
	
		return true;
	}

	/**
	 * Gets the ledger for this market ID
	 * @param ID
	 * @return
	 */
	public Ledger getLedger(Integer ID) {
		return ledgers.get(tsauctions.get(ID));
	}

	/**
	 * Gets the market for this ID
	 * @param ID
	 * @return
	 */
	public Market getIMarket(Integer ID) {
		return tsauctions.get(ID);
	}

	/**
	 * Gets all of the auctions
	 * @return
	 */
	public Collection<Market> getAuctions() {
		return this.tsauctions.values();
	}

	public TwoSidedAuction getTwoSided(Integer marketID) {
		return this.twosided.get(marketID);
	}

	public void openTwoSided(TwoSidedAuction tsa) {
		this.twosided.put(tsa.getID(), tsa);
	}


}
