package brown.agent;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import brown.exceptions.AgentCreationException;
import brown.messages.library.AbsMessage;
import brown.messages.library.AckMessage;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.BidRequestMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.NegotiateRequestMessage;
import brown.messages.library.RegistrationMessage;
import brown.setup.Logging;
import brown.setup.ISetup;

public abstract class AbsAgent extends AbsClient implements IAgent { 

  /**
   * Implementations should always invoke super()
   * 
   * @param host
   * @param port
   * @param gameSetup
   * @throws AgentCreationException
   */
  public AbsAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);

    final AbsAgent agent = this;
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
  
  //could move up to AbsClient
  public void onRegistration(RegistrationMessage registration) {
    this.ID = registration.getID();
  }

  //could move up to AbsClient
  //includes the rejected message and might say why??
  public void onAck(AckMessage message) {
    if (message.REJECTED) {
      Logging.log("[x] rej: " + message.failedBR);
    }
  }
  
  public void onBankUpdate(BankUpdateMessage bankUpdate) {
    // TODO
  }
  
  // FIX ME !!
  public void onMarketUpdate(GameReportMessage marketUpdate) {
    // TODO
  }


  // and provides information about the current market state as a part of the request
  // REALLY ??
  public void onTradeRequest(BidRequestMessage tradeRequest) {
    //Does nothing. I really don't understand!
  }
 
  
  // TODO: Create a NegotiateChannel
  // Move to AbsNegotiateAgent
  public void onNegotiateRequest(NegotiateRequestMessage tradeRequest) {
    // Noop
  }
  
}

  
 