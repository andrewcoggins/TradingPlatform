package brown.user.agent.library;

import brown.communication.channel.library.GameChannel;
import brown.system.setup.ISetup;

public abstract class AbsGameAgent extends AbsAgent {

	public AbsGameAgent(String host, int port, ISetup gameSetup) {
		super(host, port, gameSetup);
	}
	
	
	public abstract void onDiscreetGame(GameChannel channel); 
	
	public abstract void onContinuousGame(GameChannel channel); 
	
}