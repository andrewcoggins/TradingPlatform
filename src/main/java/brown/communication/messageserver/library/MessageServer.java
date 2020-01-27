package brown.communication.messageserver.library;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import brown.communication.messages.IAgentToServerMessage;
import brown.communication.messages.IRegistrationMessage;
import brown.communication.messages.IServerToAgentMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.RegistrationResponseMessage;
import brown.communication.messageserver.IMessageServer;
import brown.logging.library.ErrorLogging;
import brown.logging.library.PlatformLogging;
import brown.platform.managers.ISimulationManager;
import brown.platform.utils.Utils;
import brown.system.kryoserver.library.KryoServer;
import brown.system.setup.ISetup;

/**
 * Message passing server for Trading Platform
 * 
 * @author acoggins
 *
 */
public class MessageServer extends KryoServer implements IMessageServer {

  private ISimulationManager manager;

  public MessageServer(int port, ISetup gameSetup, ISimulationManager manager) {
    super(port, gameSetup);
    this.manager = manager;
    final IMessageServer aServer = this;

    kryoServer.addListener(new Listener() {

      public void received(Connection connection, Object message) {
        if (message instanceof IAgentToServerMessage) {
          IAgentToServerMessage castedMessage = (IAgentToServerMessage) message;
          castedMessage.serverDispatch(connection, aServer);
        }
      }
    });

    PlatformLogging.log("[-] server started");
  }

  @Override
  public void onRegistration(Connection connection,
      IRegistrationMessage registrationMessage) {
    if (!connections.containsKey(connection)) {
      PlatformLogging
          .log("[-] registration recieved from " + connection.getID());
      if (registrationMessage.getMessageID() == null) {
        PlatformLogging
            .log("[x] Server-onRegistration: encountered null registration");
        return;
      }
      // put connection in kryoServer
      // TODO: is this right?
      this.connections.put(connection, connection.getID());
      Integer agentID =
          this.manager.handleRegistration(registrationMessage, connection);
      this.sendMessage(connection, new RegistrationResponseMessage(0, agentID,
          this.manager.getAgentIDs().get(agentID),
          registrationMessage.getName()));
    } else {
      ErrorLogging
          .log("[x] Server-onRegistration: encountered redundant registration");
    }
  }

  @Override
  public void onBid(Connection connection, ITradeMessage bidMessage) {
    if (connections.containsKey(connection)) {
      PlatformLogging.log("[-] bid recieved from " + bidMessage.getAgentID());
      this.manager.giveTradeMessage(bidMessage);
    } else {
      ErrorLogging
          .log("[x] Server-onBid: encountered unknown agent connection");
    }
  }

  @Override
  public void sendMessage(Connection connection,
      IServerToAgentMessage message) {
    this.kryoServer.sendToTCP(connection.getID(), Utils.sanitize(message, this.manager.getAgentIDs()));
  }

  @Override
  public void stopMessageServer() {
    this.kryoServer.close();
    this.kryoServer.stop();
  }

}
