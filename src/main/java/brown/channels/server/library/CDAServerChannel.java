package brown.channels.server.library;

import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import brown.accounting.Ledger;
import brown.channels.MechanismType;
import brown.channels.agent.library.CDAAgentChannel;
import brown.channels.server.TwoSidedAuction;
import brown.market.marketstate.library.Order;
import brown.rules.clearingrules.IClearingRule;
import brown.tradeable.library.Tradeable;
import brown.twosided.ITwoSidedAuction;

public class CDAServerChannel implements TwoSidedAuction {
	private final Integer ID;
	private final Tradeable TYPE;
	private final IClearingRule RULE;
	
	/**
	 * For kryonet
	 * DO NOT USE
	 */
	public CDAServerChannel() {
		this.ID = null;
		this.TYPE = null;
		this.RULE = null;
	}
	
	/**
	 * Constructor
	 * @param ID : auction ID
	 * @param type : SecurityType
	 * @param rule : ClearingRule
	 */
	public CDAServerChannel(Integer ID, Tradeable type, IClearingRule rule) {
		this.ID = ID;
		this.TYPE = type;
		this.RULE = rule;
	}

	@Override
	public Integer getID() {
		return this.ID;
	}

	@Override
	public Tradeable getTradeableType() {
		return this.TYPE;
	}

	@Override
	public List<Order> buy(Integer agentID, double shareNum, double sharePrice) {
		//return this.RULE.buy(agentID, shareNum, sharePrice);
	  return null; 
	}

	@Override
	public List<Order> sell(Integer agentID, Tradeable opp, double sharePrice) {
		//return this.RULE.sell(agentID, opp, sharePrice);
	  return null;
	}

	@Override
	public double quoteBid(double shareNum, double sharePrice) {
		//return this.RULE.quoteBid(shareNum, sharePrice);
	  return -1.0; 
	}

	@Override
	public double quoteAsk(double shareNum, double sharePrice) {
		//return this.RULE.quoteAsk(shareNum, sharePrice);
	  return -1.0; 
	}

	@Override
	public boolean isClosed() {
		return false;
	}

	@Override
	public MechanismType getMechanismType() {
		return MechanismType.CDA;
	}

	@Override
	public SortedMap<Double, Set<Order>> getBuyBook() {
		//return this.RULE.getBuyBook();
	  return null;
	}

	@Override
	public SortedMap<Double, Set<Order>> getSellBook() {
		//return this.RULE.getSellBook();
	  return null; 
	}

	@Override
	public ITwoSidedAuction wrap(Ledger ledger) {
		return new CDAAgentChannel(this, ledger);
	}

	@Override
	public void tick(double time) {
		// Noop
	}

	@Override
	public boolean permitShort() {
		//return this.RULE.isShort();
	  return false; 
	}

	@Override
	public void cancel(Integer agentID, boolean buy, double shareNum,
			double sharePrice) {
		//this.RULE.cancel(agentID, buy, shareNum, sharePrice);		
	}
}
