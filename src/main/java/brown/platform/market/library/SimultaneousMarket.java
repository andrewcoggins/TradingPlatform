package brown.platform.market.library;

import brown.platform.market.IMarketBlock;
import java.util.List;

public class SimultaneousMarket implements IMarketBlock {

    public final List<AbsMarketRules> markets;

    public SimultaneousMarket(List<AbsMarketRules> markets) {
        this.markets = markets;
    }
}
