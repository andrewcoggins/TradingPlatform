package brown.user.agent;

import brown.communication.messages.IAgentToServerMessage;
import brown.communication.messages.IBankUpdateMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IRegistrationResponseMessage;
import brown.communication.messages.IServerToAgentMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.IStatusMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;

/**
 * IAgentBackend serves as the communication interface between the agent and the server. 
 * @author andrewcoggins
 *
 */
public interface IAgentBackend { 
  
  /**
   * send a message to the server. 
   * @param message
   */
  public void sendMessage(IAgentToServerMessage message);
  
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
   * Receive a message from the server. 
   * @param message
   */
  public void receiveMessage(IServerToAgentMessage message); 
  
  /**
   * Respond to IRegistrationResponseMessage
   * @param bankUpdate
   */
  public void onRegistrationResponse(IRegistrationResponseMessage registration);
  
  /**
   * Respond to IStatusMessage
   * @param bankUpdate
   */
  public void onStatusMessage(IStatusMessage message);
  
  /**
   * Get agent's Public ID
   * @return
   */
  public Integer getPublicID(); 
  
  /**
   * Get agent's Private ID
   * @return
   */
  public Integer getPrivateID(); 
  
}
