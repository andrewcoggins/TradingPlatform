package brown.communication.messageserver.library;

import java.util.Collection;
import java.util.Map;

import brown.communication.messages.IRegistrationMessage;
import brown.communication.messages.IServerToAgentMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.RegistrationResponseMessage;
import brown.communication.messageserver.IOfflineMessageServer;
import brown.logging.library.ErrorLogging;
import brown.logging.library.PlatformLogging;
import brown.platform.managers.ISimulationManager;
import brown.system.setup.ISetup;
import brown.user.agent.IAgent;

public class OfflineMessageServer implements IOfflineMessageServer {

  private ISimulationManager manager;
  private Map<Integer, IAgent> agentConnections;
  
  private final int IDMULTIPLIER = 1000000000;

  public OfflineMessageServer(int port, ISetup gameSetup,
      ISimulationManager manager) {
    this.manager = manager;
    final IOfflineMessageServer aServer = this;

    PlatformLogging.log("[-] server started");
  }

  @Override
  public void onRegistration(IAgent connection,
      IRegistrationMessage registrationMessage) {
    if (registrationMessage.getMessageID() == null) {
      PlatformLogging
          .log("[x] Server-onRegistration: encountered null registration");
      return;
    }

    Integer agentPrivateID = -1;
    Collection<Integer> allIds = this.agentConnections.keySet();
    if (!allIds.contains(agentPrivateID)) {
      agentPrivateID = ((int) (Math.random() * IDMULTIPLIER));
      while (allIds.contains(agentPrivateID)) {
        agentPrivateID = ((int) (Math.random() * IDMULTIPLIER));
      }

      this.agentConnections.put(agentPrivateID, connection);

      Integer agentID =
          this.manager.handleRegistration(registrationMessage, agentPrivateID);
      this.sendMessage(agentPrivateID,
          new RegistrationResponseMessage(0, agentID,
              this.manager.getAgentIDs().get(agentID),
              registrationMessage.getName(),
              this.manager.getSimulationJsonFileName()));
    } else {
      ErrorLogging
          .log("[x] Server-onRegistration: encountered redundant registration");
    }

  }

  @Override
  public void onBid(IAgent connection, ITradeMessage bidMessage) {
      PlatformLogging.log("[-] bid recieved from " + bidMessage.getAgentID());
      // this is fine. It will actually run in the agent thread. 
      this.manager.giveTradeMessage(bidMessage); 
      // once all have been given, do a notify. 
  }

  @Override
  public void sendMessage(Integer agentPrivateID,
      IServerToAgentMessage message) {
    // this is a bit more complicated. 
    // if we just call the method directly, it'll run the code. 
    
    // instead we want to put it in holding on the agent side, and notify the agent thread. 

  }

  @Override
  public void stopMessageServer() {
    // TODO Auto-generated method stub

  }

}
