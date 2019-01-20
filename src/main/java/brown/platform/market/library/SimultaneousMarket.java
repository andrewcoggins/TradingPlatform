package brown.platform.market.library;

import java.util.List;
import java.util.Map;

import brown.mechanism.tradeable.ITradeable;
import brown.platform.market.IMarketBlock;
import brown.platform.market.IMarketRules;

public class SimultaneousMarket implements IMarketBlock {

    public final List<IMarketRules> markets;
    public final List<Map<String, List<ITradeable>>> marketTradeables; 

    public SimultaneousMarket(List<IMarketRules> markets, List<Map<String, List<ITradeable>>> marketTradeables) {
        this.markets = markets;
        this.marketTradeables = marketTradeables; 
    }
}
