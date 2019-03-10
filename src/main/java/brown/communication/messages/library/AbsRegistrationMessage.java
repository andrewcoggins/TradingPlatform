package brown.communication.messages.library;

import com.esotericsoftware.kryonet.Connection;

import brown.communication.messages.IRegistrationMessage;
import brown.communication.messageserver.IMessageServer;

public abstract class AbsRegistrationMessage extends AbsAgentToServerMessage implements IRegistrationMessage {

  private String name; 
  
  public AbsRegistrationMessage() {
    super(null);
  }
  
  public AbsRegistrationMessage(Integer messageID) {
    super(messageID); 
    this.name = ""; 
  }
  
  public AbsRegistrationMessage(Integer messageID, String name) {
    super(messageID); 
    this.name = name; 
  }
  
  @Override
  public void serverDispatch(Connection connection, IMessageServer server) {
    server.onRegistration(connection, this);
  }
  
  @Override
  public String getName() {
    return this.name; 
  }
  
  
}
