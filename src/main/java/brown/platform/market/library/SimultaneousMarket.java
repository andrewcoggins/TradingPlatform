package brown.platform.market.library;

import java.util.List;

import brown.platform.item.ICart;
import brown.platform.market.IFlexibleRules;
import brown.platform.market.IMarketBlock;

public class SimultaneousMarket implements IMarketBlock {

    private List<IFlexibleRules> markets;
    private List<ICart> marketCarts; 

    public SimultaneousMarket(List<IFlexibleRules> markets, List<ICart> marketCarts) {
        this.markets = markets;
        this.marketCarts = marketCarts; 
    }
    
    public List<IFlexibleRules> getMarkets() {
      return this.markets; 
    }
    
    public List<ICart> getMarketCarts() {
      return this.marketCarts; 
    }
}
