package brown.communication.messages.library;

import brown.user.agent.library.AbsAgent;

/**
 * a request for an agent to join the server 
 * is a sent as a registration.
 * @author lcamery
 */
public class RegistrationMessage extends AbsMessage {
	public String name;
  
	/**
	 * Empty constructor for Kryo
	 * DO NOT USE
	 */
	public RegistrationMessage() {
		super(null);
		this.name = null;
	}

	/**
	 * Agent sends a registration message initially
	 * Server sends back a message with the agent's ID
	 * @param ID : agent's ID
	 */
	public RegistrationMessage(Integer ID) {
		super(ID);
		this.name = null;
	}
	
	/**
	 * Registration with name.
	 * @param ID agent's ID
	 * @param name: agent's name
	 */
	 public RegistrationMessage(Integer ID, String name) {
	    super(ID);
	    this.name = name;
	  }


	@Override
	public void dispatch(AbsAgent agent) {
		agent.onRegistration(this);
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
