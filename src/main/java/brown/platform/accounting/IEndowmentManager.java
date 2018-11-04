package brown.platform.accounting;

import brown.mechanism.tradeable.ITradeable;
import java.util.List;

public interface IEndowmentManager {

    void createEndowment(double money, List<ITradeable> tradeables);

    IInitialEndownment getEndowment();

}

