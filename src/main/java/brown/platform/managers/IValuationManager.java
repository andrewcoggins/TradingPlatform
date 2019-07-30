package brown.platform.managers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.valuation.IGeneralValuation;
import brown.communication.messages.IValuationMessage;
import brown.platform.item.ICart;

/**
 * IValuationManager stores and creates agent valuations.
 * 
 * @author andrewcoggins
 *
 */
public interface IValuationManager {

  /**
   * create distribution from which valuations are drawn. 
   * @param distCons
   * constructor for valuation distribution. 
   * @param generatorCons
   * generator constructor. 
   * @param generatorParams
   * parameters for IValuationGenerator
   * @param items
   * ICart that the distribution is to provide values for. 
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   */
  public void createValuation(Constructor<?> distCons,
      List<Constructor<?>> generatorCons, List<List<Double>> generatorParams,
      ICart items) throws InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException;

  /**
   * stores an IValuation associated with an agent. 
   * @param agentID
   * @param valuation
   * the agent's valuation
   */
  public void addAgentValuation(Integer agentID, IGeneralValuation valuation);

  /**
   * gets an IGeneralValuation associated with an agent. 
   * @param agentID
   * The ID of the agent whose valuation is retrieved
   * @return
   */
  public IGeneralValuation getAgentValuation(Integer agentID);

  /**
   * get all of IValuationManager's IValulationDistribution
   * @return
   */
  public List<IValuationDistribution> getDistribution();
  
  /**
   * lock the manager; after it is locked, no more valuation distributions may be created. 
   */
  public void lock();

  /**
   * construct valuation messages to be sent to agents. 
   * @return
   */
  public Map<Integer, IValuationMessage> constructValuationMessages();

  /**
   * reset all agent valuations stored within the manager. 
   */
  public void reset();

}
