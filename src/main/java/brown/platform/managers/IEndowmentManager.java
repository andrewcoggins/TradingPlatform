package brown.platform.managers;

import java.util.List;
import java.util.Map;

import brown.mechanism.tradeable.ITradeable;
import brown.platform.accounting.IInitialEndowment;

public interface IEndowmentManager {

    void createEndowment(String endowmentName, Map<String, Integer> endowmentMapping,
        Map<String, List<String>> includeMapping, Integer frequency, Map<String, List<ITradeable>> allTradeables, 
        Double money);

    IInitialEndowment getEndowment();
    
    void lock(); 

}

