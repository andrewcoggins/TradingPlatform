package brown.communication.channel.library;

import brown.communication.bid.IBid;
import brown.communication.bid.library.GameBid;
import brown.logging.library.ErrorLogging;
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
    if (action instanceof GameBid) {
  	  // TODO: do something here.
  	} else {
  	  ErrorLogging.log("ERROR: incorrect game bid type.");
  	}
  }


  
}