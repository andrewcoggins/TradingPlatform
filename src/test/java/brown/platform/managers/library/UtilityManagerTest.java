package brown.platform.managers.library;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.distribution.library.AdditiveValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.generator.library.NormalValGenerator;
import brown.auction.value.valuation.IGeneralValuation;
import brown.auction.value.valuation.ISpecificValuation;
import brown.auction.value.valuation.library.GeneralValuation;
import brown.platform.accounting.IAccount;
import brown.platform.accounting.library.Account;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;
import brown.platform.managers.IUtilityManager;

public class UtilityManagerTest {

  @Test
  public void testUtilityManager() {

    IUtilityManager utilManager = new UtilityManager();

    utilManager.addAgentRecord(0);
    utilManager.addAgentRecord(1);

    assertTrue(utilManager.getAgentRecords().keySet().size() == 2);

    Map<Integer, IAccount> accounts = new HashMap<Integer, IAccount>();

    Map<Integer, IGeneralValuation> valuations =
        new HashMap<Integer, IGeneralValuation>();

    List<IItem> agentItems = new LinkedList<IItem>();
    agentItems.add(new Item("test", 1));;

    List<IItem> agentItemsTwo = new LinkedList<IItem>();
    agentItemsTwo.add(new Item("test", 1));

    ICart agentCart = new Cart(agentItems);
    ICart agentCartTwo = new Cart(agentItemsTwo);

    IAccount acctOne = new Account(0, 100.0, agentCart);
    IAccount acctTwo = new Account(0, 100.0, agentCartTwo);

    accounts.put(0, acctOne);
    accounts.put(1, acctTwo);

    Map<List<IItem>, ISpecificValuation> valOne =
        new HashMap<List<IItem>, ISpecificValuation>();
    Map<List<IItem>, ISpecificValuation> valTwo =
        new HashMap<List<IItem>, ISpecificValuation>();

    valuations.put(0, new GeneralValuation(valOne));
    valuations.put(0, new GeneralValuation(valTwo));

    List<IValuationGenerator> gen = new LinkedList<IValuationGenerator>();
    List<Double> valParams = new LinkedList<Double>();
    valParams.add(2.0);
    valParams.add(0.1);

    gen.add(new NormalValGenerator(valParams));
    IValuationDistribution valDist =
        new AdditiveValuationDistribution(new Cart(agentItems), gen);
    ISpecificValuation valuation = valDist.sample();

    valOne.put(agentItems, valuation);
    valTwo.put(agentItems, valuation);

    IGeneralValuation genOne = new GeneralValuation(valOne);
    IGeneralValuation genTwo = new GeneralValuation(valTwo);

    valuations.put(0, genOne);
    valuations.put(1, genTwo);

    utilManager.updateUtility(accounts, valuations);

    Map<Integer, List<Double>> agentRecords = utilManager.getAgentRecords();

    for (Integer id : agentRecords.keySet()) {
      List<Double> utils = agentRecords.get(id);
      assertTrue(utils.size() == 1);
      assertTrue(utils.get(0) > 100.0);
    }
  }

}
