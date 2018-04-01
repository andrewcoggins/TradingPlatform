package brown.channels.library; 

import java.util.Map;

import brown.agent.AbsAgent;
import brown.agent.AbsOpenOutcryAgent;
import brown.bidbundle.IBidBundle;
import brown.channels.IAgentChannel;

public class OpenOutcryChannel extends AbsChannel implements IAgentChannel {

  private IBidBundle reserve; 

  /**
   * for kryo do not use
   */
  public OpenOutcryChannel() {
    super(); 
  }
  
  /**
   * Constructor
   */
  public OpenOutcryChannel(Integer ID, IBidBundle reserve) {
    super(ID); 
    this.reserve = reserve; 
  }
  
  @Override
  public void dispatchMessage(AbsAgent agent) {
    if (agent instanceof AbsOpenOutcryAgent) {
      AbsOpenOutcryAgent openOutcryAgent = (AbsOpenOutcryAgent) agent; 
      openOutcryAgent.onOpenOutcry(this); 
    }
  }

  @Override
  public void bid(AbsAgent agent, IBidBundle bid) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String toString() {
    return "OpenOutcryChannel [reserve=" + reserve + "]";
  }

  @Override
  public IAgentChannel sanitize(Integer agent, Map<Integer, Integer> privateToPublic) {
    return this;
  }
  
}