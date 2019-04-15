package brown.platform.managers;

import java.util.List;
import java.util.Map;

import brown.platform.accounting.IInitialEndowment;
import brown.platform.tradeable.ITradeable;

public interface IEndowmentManager {

  void createEndowment(String endowmentName,
      Map<String, Integer> endowmentMapping, Integer frequency,
      Map<String, List<ITradeable>> allTradeables, Double money);

  IInitialEndowment getEndowment();

  void lock();

}
