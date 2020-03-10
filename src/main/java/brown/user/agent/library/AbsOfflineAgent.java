package brown.user.agent.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.communication.messages.IBankUpdateMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IRegistrationResponseMessage;
import brown.communication.messages.IServerToAgentMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.IStatusMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;
import brown.communication.messages.library.RegistrationMessage;
import brown.communication.messageserver.IOfflineMessageServer;
import brown.logging.library.ErrorLogging;
import brown.logging.library.SystemLogging;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Item;
import brown.platform.utils.Utils;
import brown.user.agent.IOfflineAgent;

public abstract class AbsOfflineAgent implements IOfflineAgent {

  private List<IServerToAgentMessage> incomingMessages;

  public Integer ID;
  public Integer publicID;

  protected IOfflineMessageServer messageServer;

  protected double money;
  protected Map<String, IItem> goods;
  protected IBankUpdateMessage initialEndowment;

  public AbsOfflineAgent(IOfflineMessageServer messageServer) {
    this.incomingMessages = new LinkedList<IServerToAgentMessage>();
    this.goods = new HashMap<String, IItem>();
    this.messageServer = messageServer;
    messageServer.onRegistration(this, new RegistrationMessage(-1));
    while (true) {
      synchronized(this) {
        try {
          wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } 
      }
      synchronized (this.incomingMessages) {
        if (this.incomingMessages.size() > 0) {
          for (IServerToAgentMessage message : this.incomingMessages) {
            message.agentDispatch(this);
          }
          this.incomingMessages.clear(); 
        }
      }
    }
  }

  public abstract void
      onInformationMessage(IInformationMessage informationMessage);

  public abstract void
      onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage);

  public abstract void onValuationMessage(IValuationMessage valuationMessage);

  public abstract void
      onSimulationReportMessage(ISimulationReportMessage reportMessage);

  public abstract void onStatusMessage(IStatusMessage message);

  public synchronized void receiveMessage(IServerToAgentMessage message) {
    
    // TODO: straight notify. 
      synchronized (this.incomingMessages) {
        this.incomingMessages.add(message);
        synchronized(this) {
          this.notify();
        }
      }
  }

  public void onBankUpdate(IBankUpdateMessage bankUpdate) {
    this.initialEndowment = bankUpdate;
    this.money += bankUpdate.getMoneyAddedLost();
    updateItems(bankUpdate.getItemsAdded(), true);
    updateItems(bankUpdate.getItemsLost(), false);
    notifyServer("BankUpdateMessage");
  }

  @Override
  public void
      onRegistrationResponse(IRegistrationResponseMessage registrationMessage) {
    SystemLogging.log("[-] Registered To Server");
    this.ID = registrationMessage.getAgentID();
    this.publicID = registrationMessage.getPublicAgentID();
    SystemLogging.log("Private ID: " + this.ID);
    SystemLogging.log("Public ID: " + this.publicID);
    notifyServer("RegistrationMessage");
  }

  protected void notifyServer(String messageType) {
    synchronized (this.messageServer) {
      if (this.messageServer.readyToNotify(messageType)) {
        this.messageServer.clearMessagesRecieved(messageType); 
        System.out.println("notifying");
        this.messageServer.notify(); 
      }
    }
  }

  // helper methods
  private void updateItems(ICart cart, boolean add) {
    if (add) {
      for (IItem item : cart.getItems()) {
        if (!this.goods.containsKey(item.getName())) {
          this.goods.put(item.getName(), item);
        } else {
          IItem currentItem = this.goods.get(item.getName());
          IItem updatedItem = new Item(item.getName(),
              currentItem.getItemCount() + item.getItemCount());
          this.goods.put(item.getName(), updatedItem);
        }
      }
    } else {
      for (IItem item : cart.getItems()) {
        if (this.goods.containsKey(item.getName())) {
          IItem currentItem = this.goods.get(item.getName());
          int newCount = currentItem.getItemCount() - item.getItemCount();
          if (newCount == 0) {
            this.goods.remove(item.getName());
          } else if (newCount > 0) {
            this.goods.put(item.getName(), new Item(item.getName(), newCount));
          } else {
            ErrorLogging
                .log("ERROR: attempted to remove too high quantity of item: "
                    + item.toString() + "vs. "
                    + this.goods.get(item.getName()));
          }
          IItem updatedItem = new Item(item.getName(),
              currentItem.getItemCount() - item.getItemCount());
          this.goods.put(item.getName(), updatedItem);
        } else {
          ErrorLogging.log("ERROR: nonexistent item: " + item.toString());
        }
      }
    }
  }

}
