package brown.user.agent.library;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import brown.communication.messages.IAgentToServerMessage;
import brown.communication.messages.IBankUpdateMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IServerToAgentMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.IStatusMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;
import brown.communication.messages.library.AbsServerToAgentMessage;
import brown.communication.messages.library.RegistrationMessage;
import brown.logging.library.SystemLogging;
import brown.system.setup.ISetup;
import brown.system.setup.library.Setup;
import brown.user.agent.IAgent;
import brown.user.agent.IAgentBackend;

/**
 * every agent class extends this class.
 * 
 * @author andrew
 *
 */
public class OnlineAgentBackend extends AbsAgentBackend implements IAgentBackend {
  
  protected final IAgent agent; 
  public final Client CLIENT;

  /**
   * 
   * AbsAgent takes in a host, a port, an ISetup.
   * 
   * @param host
   * @param port
   * @param gameSetup
   * @throws AgentCreationException
   */
  public OnlineAgentBackend(String host, int port, ISetup setup, IAgent agent) {
    super(agent); 
    this.CLIENT = new Client(65536, 65536);
    this.ID = null;
    this.publicID = null; 
    CLIENT.start();
    Kryo agentKryo = CLIENT.getKryo();
    Setup.start(agentKryo);
    if (setup != null) {
      setup.setup(agentKryo);
    }
    try {
      CLIENT.connect(5000, host, port, port);
    } catch (IOException e) {
      SystemLogging.log("[x] Failed to Connect to Server");
      SystemLogging.log(e.toString());
    }
    this.agent = agent; 
    final OnlineAgentBackend agentBackend = this;
    // All agents listen for messages.
    CLIENT.addListener(new Listener() {
      public void received(Connection connection, Object message) {
        synchronized (agent) {
          if (message instanceof AbsServerToAgentMessage) {
            AbsServerToAgentMessage theMessage =
                (AbsServerToAgentMessage) message;
            theMessage.agentDispatch(agentBackend);
          }
        }
      }
    });
    
    this.sendMessage(new RegistrationMessage(-1, agent.getAgentName()));
  }
  
  @Override
  public void onBankUpdate(IBankUpdateMessage bankUpdate) {
    this.agent.onBankUpdate(bankUpdate);
  }

  @Override
  public void onInformationMessage(IInformationMessage informationMessage) {
    this.agent.onInformationMessage(informationMessage);
  }

  @Override
  public void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage) {
    this.agent.onTradeRequestMessage(tradeRequestMessage);
  }

  @Override
  public void onValuationMessage(IValuationMessage valuationMessage) {
    this.agent.onValuationMessage(valuationMessage);
    
  }

  @Override
  public void
      onSimulationReportMessage(ISimulationReportMessage reportMessage) {
    this.agent.onSimulationReportMessage(reportMessage);
  }
  
  @Override
  public void sendMessage(IAgentToServerMessage message) {
    this.CLIENT.sendTCP(message);
  }
  
  @Override
  public void onStatusMessage(IStatusMessage message) {
    SystemLogging
        .log("[x] rej: " + message.getStatus() + ", agent ID: " + this.ID);
  }

  @Override
  public void receiveMessage(IServerToAgentMessage message) {
   // noop
  }

}
