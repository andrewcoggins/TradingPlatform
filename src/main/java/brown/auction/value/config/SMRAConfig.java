package brown.auction.value.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import brown.auction.prevstate.PrevStateInfo;
import brown.auction.prevstate.PriceDiscoveryInfo;
import brown.auction.value.distribution.AdditiveValuationDistribution;
import brown.auction.value.generator.UniformValGenerator;
import brown.auction.value.valuation.ValuationType;
import brown.mechanism.bid.BidType;
import brown.mechanism.bidbundle.AuctionBidBundle;
import brown.mechanism.tradeable.ITradeable;

public class SMRAConfig extends ValConfig {

  public SMRAConfig(Set<ITradeable> allGoods) {
    super(new AdditiveValuationDistribution(new UniformValGenerator(), allGoods), ValuationType.Auction);
  }
 
  @Override
  public PrevStateInfo generateInfo() {
    // TODO Auto-generated method stub
    Map<ITradeable, Double> init = new HashMap<ITradeable, Double>();
    return new PriceDiscoveryInfo(init);
  }
  
}