package brown.channels.server;

import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import brown.market.marketstate.library.Order;
import brown.tradeable.library.MultiTradeable;

/**
 * the server sends a Wrapper exposing a market's functionality,
 * and containing information about a market that utilizes the [Mechanism]
 * rule. a TwoSidedAuction is passed into either: onCDA, onCallMarket, 
 * onMarketScoring, or onLMSR
 * @author andrew
 *
 */
public interface TwoSidedAuction extends IServerChannel {
	public MultiTradeable getTradeableType();
	
	public List<Order> buy(Integer agentID, double shareNum, double sharePrice);
	public List<Order> sell(Integer agentID, MultiTradeable opp, double sharePrice);
	public void cancel(Integer agentID, boolean buy, double shareNum, double sharePrice);
	
	public double quoteBid(double shareNum, double sharePrice);
	public double quoteAsk(double shareNum, double sharePrice);
	
	public SortedMap<Double, Set<Order>> getBuyBook();
	public SortedMap<Double, Set<Order>> getSellBook();
	
	public void tick(double time);
}
