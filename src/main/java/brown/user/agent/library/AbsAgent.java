package brown.user.agent.library;

import java.util.HashMap;
import java.util.Map;

import brown.communication.messages.IBankUpdateMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;
import brown.logging.library.ErrorLogging;
import brown.logging.library.UserLogging;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Item;
import brown.user.agent.IAgent;
import brown.user.agent.IAgentBackend;

public abstract class AbsAgent implements IAgent {
  
  protected double money;
  protected Map<String, IItem> goods;
  protected String name;
  protected IBankUpdateMessage initialEndowment; 
  protected IAgentBackend agentBackend; 
  
  
  public AbsAgent(String name) {
    this.name = name; 
    this.money = 0.0; 
    this.goods = new HashMap<String, IItem>(); 
  }
  
  @Override 
  public void addAgentBackend(IAgentBackend backend) {
    this.agentBackend = backend; 
  }
  
  @Override
  public void onBankUpdate(IBankUpdateMessage bankUpdate) {
    UserLogging.log(this.name + ": " + bankUpdate.toString());
    this.initialEndowment = bankUpdate; 
    this.money += bankUpdate.getMoneyAddedLost();
    updateItems(bankUpdate.getItemsAdded(), true);
    updateItems(bankUpdate.getItemsLost(), false);
  }
  
  @Override
  public String getAgentName() {
    return this.name;
  }
  
  @Override
  public abstract void onInformationMessage(IInformationMessage informationMessage); 

  @Override
  public abstract void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage); 
  
  @Override
  public abstract void onValuationMessage(IValuationMessage valuationMessage); 
  
  @Override
  public abstract void onSimulationReportMessage(ISimulationReportMessage simReportMessage); 
  
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
