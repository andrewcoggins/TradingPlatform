package brown.platform.accounting.library;

import brown.logging.library.PlatformLogging;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.accounting.IEndowmentManager;
import brown.platform.accounting.IInitialEndownment;

import java.util.List;

public class EndowmentManager implements IEndowmentManager {

    private double money;
    private List<ITradeable> tradeables;
    private boolean lock;

    public EndowmentManager() {
        this.lock = false;
    }

    public void createEndowment(double money, List<ITradeable> goods) {
        if (!lock) {
            this.money = money;
            this.tradeables = goods;
            this.lock = true;
        } else {
            PlatformLogging.log("Creation denied: endowment manager locked.");
        }
    }
    public IInitialEndownment getEndowment() {
        return new InitialEndowment(this.money, this.tradeables);
    }

}
