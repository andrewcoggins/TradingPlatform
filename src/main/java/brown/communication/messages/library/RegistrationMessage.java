package brown.communication.messages.library;

import brown.communication.messages.IRegistrationMessage;

/**
 * a request for an agent to join the server 
 * is a sent as a registration.
 * @author lcamery
 */
public class RegistrationMessage extends AbsRegistrationMessage implements IRegistrationMessage {
  
	/**
	 * Empty constructor for Kryo
	 * DO NOT USE
	 */
	public RegistrationMessage() {
		super(null);
	}

	/**
	 * Agent sends a registration message initially
	 * Server sends back a message with the agent's ID
	 * @param ID : agent's ID
	 */
	public RegistrationMessage(Integer ID) {
		super(ID);
	}
	
	/**
	 * Registration with name.
	 * @param ID agent's ID
	 * @param name: agent's name
	 */
	 public RegistrationMessage(Integer ID, String name) {
	    super(ID, name);
	  }
	
}
