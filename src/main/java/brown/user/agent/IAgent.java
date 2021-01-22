package brown.user.agent;

import brown.communication.messages.IAgentToServerMessage;
import brown.communication.messages.IBankUpdateMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;

/**
 * IAgent implement's the agent's trading logic. 
 * @author andrewcoggins
 *
 */
public interface IAgent {
  
  public void addAgentBackend(IAgentBackend backend); 
  
  /**
   * Respond to IBankUpdateMessage
   * @param bankUpdate
   */
  public void onBankUpdate(IBankUpdateMessage bankUpdate);

  /**
   * Respond to IInformationMessage
   * @param bankUpdate
   */
  public void onInformationMessage(IInformationMessage informationMessage); 
  
  /**
   * Respond to ITradeRequestMessage
   * @param bankUpdate
   */
  public void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage); 
  
  /**
   * Respond to IValuationMessage
   * @param bankUpdate
   */
  public void onValuationMessage(IValuationMessage valuationMessage); 

  /**
   * Respond to ISimulationReportMessage
   * @param bankUpdate
   */
  public void onSimulationReportMessage(ISimulationReportMessage reportMessage); 
  
  /**
   * Get Agent's Name
   * @return
   */
  public String getAgentName();
  
}
