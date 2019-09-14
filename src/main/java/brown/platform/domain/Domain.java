package brown.platform.domain;

import brown.platform.managers.IAccountManager;
import brown.platform.managers.IEndowmentManager;
import brown.platform.managers.IItemManager;
import brown.platform.managers.IValuationManager;

/**
 * a domain consists of Items, an a valuation distribution, and endowments.
 */
public class Domain implements IDomain {

    private IItemManager itemManager;
    private IValuationManager valuationManager;
    private IEndowmentManager endowmentManager;
    private IAccountManager accountManager;

    public Domain (IItemManager tManager, IValuationManager valuationManager,
                   IEndowmentManager endowmentManager, IAccountManager acctManager) {
        this.itemManager = tManager;
        this.valuationManager = valuationManager;
        this.endowmentManager = endowmentManager;
        this.accountManager = acctManager;
    }
    
    @Override
    public IItemManager getItemManager() {
      return this.itemManager;
    }

    @Override
    public IValuationManager getValuationManager() {
      return valuationManager;
    }

    @Override
    public IEndowmentManager getEndowmentManager() {
      return this.endowmentManager;
    }
    
    @Override
    public IAccountManager getAccountManager() {
      return this.accountManager;
    }
    
    @Override
    public String toString() {
      return "Domain [tManager=" + itemManager + ", valuationManager=" + valuationManager
          + ", acctManager=" + accountManager + ", endowmentManager="
          + endowmentManager + "]";
    }
   
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result =
          prime * result + ((accountManager == null) ? 0 : accountManager.hashCode());
      result = prime * result
          + ((endowmentManager == null) ? 0 : endowmentManager.hashCode());
      result = prime * result + ((itemManager == null) ? 0 : itemManager.hashCode());
      result =
          prime * result + ((valuationManager == null) ? 0 : valuationManager.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Domain other = (Domain) obj;
      if (accountManager == null) {
        if (other.accountManager != null)
          return false;
      } else if (!accountManager.equals(other.accountManager))
        return false;
      if (endowmentManager == null) {
        if (other.endowmentManager != null)
          return false;
      } else if (!endowmentManager.equals(other.endowmentManager))
        return false;
      if (itemManager == null) {
        if (other.itemManager != null)
          return false;
      } else if (!itemManager.equals(other.itemManager))
        return false;
      if (valuationManager == null) {
        if (other.valuationManager != null)
          return false;
      } else if (!valuationManager.equals(other.valuationManager))
        return false;
      return true;
    }
    
}
