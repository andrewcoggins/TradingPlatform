package brown.value.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import brown.bid.interim.BidType;
import brown.bidbundle.library.AuctionBidBundle;
import brown.market.library.PrevStateInfo;
import brown.market.library.PriceDiscoveryInfo;
import brown.tradeable.ITradeable;
import brown.value.distribution.library.AdditiveValuationDistribution;
import brown.value.generator.library.UniformValGenerator;
import brown.value.valuation.ValuationType;

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