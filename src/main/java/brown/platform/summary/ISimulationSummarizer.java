package brown.platform.summary;

import java.util.List;
import java.util.Map;

import brown.auction.value.valuation.IValuation;
import brown.platform.accounting.library.Account;

/**
 * ISimulation summary collects information across simulations.
 * @author andrew
 *
 */
public interface ISimulationSummarizer {
  
  /**
   * collects information for summary.
   * @param agentAccounts all agent accounts. 
   * @param privateValuations all agent private valuations.
   */
  public void collectInformation(List<Account> agentAccounts, Map<Integer, IValuation> privateValuations);
}