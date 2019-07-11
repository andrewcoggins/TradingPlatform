package brown.platform.managers.library;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.endowment.IEndowment;
import brown.auction.endowment.distribution.IEndowmentDistribution;
import brown.auction.endowment.library.Endowment;
import brown.auction.value.generator.IValuationGenerator;
import brown.logging.library.PlatformLogging;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.managers.IEndowmentManager;

public class EndowmentManager implements IEndowmentManager {

  private List<IEndowmentDistribution> distributions;
  private Map<Integer, IEndowment> agentEndowments;
  private boolean lock;

  public EndowmentManager() {

    this.lock = false;
    this.agentEndowments = new HashMap<Integer, IEndowment>();
    this.distributions = new LinkedList<IEndowmentDistribution>();
  }

  public void createEndowment(Constructor<?> distCons,
      List<Constructor<?>> generatorCons, List<List<Double>> generatorParams, 
      ICart items)
      throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {  
    if (!this.lock) {
      List<IValuationGenerator> generatorList =
          new LinkedList<IValuationGenerator>();
      for (int i = 0; i < generatorCons.size(); i++) {
        IValuationGenerator newGen = (IValuationGenerator) generatorCons.get(i).newInstance(generatorParams.get(i));
        generatorList.add(newGen);
      }
      this.distributions.add(
          (IEndowmentDistribution) distCons.newInstance(items, generatorList));
    } else {
      PlatformLogging.log("Creation denied: Endowment manager locked.");
    }
  }

  public IEndowment makeAgentEndowment(Integer agentID) {
    List<IEndowment> endowments = new LinkedList<IEndowment>(); 
    for (IEndowmentDistribution dist : this.distributions) {
      IEndowment anEndowment = dist.sample();
      endowments.add(anEndowment); 
    }
    this.agentEndowments.put(agentID, combine(endowments)); 
    return this.agentEndowments.get(agentID);
  }

  public List<IEndowmentDistribution> getDistribution() {
    return this.distributions;
  }
  
  public void lock() {
    this.lock = true;
  }

  @Override
  public void reset() {
    this.agentEndowments.clear();
  }
  
  private IEndowment combine(List<IEndowment> multipleEndowments) {
    if (multipleEndowments.size() > 0) {
      IEndowment first = multipleEndowments.get(0);
      
      ICart firstCart = first.getCart(); 
      double firstMoney = first.getMoney();
      
      multipleEndowments.remove(0); 
      for (IEndowment anEndowment : multipleEndowments) {
        firstMoney += anEndowment.getMoney(); 
        anEndowment.getCart().getItems().forEach(anItem -> firstCart.addToCart(anItem));
      }
      return new Endowment(firstCart, firstMoney); 
    } else {
      return new Endowment(new Cart(new LinkedList<IItem>()), 0.0); 
    }
  }

}
