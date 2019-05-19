package brown.platform.managers;

import java.util.Map;

import brown.platform.accounting.IInitialEndowment;
import brown.platform.item.ICart;

public interface IEndowmentManager {

  void createEndowment(String endowmentName,
      Map<String, Integer> endowmentMapping, Integer frequency,
      ICart allTradeables, Double money);

  IInitialEndowment getEndowment();

  void lock();

}
