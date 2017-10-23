package brown.agent;

import java.util.Map;
import java.util.Set;

import brown.exceptions.AgentCreationException;
import brown.valuable.library.Tradeable;
import brown.valuable.library.Value;
import brown.valuation.IValuation;


/**
 * an agent that doesn't operate over a network.
 * 
 * @author lcamery
 */
@SuppressWarnings("unused")
public abstract class TestAgent {
  
  public TestAgent() throws AgentCreationException {
    // TODO Auto-generated constructor stub
  }
 
  /**
   * for submitting a simple bid to a sealed auction in a local,
   *  controlled environment
   * @return
   */
  public abstract Map<Tradeable, Double> onTestSS(IValuation valuation);
  
}