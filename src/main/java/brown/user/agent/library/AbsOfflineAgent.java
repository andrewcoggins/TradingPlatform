package brown.user.agent.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import brown.communication.messages.IAgentToServerMessage;
import brown.communication.messages.IBankUpdateMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IRegistrationResponseMessage;
import brown.communication.messages.IServerToAgentMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.IStatusMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;
import brown.communication.messages.library.AckMessage;
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

  public Integer ID;
  public Integer publicID;

  protected IOfflineMessageServer messageServer;

  protected double money;
  protected Map<String, IItem> goods;
  protected IBankUpdateMessage initialEndowment;
  
  private final LinkedBlockingQueue<IServerToAgentMessage> tasks;

  public AbsOfflineAgent(IOfflineMessageServer messageServer) {
    this.goods = new HashMap<String, IItem>();
    this.messageServer = messageServer;
    this.tasks = new LinkedBlockingQueue<>();
    
    // "listener"
    new Thread(new Runnable() {
		
		@Override
		public void run() {
			while (true) {
				IServerToAgentMessage message;
				try {
					message = AbsOfflineAgent.this.tasks.take();
					message.agentDispatch(AbsOfflineAgent.this);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}
	}).start();
    
    
    this.sendMessage(new RegistrationMessage(-1));
  }
  
  @Override
  public synchronized void sendMessage(IAgentToServerMessage message) {
	  this.messageServer.receiveMessage(this, message);
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
	  try {
		this.tasks.put(message);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
  }

  public void onBankUpdate(IBankUpdateMessage bankUpdate) {
    this.initialEndowment = bankUpdate;
    this.money += bankUpdate.getMoneyAddedLost();
    updateItems(bankUpdate.getItemsAdded(), true);
    updateItems(bankUpdate.getItemsLost(), false);
    this.sendMessage(new AckMessage(0, this.ID, bankUpdate.getMessageID()));
  }

  @Override
  public void
      onRegistrationResponse(IRegistrationResponseMessage registrationMessage) {
    SystemLogging.log("[-] Registered To Server");
    this.ID = registrationMessage.getAgentID();
    this.publicID = registrationMessage.getPublicAgentID();
    SystemLogging.log("Private ID: " + this.ID);
    SystemLogging.log("Public ID: " + this.publicID);
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
