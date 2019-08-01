package brown.user.agent.library;

import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import brown.communication.messages.IBankUpdateMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;
import brown.communication.messages.library.AbsServerToAgentMessage;
import brown.communication.messages.library.RegistrationMessage;
import brown.logging.library.ErrorLogging;
import brown.logging.library.UserLogging;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Item;
import brown.system.client.library.TPClient;
import brown.system.setup.ISetup;
import brown.user.agent.IAgent;

/**
 * every agent class extends this class.
 * 
 * @author andrew
 *
 */
public abstract class AbsAgent extends TPClient implements IAgent {

  protected double money;
  protected Map<String, IItem> goods;

  /**
   * 
   * AbsAgent takes in a host, a port, an ISetup.
   * 
   * @param host
   * @param port
   * @param gameSetup
   * @throws AgentCreationException
   */
  public AbsAgent(String host, int port, ISetup gameSetup) {
    super(host, port, gameSetup);
    final AbsAgent agent = this;
    // All agents listen for messages
    CLIENT.addListener(new Listener() {
      public void received(Connection connection, Object message) {
        synchronized (agent) {
          if (message instanceof AbsServerToAgentMessage) {
            AbsServerToAgentMessage theMessage =
                (AbsServerToAgentMessage) message;
            theMessage.agentDispatch(agent);
          }
        }
      }
    });

    CLIENT.sendTCP(new RegistrationMessage(-1));
    this.money = 0.0;
    this.goods = new HashMap<String, IItem>();
  }

  /**
   * 
   * AbsAgent takes in a host, a port, an ISetup.
   * 
   * @param host
   * @param port
   * @param gameSetup
   * @throws AgentCreationException
   */
  public AbsAgent(String host, int port, ISetup gameSetup, String name) {
    super(host, port, gameSetup);
    final AbsAgent agent = this;
    // All agents listen for messages.
    CLIENT.addListener(new Listener() {
      public void received(Connection connection, Object message) {
        synchronized (agent) {
          if (message instanceof AbsServerToAgentMessage) {
            AbsServerToAgentMessage theMessage =
                (AbsServerToAgentMessage) message;
            theMessage.agentDispatch(agent);
          }
        }
      }
    });

    CLIENT.sendTCP(new RegistrationMessage(-1, name));
    this.money = 0.0;
    this.goods = new HashMap<String, IItem>();
  }
  
  @Override
  public void onBankUpdate(IBankUpdateMessage bankUpdate) {
    UserLogging.log(bankUpdate.toString());
    this.money += bankUpdate.getMoneyAddedLost();
    updateItems(bankUpdate.getItemsAdded(), true);
    updateItems(bankUpdate.getItemsLost(), false);
  }
  
  @Override
  public abstract void onInformationMessage(IInformationMessage informationMessage); 

  @Override
  public abstract void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage); 
  
  @Override
  public abstract void onValuationMessage(IValuationMessage valuationMessage); 
  
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
            this.goods.put(item.getName(),
                new Item(item.getName(), newCount));
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
