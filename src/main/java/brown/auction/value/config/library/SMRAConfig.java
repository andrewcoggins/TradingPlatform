package brown.auction.value.config.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import brown.auction.prevstate.library.PrevStateInfo;
import brown.auction.prevstate.library.PriceDiscoveryInfo;
import brown.auction.value.distribution.library.AdditiveValuationDistribution;
import brown.auction.value.generator.library.UniformValGenerator;
import brown.auction.value.valuation.library.ValuationType;
import brown.mechanism.bid.library.BidType;
import brown.mechanism.bidbundle.library.AuctionBidBundle;
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