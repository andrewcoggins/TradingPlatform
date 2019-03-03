  package brown.communication.messageserver;

import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IInformationRequestMessage;
import brown.communication.messages.IRegistrationMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;

/**
 * Interface for communication between server and agents. 
 * @author andrewcoggins
 *
 */
public interface IMessageServer {
  
  // server sends invitaions for registration
  public void openRegistration(); 
  
  // server receives registration message from agent. 
  public void onRegistration(IRegistrationMessage registrationMessage); 
  
  // server receives request for information from agent. 
  public void onInformationRequest(IInformationRequestMessage informationRequestMessage); 
  
  // server receives bid message from agent. 
  public void onBid(ITradeMessage bidMessage); 
  
  // server sends tradeRequest message
  public void sendTradeRequest(ITradeRequestMessage tRequestMessage); 
  
  // server sends information message
  public void sendInfomationMessage(IInformationMessage informationMessage); 
}
