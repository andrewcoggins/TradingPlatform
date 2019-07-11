package brown.platform.managers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import brown.auction.endowment.IEndowment;
import brown.auction.endowment.distribution.IEndowmentDistribution;
import brown.platform.item.ICart;

public interface IEndowmentManager {

  public void createEndowment(Constructor<?> distCons,
      List<Constructor<?>> generatorCons, List<List<Double>> generatorParams, 
      ICart items) throws InstantiationException, IllegalAccessException,
  IllegalArgumentException, InvocationTargetException;
  
  public IEndowment makeAgentEndowment(Integer agentID); 
  
  public List<IEndowmentDistribution> getDistribution();

  public void lock();
  
  public void reset(); 
}
