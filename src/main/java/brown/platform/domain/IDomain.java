package brown.platform.domain;

import brown.platform.managers.IAccountManager;
import brown.platform.managers.IEndowmentManager;
import brown.platform.managers.IItemManager;
import brown.platform.managers.IValuationManager;

/**
 * Interface IDomain- consists of tradeables, an account manager, and a valuation distribution all stored together.
 */
public interface IDomain {

  /**
   * get IDomainManager's ItemManager
   * @return
   */
  public IItemManager getItemManager(); 
  
  /**
   * getIDomainManager's AccountManager
   * @return
   */
  public IAccountManager getAccountManager(); 
  
  /**
   * getIDomainManager's EndowmentManager
   * @return
   */
  public IEndowmentManager getEndowmentManager(); 
  
  /**
   * getIDomainManager's ValuationManager
   * @return
   */
  public IValuationManager getValuationManager(); 
  
}
