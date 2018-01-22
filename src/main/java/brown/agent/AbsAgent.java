package brown.agent;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import brown.exceptions.AgentCreationException;
import brown.messages.library.AbsMessage;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.NegotiateRequestMessage;
import brown.messages.library.PrivateInformationMessage;
import brown.messages.library.RegistrationMessage;
import brown.setup.ISetup;

/**
 * every agent class extends this class.
 * @author andrew
 *
 */
public abstract class AbsAgent extends AbsClient implements IAgent { 

  /**
   * 
   * AbsAgent takes in a host, a port, an ISetup.
   * @param host
   * @param port
   * @param gameSetup
   * @throws AgentCreationException
   */
  public AbsAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
    final AbsAgent agent = this;
    // All agents listen for messages.
    CLIENT.addListener(new Listener() {
      public void received(Connection connection, Object message) {
        synchronized (agent) {
          if (message instanceof AbsMessage) {
            AbsMessage theMessage = (AbsMessage) message;
            theMessage.dispatch(agent);
          }
        }
      }
    });

    CLIENT.sendTCP(new RegistrationMessage(-1));
  }

  public void onPrivateInformation(PrivateInformationMessage registration) {
  }
  
  public void onBankUpdate(BankUpdateMessage bankUpdate) {

  }
  
  public void onMarketUpdate(GameReportMessage marketUpdate) {

  }
    
  public void onNegotiateRequest(NegotiateRequestMessage request) {
    
  }
  
}

  
 