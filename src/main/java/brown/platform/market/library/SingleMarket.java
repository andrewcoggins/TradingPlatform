package brown.platform.market.library;

import brown.platform.market.IMarket;
import brown.platform.market.IMarketBlock;

public class SingleMarket implements IMarketBlock {

    public final IMarket market;

    public SingleMarket(IMarket market) {
        this.market = market;
    }
}
