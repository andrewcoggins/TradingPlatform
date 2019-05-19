package brown.platform.market.library;

import java.util.List;

import brown.platform.item.ICart;
import brown.platform.market.IFlexibleRules;
import brown.platform.market.IMarketBlock;

public class SimultaneousMarket implements IMarketBlock {

    private List<IFlexibleRules> markets;
    private List<ICart> marketTradeables; 

    public SimultaneousMarket(List<IFlexibleRules> markets, List<ICart> marketTradeables) {
        this.markets = markets;
        this.marketTradeables = marketTradeables; 
    }
    
    public List<IFlexibleRules> getMarkets() {
      return this.markets; 
    }
    
    public List<ICart> getMarketTradeables() {
      return this.marketTradeables; 
    }
}
