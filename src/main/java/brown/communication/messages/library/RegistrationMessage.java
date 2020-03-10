package brown.communication.messages.library;

import com.esotericsoftware.kryonet.Connection;

import brown.communication.messages.IRegistrationMessage;
import brown.communication.messageserver.IOnlineMessageServer;

/**
 * a request for an agent to join the server 
 * is a sent as a registration.
 * @author lcamery
 */
public class RegistrationMessage extends AbsAgentToServerMessage implements IRegistrationMessage {
  
  private String name; 
  
  public RegistrationMessage() {
    super(null, null);
    this.name = null; 
  }
  
  public RegistrationMessage(Integer messageID) {
    super(messageID, 0); 
    this.name = "default"; 
  }
  
  public RegistrationMessage(Integer messageID, String name) {
    super(messageID, 0); 
    this.name = name; 
  }
  
  @Override
  public void serverDispatch(Connection connection, IOnlineMessageServer server) {
    server.onRegistration(connection, this);
  }
  
  @Override
  public String getName() {
    return this.name; 
  }

  @Override
  public String toString() {
    return "RegistrationMessage [name=" + name + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    RegistrationMessage other = (RegistrationMessage) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
  
	
}
