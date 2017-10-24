package brown.messages;

import brown.agent.Agent;

/**
 * a request for an agent to join the server 
 * is a sent as a registration.
 * @author lcamery
 */
public class Registration extends Message {
	
	/**
	 * Kryonet requires an empty constructor
	 * DO NOT USE
	 */
	public Registration() {
		super(null);
	}

	/**
	 * Registration when an agent connects to the server
	 * Server sends back with the agent's ID
	 * @param ID : agent's ID
	 */
	public Registration(Integer ID) {
		super(ID);
	}

	@Override
	public void dispatch(Agent agent) {
		agent.onRegistration(this);
	}
}
