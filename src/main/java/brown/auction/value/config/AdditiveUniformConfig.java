package brown.auction.value.config; 

import java.util.Set;

import brown.auction.prevstate.BlankStateInfo;
import brown.auction.prevstate.PrevStateInfo;
import brown.auction.value.distribution.AdditiveValuationDistribution;
import brown.auction.value.generator.UniformValGenerator;
import brown.auction.value.valuation.ValuationType;
import brown.mechanism.tradeable.ITradeable;

/**
 * Configuration for simple valuations over a uniform distribution.
 * @author acoggins
 */
public class AdditiveUniformConfig extends ValConfig {
  
  public AdditiveUniformConfig(Set<ITradeable> allGoods) {
    super(new AdditiveValuationDistribution(new UniformValGenerator(), allGoods), ValuationType.Auction);
  }
  
  @Override
  public PrevStateInfo generateInfo() {
    return new BlankStateInfo();
  }

}