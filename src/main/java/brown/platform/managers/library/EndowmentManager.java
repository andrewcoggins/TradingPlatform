package brown.platform.managers.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import brown.logging.library.PlatformLogging;
import brown.platform.accounting.IInitialEndowment;
import brown.platform.accounting.library.InitialEndowment;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.managers.IEndowmentManager;

public class EndowmentManager implements IEndowmentManager {

  private boolean lock;

  private Map<String, Integer> endowmentAlias;
  private Map<String, ICart> endowments;
  private Map<String, Double> endowmentMoneyAlias;

  public EndowmentManager() {
    this.lock = false;
    this.endowmentAlias = new HashMap<String, Integer>();
    this.endowments = new HashMap<String, ICart>();
    this.endowmentMoneyAlias = new HashMap<String, Double>();
  }

  @Override
  public void createEndowment(String endowmentName,
      Map<String, Integer> endowmentMapping, Integer frequency,
      ICart allTradeables, Double money) {
    if (!this.lock) {
      endowmentAlias.put(endowmentName, frequency);
      List<IItem> endowmentItems = new LinkedList<IItem>();
      endowmentMapping.keySet().forEach(
          tName -> endowmentItems.add(allTradeables.getItemByName(tName)));
      endowments.put(endowmentName, new Cart(endowmentItems));
      endowmentMoneyAlias.put(endowmentName, money);
    } else {
      PlatformLogging.log("Creation denied: endowment manager locked.");
    }
  }

  @Override
  public IInitialEndowment getEndowment() {
    // need to get based on the frequency.
    List<String> freqStrings = new LinkedList<String>();
    for (String s : endowmentAlias.keySet()) {
      for (int i = 0; i < endowmentAlias.get(s); i++) {
        freqStrings.add(s);
      }
    }
    int freq = 0;
    for (String s : endowmentAlias.keySet()) {
      freq += this.endowmentAlias.get(s);
    }
    int randomNum = ThreadLocalRandom.current().nextInt(0, freq);
    String endowmentString = freqStrings.get(randomNum);
    ICart endowment = this.endowments.get(endowmentString);
    Double endowmentMoney = this.endowmentMoneyAlias.get(endowmentString);
    return new InitialEndowment(endowmentMoney, endowment);
  }

  @Override
  public void lock() {
    this.lock = true;
  }

}
