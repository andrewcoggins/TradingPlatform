package brown.auction.value.config;

import java.util.List;
import java.util.Map;

import brown.platform.messages.PrivateInformationMessage;

/**
 * IValuationConfig specifies initial valuations and private information. 
 * currently, some valuations do not implement IValuationConfig. 
 * @author andrew
 *
 */
public interface IValuationConfig {

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
