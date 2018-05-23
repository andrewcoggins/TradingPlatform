package brown.messages.library;

import brown.agent.AbsAgent;

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
	
}
