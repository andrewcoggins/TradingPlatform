package brown.auction.value.manager.library;


import java.util.Set;

import brown.auction.prevstate.library.BlankStateInfo;
import brown.auction.prevstate.library.PrevStateInfo;
import brown.auction.value.distribution.library.XORValuationDistribution;
import brown.auction.value.generator.library.UniformValGenerator;
import brown.auction.value.valuation.library.ValuationType;
import brown.mechanism.tradeable.ITradeable;

/**
 * Val Config for complex Valuations. Sends agents valuations of every possible bundle
 * of goods they are bidding on. 
 * Experimental. not recommended for large numbers of goods.
 * @author andrew
 *
 */
public class ComplexValConfig extends ValuationManager {

  public ComplexValConfig(Set<ITradeable> allGoods) {
    super(new XORValuationDistribution(new UniformValGenerator(), allGoods), ValuationType.Auction);
  }
  
  @Override
  public PrevStateInfo generateInfo() {
    return new BlankStateInfo();
  }


  
}