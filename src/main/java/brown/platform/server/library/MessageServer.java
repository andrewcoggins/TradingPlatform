package brown.platform.server.library;

import brown.platform.messages.IMessage;
import brown.system.kryoserver.library.KryoServer;
import brown.system.setup.ISetup;

/**
 * The core server for the Trading Platform. Integrates all parts of the platform- 
 * manages server-side agent information including accounts and valuations,
 * mediates message passing between agents and markets, controls the dymanics of 
 * markets within a simulation, collects and summarizes information about 
 * agent accounts and utility at the end of simulations. 
 * 
 * @author acoggins, kerry, lcamery
 *
 */
public class MessageServer extends KryoServer implements IMessageServer{
  public MessageServer(int port, ISetup gameSetup) {
    super(port, gameSetup);
  }

  @Override
  public void openRegistration() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onRegistration() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onInformationRequest() {
    // TODO Auto-generated method stub
    
  }

  @Override 
  public void onBid(IMessage bidMessage) {
    // TODO Auto-generated method stub
    
  }
}
