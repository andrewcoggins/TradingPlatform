package brown.mechanism.channel.library;

import brown.logging.library.ErrorLogging;
import brown.mechanism.bid.IBid;
import brown.mechanism.bid.library.ContinuousGameBid;
import brown.mechanism.bid.library.DiscreetGameBid;
import brown.user.agent.library.AbsAgent;
import brown.user.agent.library.AbsGameAgent;

/**
 * Game channel is the channel through which server and agent communicate in non-auction games.
 * @author andrew
 */
public class GameChannel extends AbsChannel {
	
  /**
   * For Kryo
   * DO NOT USE
   */
  public GameChannel() { 
	super();
  }
  
  /**
   * Constructor
   * @param ID
   */
  public GameChannel(Integer ID) {
	super(ID); 
  }

  @Override
  public void dispatchMessage(AbsAgent agent) {
    if (agent instanceof AbsGameAgent) {
      AbsGameAgent gameAgent = (AbsGameAgent) agent; 
        gameAgent.onContinuousGame(this); 
    }
  }

  @Override
  public void bid(AbsAgent agent, IBid action) {
  	if (action instanceof ContinuousGameBid && this.continuous) {
  	  
  	} else if (action instanceof DiscreetGameBid && !this.continuous) {
  	  
  	} else {
  	  ErrorLogging.log("ERROR: incorrect game bid type.");
  	}
  }


  
}