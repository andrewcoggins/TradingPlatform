package brown.summary;

import java.util.List;
import java.util.Map;

import brown.accounting.library.Account;
import brown.value.valuation.IValuation;

/**
 * ISimulation summary collects information across simulations.
 * @author andrew
 *
 */
public interface ISimulationSummarizer {
  
  public void collectInformation(List<Account> agentAccounts,
      Map<Integer, IValuation> privateValuations, double initialMonies);
}