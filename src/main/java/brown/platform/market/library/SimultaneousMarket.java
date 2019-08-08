package brown.platform.market.library;

import java.util.List;

import brown.platform.item.ICart;
import brown.platform.market.IFlexibleRules;
import brown.platform.market.IMarketBlock;

/**
 * A Simultaneous market contains one or multiple markets, to be run at the same time
 * in the Simulation. 
 * @author andrewcoggins
 *
 */
public class SimultaneousMarket implements IMarketBlock {

    private List<IFlexibleRules> markets;
    private List<ICart> marketCarts; 

    /**
     * Constructor includes a list of IIFlexibleRules corresponding to the markets 
     * to be opened, and a list of ICart corresponding to the items each market will 
     * be open for. 
     * @param markets
     * @param marketCarts
     */
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
