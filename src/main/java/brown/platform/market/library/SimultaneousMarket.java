package brown.platform.market.library;

import java.util.List;
import java.util.Map;

import brown.platform.market.IFlexibleRules;
import brown.platform.market.IMarketBlock;
import brown.platform.market.IMarketRules;
import brown.platform.tradeable.ITradeable;

public class SimultaneousMarket implements IMarketBlock {

    private List<IFlexibleRules> markets;
    private List<Map<String, List<ITradeable>>> marketTradeables; 

    public SimultaneousMarket(List<IFlexibleRules> markets, List<Map<String, List<ITradeable>>> marketTradeables) {
        this.markets = markets;
        this.marketTradeables = marketTradeables; 
    }
    
    public List<IFlexibleRules> getMarkets() {
      return this.markets; 
    }
    
    public List<Map<String, List<ITradeable>>> getMarketTradeables() {
      return this.marketTradeables; 
    }
}
