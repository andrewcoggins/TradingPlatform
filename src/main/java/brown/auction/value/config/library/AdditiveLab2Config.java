package brown.auction.value.config.library; 

import java.util.Set;

import brown.auction.prevstate.library.BlankStateInfo;
import brown.auction.prevstate.library.PrevStateInfo;
import brown.auction.value.distribution.library.AdditiveValuationDistribution;
import brown.auction.value.generator.library.LabTwoValGenerator;
import brown.auction.value.valuation.library.ValuationType;
import brown.mechanism.tradeable.ITradeable;

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
    return new BlankStateInfo();
  }

}