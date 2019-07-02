package brown.platform.managers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import brown.auction.endowment.IEndowment;
import brown.auction.endowment.distribution.IEndowmentDistribution;
import brown.platform.item.ICart;

public interface IEndowmentManager {

  public void createEndowment(Constructor<?> distCons, Map<Constructor<?>, List<Double>> generatorCons, 
      ICart items) throws InstantiationException, IllegalAccessException,
  IllegalArgumentException, InvocationTargetException;
  
  public void addAgentEndowment(Integer agentID, IEndowment Endowment); 
  
  public IEndowment getAgentEndowment(Integer agentID); 
  
  public List<IEndowmentDistribution> getDistribution();

  public void lock();
  
  public void reset(); 
}
