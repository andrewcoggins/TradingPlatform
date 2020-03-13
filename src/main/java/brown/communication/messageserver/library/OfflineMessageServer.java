package brown.communication.messageserver.library;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import brown.communication.messages.IRegistrationMessage;
import brown.communication.messages.IServerToAgentMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.RegistrationResponseMessage;
import brown.communication.messageserver.IOfflineMessageServer;
import brown.logging.library.ErrorLogging;
import brown.logging.library.PlatformLogging;
import brown.platform.managers.ISimulationManager;
import brown.platform.utils.Utils;
import brown.user.agent.IAgent;
import brown.user.agent.IOfflineAgent;

public class OfflineMessageServer implements IOfflineMessageServer {

  private ISimulationManager manager;
  private Map<Integer, IOfflineAgent> agentConnections;
  
  private Map<String, Integer> messagesReceived; 
  
  private final int IDMULTIPLIER = 1000000000;

  public OfflineMessageServer(ISimulationManager manager) {
    
    this.messagesReceived = new HashMap<String, Integer>();
    this.messagesReceived.put("TradeRequestMessage", 0); 
    this.messagesReceived.put("InformationMessage", 0); 
    this.messagesReceived.put("RegistrationMessage", 0); 
    this.messagesReceived.put("StatusMessage", 0); 
    this.messagesReceived.put("ValuationMessage", 0); 
    this.messagesReceived.put("InitializationMessage", 0); 
    this.messagesReceived.put("SimulationReportMessage", 0); 
    this.messagesReceived.put("BankUpdateMessage", 0);
    this.manager = manager;
    this.agentConnections = new ConcurrentHashMap<Integer, IOfflineAgent>();
    // final IOfflineMessageServer aServer = this;

    PlatformLogging.log("[-] server started");

  }

  @Override
  public synchronized void waitForRegistrations() {
    // notify agents to begin registering
    synchronized (this) {
      try {
        // wait for agents to respond.
        this.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }
  
  @Override
  public synchronized void waitForBids(String messageType) {
    Utils.sleep(50);
    System.out.println("server waiting for agent notification: " + messageType); 
    // notify agents to begin registering
      try {
        // wait for agents to respond.
        this.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("server received agent notification: " + messageType); 
  }

  @Override
  public void onRegistration(IAgent connection,
      IRegistrationMessage registrationMessage) {
    // this will run in the agent thread.
    // once all have been received (?) do a notify.
    PlatformLogging
    .log("[x] new registration");
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

      this.agentConnections.put(agentPrivateID, (IOfflineAgent) connection);

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
    this.manager.giveTradeMessage(bidMessage);
    //System.out.println("given"); 
  }

  @Override
  public void sendMessage(Integer agentPrivateID,
      IServerToAgentMessage message) {
    this.agentConnections.get(agentPrivateID).receiveMessage(message); 
  }

  @Override
  public void stopMessageServer() {
    // TODO Auto-generated method stub

  }

  @Override
  public void notifyToRespond() {
    System.out.println("trying to notify agents"); 
    for (IOfflineAgent agent : this.agentConnections.values()) {
      synchronized(agent) {
        System.out.println("trying to notify agent"); 
        agent.notify(); 
      }
    }
  }
  
  @Override
  public boolean readyToNotify(String messagetype) {
    int numMessages = this.messagesReceived.get(messagetype); 
    numMessages++; 
    this.messagesReceived.put(messagetype, numMessages); 
    System.out.println("messages received: " + this.messagesReceived);
    return numMessages == this.agentConnections.keySet().size(); 
  }

  @Override
  public void clearMessagesRecieved(String messageType) {
    this.messagesReceived.put(messageType, 0); 
  }
  
}
