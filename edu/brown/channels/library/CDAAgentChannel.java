package brown.channels.library;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import brown.agent.CDAAgent;
import brown.agent.Agent;
import brown.assets.accounting.Ledger;
import brown.assets.accounting.Order;
import brown.channels.ITwoSidedPriceSetter;
import brown.messages.markets.MarketOrder;
import brown.valuable.library.Tradeable;

/*
 * Implements IMarket for Continuous Double auctions
 */
public class CDAAgentChannel implements ITwoSidedPriceSetter {
	private final Integer MARKETID;
	private final Tradeable TYPE;
	private final SortedMap<Double, Double> BUYBOOK;
	private final SortedMap<Double, Double> SELLBOOK;
	private final Ledger LEDGER;
	
	public CDAAgentChannel() {
		this.TYPE = null;
		this.MARKETID = null;
		this.BUYBOOK = null;
		this.SELLBOOK = null;
		this.LEDGER = null;
	}
	
	public CDAAgentChannel(CDAServerChannel CDA, Ledger ledger) {
		this.BUYBOOK = new TreeMap<Double, Double>();
		this.SELLBOOK = new TreeMap<Double, Double>();
		this.LEDGER = ledger;
		
		this.MARKETID = CDA.getID();
		this.TYPE = CDA.getTradeableType();
		for (Map.Entry<Double, Set<Order>> entry : CDA.getBuyBook().entrySet()) {
			double count = 0;
			for (Order t : entry.getValue()) {
				count += t.QUANTITY;
			}
			this.BUYBOOK.put(entry.getKey(), count);
		}
		
		for (Map.Entry<Double, Set<Order>> entry : CDA.getSellBook().entrySet()) {
			double count = 0;
			for (Order t : entry.getValue()) {
				count += t.GOOD.getCount();
			}
			this.SELLBOOK.put(entry.getKey(), count);
		}
	}

	@Override
	public Integer getAuctionID() {
		return this.MARKETID;
	}

	@Override
	public Tradeable getTradeableType() {
		return this.TYPE;
	}

	@Override
	public void buy(Agent agent, double shareNum, double sharePrice) {
		agent.CLIENT.sendTCP(new MarketOrder(0,this.MARKETID, shareNum, 0, sharePrice));
	}

	@Override
	public void sell(Agent agent, double shareNum, double sharePrice) {
		agent.CLIENT.sendTCP(new MarketOrder(0, this.MARKETID, 0, shareNum, sharePrice));
	}

	@Override
	public double quoteBid(double shareNum, double sharePrice) {
		//TODO: Fix
		return -1;
	}

	@Override
	public double quoteAsk(double shareNum, double sharePrice) {
		//TODO: Fix
		return -1;
	}

	@Override
	public SortedMap<Double, Double> getBuyBook() {
		return this.BUYBOOK;
	}

	@Override
	public SortedMap<Double, Double> getSellBook() {
		return this.SELLBOOK;
	}

	@Override
	public void cancel(Agent agent, boolean buy, double shareNum, double sharePrice) {
		if (buy) {
			agent.CLIENT.sendTCP(new MarketOrder(0,this.MARKETID, shareNum, 0, sharePrice, true));
		} else {
			agent.CLIENT.sendTCP(new MarketOrder(0, this.MARKETID, 0, shareNum, sharePrice, true));
		}
	}

	//what to do... maybe make dispatch message a higher level method. 
	//no. this must be generic. 
	//two options: 
	//either I implement every one of these methods for a TPClient, or I 
	//make the on... generic. 
	@Override
	public void dispatchMessage(Agent agent) {
		agent.onContinuousDoubleAuction(this);
	}

	@Override
	public Ledger getLedger() {
		return this.LEDGER;
	}

}
