package brown.value.config; 

import java.util.Set;

import brown.market.library.PrevStateInfo;
import brown.tradeable.ITradeable;
import brown.value.distribution.library.AdditiveValuationDistribution;
import brown.value.generator.library.LabTwoValGenerator;
import brown.value.valuation.ValuationType;

/**
 * Configuration for simple valuations over distribution used in lab two. 
 * @author acoggins
 */
public class AdditiveLab2Config extends ValConfig {
  
  public AdditiveLab2Config(Set<ITradeable> allGoods) {
    super(new AdditiveValuationDistribution(new LabTwoValGenerator(), allGoods), ValuationType.Auction);
  }
  
  @Override
  public PrevStateInfo generateInfo() {
    return null;
  }

}