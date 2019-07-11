package brown.communication.messages.library;

import brown.auction.value.valuation.IGeneralValuation;
import brown.communication.messages.IValuationMessage;
import brown.user.agent.IAgent;

public class ValuationMessage extends AbsServerToAgentMessage implements IValuationMessage {
  
  private IGeneralValuation valuation; 
  
  public ValuationMessage() {
    super(null, null); 
  }
  
  public ValuationMessage(Integer messageID, Integer agentID, IGeneralValuation valuation) {
    super(messageID, agentID);
    this.valuation = valuation; 
  }
  

  @Override
  public void agentDispatch(IAgent agent) {
    agent.onValuationMessage(this);
  }
  
  
  public IGeneralValuation getValuation() {
    return this.valuation; 
  }

}
