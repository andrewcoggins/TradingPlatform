package brown.platform.domain;

import brown.platform.managers.IAccountManager;
import brown.platform.managers.IEndowmentManager;
import brown.platform.managers.IItemManager;
import brown.platform.managers.IValuationManager;

/**
 * Interface IDomain
 * Characterized by items, valuation distributions, and endowments.
 */
public interface IDomain {

  /**
   * get IDomainManager's ItemManager
   * 
   * @return
   */
  public IItemManager getItemManager(); 
  
  /**
   * get IDomainManager's ValuationManager
   * 
   * @return
   */
  public IValuationManager getValuationManager(); 
  
  /**
   * get IDomainManager's EndowmentManager
   * 
   * @return
   */
  public IEndowmentManager getEndowmentManager(); 
  
  /**
   * get IDomainManager's AccountManager
   * 
   * @return
   */
  public IAccountManager getAccountManager(); 
  
}
