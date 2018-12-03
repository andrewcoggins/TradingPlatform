package brown.mechanism.channel.library;

import brown.logging.library.ErrorLogging;
import brown.mechanism.bid.IGameAction;
import brown.mechanism.bid.library.ContinuousGameBid;
import brown.mechanism.bid.library.DiscreetGameBid;
import brown.user.agent.library.AbsAgent;
import brown.user.agent.library.AbsGameAgent;

/**
 * Game channel is the channel through which server and agent communicate in non-auction games.
 * @author andrew
 */
public class GameChannel extends AbsChannel {
	
  private final Boolean continuous; 
  /**
   * For Kryo
   * DO NOT USE
   */
  public GameChannel() { 
	super();
	this.continuous = null;
  }
  
  /**
   * Constructor
   * @param ID
   */
  public GameChannel(Integer ID, boolean continuous) {
	super(ID);
	this.continuous = continuous; 
  }

  @Override
  public void dispatchMessage(AbsAgent agent) {
    if (agent instanceof AbsGameAgent) {
      AbsGameAgent gameAgent = (AbsGameAgent) agent; 
      if (continuous) {
          gameAgent.onContinuousGame(this); 
      } else if (!continuous) {
    	gameAgent.onDiscreetGame(this);
      }
    }
  }

@Override
public void bid(AbsAgent agent, IGameAction action) {
	if (action instanceof ContinuousGameBid && this.continuous) {
	  
	} else if (action instanceof DiscreetGameBid && !this.continuous) {
	  
	} else {
	  ErrorLogging.log("ERROR: incorrect game bid type.");
	}
}


  
}