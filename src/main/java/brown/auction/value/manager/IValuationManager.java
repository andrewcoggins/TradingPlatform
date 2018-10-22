package brown.auction.value.manager;

import java.util.List;
import java.util.Map;

import brown.platform.messages.library.PrivateInformationMessage;

/**
 * IValuationManager specifies initial valuations and private information.
 * currently, some valuations do not implement IValuationManager.
 * @author andrew
 *
 */
public interface IValuationManager {

  //TODO: Bulldoze
  /**
   * Initialize some private information.
   * @param agents the IDs of all registered agents.
   */
  public void initialize(List<Integer> agents);
  
  /**
   * Generates a message to send to the agents about information.
   * @param agents IDs of all registered agents
   * @return map from agent IDs to private information messages. 
   */
  public Map<Integer,PrivateInformationMessage> generateReport(List<Integer> agents);
}
