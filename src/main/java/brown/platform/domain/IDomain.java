package brown.platform.domain;

import brown.platform.managers.IAccountManager;
import brown.platform.managers.IEndowmentManager;
import brown.platform.managers.IItemManager;
import brown.platform.managers.IValuationManager;

/**
 * Interface IDomain- consists of tradeables, an account manager, and a valuation distribution all stored together.
 */
public interface IDomain {

  public IItemManager getTradeableManager(); 
  
  public IAccountManager getAccountManager(); 
  
  public IEndowmentManager getEndowmentManager(); 
  
  public IValuationManager getValuationManager(); 
  
  
}
