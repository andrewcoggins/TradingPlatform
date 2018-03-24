package brown.rules.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import ch.uzh.ifi.ce.mweiss.specvalopt.vcg.external.domain.AuctionResult;
import brown.bid.interim.BidType;
import brown.bidbundle.BundleType;
import brown.bidbundle.library.AuctionBidBundle;
import brown.market.marketstate.IMarketState;
import brown.market.marketstate.library.Order;
import brown.messages.library.TradeMessage;
import brown.rules.IPaymentRule;
import brown.tradeable.ITradeable;
import brown.tradeable.library.ComplexTradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.tradeable.library.TradeableType;
import brown.value.generator.library.SpecValGenerator;

/**
 * payment rule for VCG auctions. Uses specval SATS solver to find solution.=
 * @author acoggins
 *
 */
public class VCGPayment implements IPaymentRule {

  @Override
  public void setOrders(IMarketState state) {
    // convert all sent bids to vcg solver form
    List<TradeMessage> bids = state.getBids(); 
    Map<String, Map<Set<String>, Double>> vcgFormatted = new HashMap<String, Map<Set<String>, Double>>(); 
    for (TradeMessage trade : bids) {
      if (trade.Bundle.getType() != BundleType.AUCTION) {
        continue; 
      }
      Map<Set<String>, Double> formattedCBid = new HashMap<Set<String>, Double>(); 
      AuctionBidBundle tradeBundle = (AuctionBidBundle) trade.Bundle; 
      Map<ITradeable, BidType> bidMap = tradeBundle.getBids().bids;
      for(Entry<ITradeable, BidType> e : bidMap.entrySet()) {
        if (e.getKey().getType() != TradeableType.Complex){ 
          continue; 
        }
        ComplexTradeable cTrade = (ComplexTradeable) e.getKey();
        Double bidAmt = e.getValue().price;
        // annoying conversion
        List<SimpleTradeable> simples = cTrade.flatten();
        Set<String> formattedTradeables = new HashSet<String>();
        for(SimpleTradeable s : simples) {
          formattedTradeables.add(s.ID.toString()); 
        }
        formattedCBid.put(formattedTradeables, bidAmt);
      }
      vcgFormatted.put(trade.AgentID.toString(), formattedCBid);
    }
    // use vcg solver (specval initialization doesn't really matter)
    SpecValGenerator svg = new SpecValGenerator(bids.size(), 1);
    AuctionResult solvedVCG = svg.runVCGWithGivenBids(vcgFormatted);
    Map<String, Double> vcgPayments = svg.getVcgPaymentSimpleBid(solvedVCG);
    List<Order> payments = new LinkedList<Order>(); 
    Map<Integer, List<ITradeable>> allocation = state.getAllocation();
    for (Entry<String, Double> payment : vcgPayments.entrySet()) {
      Integer agent = Integer.valueOf(payment.getKey());
      for(ITradeable t : allocation.get(agent)) {
        double size = (double) allocation.get(agent).size(); 
        Order anOrder = new Order(agent, null, payment.getValue() / size, 1, t);
        payments.add(anOrder);
      }
    }
    state.setPayments(payments);
  }

  @Override
  public void reset() {
    // noop
  } 
  
}