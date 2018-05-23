package brown.rules;

import java.io.IOException;
import java.util.Map;

import brown.market.marketstate.IMarketState;
import brown.value.valuation.IValuation;

/**
 * Dictates how/if record keeping will occur. This may involve 
 * writing some information to an output file. 
 * @author kerry
 *
 */
public interface IRecordKeepingRule {

 /**
  * records some information
  * @param state market internal state
  * @param privateVals private valuations of agents, which may be relevant when recording utility. 
  * @throws IOException 
  */
  void record(IMarketState state, Map<Integer, IValuation> privateVals) throws IOException;
  
}
