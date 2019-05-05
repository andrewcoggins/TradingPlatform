package brown.user.agent.library;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import brown.communication.messages.IBankUpdateMessage;
import brown.communication.messages.library.AbsServerToAgentMessage;
import brown.logging.library.Logging;
import brown.system.client.library.TPClient;
import brown.system.setup.ISetup;
import brown.user.agent.IAgent;

/**
 * every agent class extends this class.
 * @author andrew
 *
 */
public abstract class AbsAgent extends TPClient implements IAgent { 

  
  protected double money;
//  protected List<ITradeable> goods;
//  
  /**
   * 
   * AbsAgent takes in a host, a port, an ISetup.
   * @param host
   * @param port
   * @param gameSetup
   * @throws AgentCreationException
   */
  public AbsAgent(String host, int port, ISetup gameSetup) {
    super(host, port, gameSetup);
    final AbsAgent agent = this;
    // All agents listen for messages.
    CLIENT.addListener(new Listener() {
      public void received(Connection connection, Object message) {
        synchronized (agent) {
          if (message instanceof AbsServerToAgentMessage) {
            AbsServerToAgentMessage theMessage = (AbsServerToAgentMessage) message;
            
            theMessage.agentDispatch(agent);
          }
        }
      }
    });
  }
  
//
//    CLIENT.sendTCP(new RegistrationMessage(-1, -1));
//    
//    this.monies = 0.0;
//    this.goods = new LinkedList<ITradeable>();
//  }
//  
//  /**
//   * 
//   * AbsAgent takes in a host, a port, an ISetup.
//   * @param host
//   * @param port
//   * @param gameSetup
//   * @throws AgentCreationException
//   */
//  public AbsAgent(String host, int port, ISetup gameSetup, String name) {
//    super(host, port, gameSetup);
//    final AbsAgent agent = this;
//    // All agents listen for messages.
//    CLIENT.addListener(new Listener() {
//      public void received(Connection connection, Object message) {
//        synchronized (agent) {
//          if (message instanceof AbsMessage) {
//            AbsMessage theMessage = (AbsMessage) message;
//            theMessage.dispatch(agent);
//          }
//        }
//      }
//    });
//
//    CLIENT.sendTCP(new RegistrationMessage(-1, -1, name));
//    this.monies = 0.0;
//    this.goods = new LinkedList<ITradeable>();
//  }
//  
  public void onBankUpdate(IBankUpdateMessage bankUpdate) {
    this.money += bankUpdate.getMoneyAddedLost();
    Logging.log(bankUpdate.toString());
    // as long as u don't do weird shit with fractions this should work
    // maybe fix this later
//    for (int i = 0; i < bankUpdate.quantity; i++){
//      this.goods.add(bankUpdate.tradeableAdded);
//      this.goods.remove(bankUpdate.tradeableLost);      
//    }
  }
//
//  @Override
//  public void onAccountInitialization(AccountResetMessage accountResetMessage) {
//    this.monies = accountResetMessage.monies;
//    this.goods = accountResetMessage.tradeables;
//  }
}
