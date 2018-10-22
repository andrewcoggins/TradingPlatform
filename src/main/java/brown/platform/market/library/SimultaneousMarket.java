package brown.platform.market.library;

import brown.platform.market.IMarketBlock;
import brown.platform.market.IMarket;
import java.util.List;

public class SimultaneousMarket implements IMarketBlock {

    public final List<IMarket> markets;

    public SimultaneousMarket(List<IMarket> markets) {
        this.markets = markets;
    }
}
