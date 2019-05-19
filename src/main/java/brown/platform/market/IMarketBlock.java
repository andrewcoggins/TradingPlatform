package brown.platform.market;

import java.util.List;

import brown.platform.item.ICart;

public interface IMarketBlock {

  public List<IFlexibleRules> getMarkets();

  public List<ICart> getMarketTradeables();

}
