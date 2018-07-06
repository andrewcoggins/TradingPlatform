package brown.auction.value.config.library; 

import java.util.Set;

import brown.auction.prevstate.library.BlankStateInfo;
import brown.auction.prevstate.library.PrevStateInfo;
import brown.auction.value.distribution.library.AdditiveValuationDistribution;
import brown.auction.value.generator.library.UniformValGenerator;
import brown.auction.value.valuation.library.ValuationType;
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