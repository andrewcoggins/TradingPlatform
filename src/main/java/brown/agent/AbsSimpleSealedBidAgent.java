package brown.agent;

import java.util.List;

import brown.channels.agent.library.SimpleAgentChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.PrivateInformationMessage;
import brown.setup.ISetup;
import brown.tradeable.ITradeable;
import brown.value.distribution.IValuationDistribution;
import brown.value.valuation.IValuation;

/**
 * abstract agent for sealed bid games. 
 * All sealed bid agents will implement this game.
 * @author andrew
 *
 */
public abstract class AbsSimpleSealedBidAgent extends AbsValnAgent implements IAgent {

  protected IValuation privateValuation; 
  protected IValuationDistribution allValuations;
  protected List<ITradeable> allTradeables;
  
  public AbsSimpleSealedBidAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
  }

  /**
   * Provides agent response to sealed-bid auction
   * @param simpleWrapper - simple agent channel
   */
  public abstract void onSimpleSealedBid(SimpleAgentChannel simpleWrapper);

  public void onPrivateInformation(PrivateInformationMessage privateInfo) {
    this.privateValuation = privateInfo.getPrivateValuation(); 
    this.allValuations = privateInfo.getAllValuations();
    this.allTradeables = privateInfo.getTradeables();
  }
}