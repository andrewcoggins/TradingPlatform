  package brown.platform.server.library;

import brown.mechanism.messages.IMessage;

public interface IMessageServer {
  
  // server sends invitaions for registration
  public void openRegistration(); 
  
  // server receives registration message from agent. 
  public void onRegistration(); 
  
  // server receives request for information from agent. 
  public void onInformationRequest(); 
  
  // server receives bid message from agent. 
  public void onBid(IMessage bidMessage); 
  
  
}
