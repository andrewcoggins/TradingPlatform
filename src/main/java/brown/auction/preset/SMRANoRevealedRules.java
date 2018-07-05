package brown.auction.preset;

import java.util.Map;

import brown.auction.rules.library.AscendingActivity;
import brown.auction.rules.library.NoAllocation;
import brown.auction.rules.library.NoPayment;
import brown.auction.rules.library.NoRecordKeeping;
import brown.auction.rules.library.OneGrouping;
import brown.auction.rules.library.RevealedPreferenceActivity;
import brown.auction.rules.library.SMRAPolicy;
import brown.auction.rules.library.SMRAQuery;
import brown.auction.rules.library.SomeBidsTermination;
import brown.auction.rules.library.XRoundTermination;
import brown.mechanism.tradeable.ITradeable;

/**
 * rules for an SMRA price discovery auction, where no allocations or payments are made.  
 * @author acoggins
 *
 */
public class SMRANoRevealedRules extends AbsMarketPreset {

  private Map<ITradeable, Double> base; 
  private Map<ITradeable, Double> increment; 
  
  public SMRANoRevealedRules(Map<ITradeable, Double> base, Map<ITradeable, Double> increment) {
    super(new NoAllocation(), 
        new NoPayment(), 
        new SMRAQuery(), 
        new OneGrouping(),
        new AscendingActivity(base, increment), 
        new SMRAPolicy(), 
        new SomeBidsTermination(),
        new XRoundTermination(1), 
        new NoRecordKeeping());
    this.base = base; 
    this.increment = increment; 
  }

  @Override
  public AbsMarketPreset copy() {
    return new SMRANoRevealedRules(this.base, this.increment);
  } 
   
}