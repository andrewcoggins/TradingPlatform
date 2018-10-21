package brown.platform.accounting.library;

import brown.mechanism.tradeable.ITradeable;
import brown.platform.accounting.IInitialEndownment;
import java.util.List;

/**
 * Initial Endowment specifies what an agent has at the beginning of a game.
 */
public class InitialEndowment implements IInitialEndownment {

    public final double money;
    public final List<ITradeable> goods;

    /**
     * Agent's initial endowment.
     * @param money starting money
     * @param goods starting goods.
     */
    public InitialEndowment(double money, List<ITradeable> goods) {
        this.money = money;
        this.goods = goods;
    }
}
