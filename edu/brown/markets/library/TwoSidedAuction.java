package brown.markets.library;

import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import brown.assets.accounting.Order;
import brown.markets.IMarketServer;
import brown.tradeables.Asset;
import brown.valuable.library.Tradeable;

/**
 * the server sends a Wrapper exposing a market’s functionality,
 * and containing information about a market that utilizes the [Mechanism]
 * rule. a TwoSidedAuction is passed into either: onCDA, onCallMarket, 
 * onMarketScoring, or onLMSR
 * @author andrew
 *
 */
public interface TwoSidedAuction extends IMarketServer {
	public Tradeable getTradeableType();
	
	public List<Order> buy(Integer agentID, double shareNum, double sharePrice);
	public List<Order> sell(Integer agentID, Asset opp, double sharePrice);
	public void cancel(Integer agentID, boolean buy, double shareNum, double sharePrice);
	
	public double quoteBid(double shareNum, double sharePrice);
	public double quoteAsk(double shareNum, double sharePrice);
	
	public SortedMap<Double, Set<Order>> getBuyBook();
	public SortedMap<Double, Set<Order>> getSellBook();
	
	public void tick(double time);
}
