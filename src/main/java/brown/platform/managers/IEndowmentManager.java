package brown.platform.managers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import brown.auction.endowment.IEndowment;
import brown.auction.endowment.distribution.IEndowmentDistribution;
import brown.platform.item.ICart;

/**
 * Endowment Manager creates and stores initial agent endowments of goods and money
 * @author andrewcoggins
 *
 */
public interface IEndowmentManager {

  /**
   * create a distribution over which endowments for specified items are generated. 
   * @param distCons
   * constructor for an IEndowmentDistribution
   * @param generatorCons
   * contstructor for an IValuationGenerator
   * @param generatorParams
   * parameters for the IValuationGenerator constructor
   * @param items
   * items to be included in the generated endowments. 
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   */
  public void createEndowment(Constructor<?> distCons,
      List<Constructor<?>> generatorCons, List<List<Double>> generatorParams, 
      ICart items) throws InstantiationException, IllegalAccessException,
  IllegalArgumentException, InvocationTargetException;
  
  /**
   * make an agent endowment. 
   * @param agentID
   * @return
   */
  public IEndowment makeAgentEndowment(Integer agentID); 
  
  /**
   * get an endowment distribution. this is the 'seed' from which agent endowments are 
   * drawn. 
   * @return
   */
  public List<IEndowmentDistribution> getDistribution();

  /**
   * locks manager. no more endowment distributions can be created after this method is called. 
   */
  public void lock();
  
  /**
   * reset endowments. clears all initial endowments given to agents. 
   */
  public void reset(); 
}
