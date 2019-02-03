package brown.platform.world.library;

import brown.platform.managers.IAccountManager;
import brown.platform.managers.IEndowmentManager;
import brown.platform.managers.ITradeableManager;
import brown.platform.managers.IValuationManager;
import brown.platform.world.IDomain;


/**
 * a domain consists of Tradeables, an IValuationGenerator, and an AccountManager.
 */
public class Domain implements IDomain {

    public final ITradeableManager tManager;
    public final  IValuationManager valuation;
    public final IAccountManager acctManager;
    public final IEndowmentManager endowmentManager;

    public Domain (ITradeableManager tManager, IValuationManager valuation, IAccountManager acctManager,
                   IEndowmentManager endowmentManager) {
        this.tManager = tManager;
        this.valuation = valuation;
        this.acctManager = acctManager;
        this.endowmentManager = endowmentManager;
    }

    @Override
    public String toString() {
      return "Domain [tManager=" + tManager + ", valuation=" + valuation
          + ", acctManager=" + acctManager + ", endowmentManager="
          + endowmentManager + "]";
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result =
          prime * result + ((acctManager == null) ? 0 : acctManager.hashCode());
      result = prime * result
          + ((endowmentManager == null) ? 0 : endowmentManager.hashCode());
      result = prime * result + ((tManager == null) ? 0 : tManager.hashCode());
      result =
          prime * result + ((valuation == null) ? 0 : valuation.hashCode());
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
      if (acctManager == null) {
        if (other.acctManager != null)
          return false;
      } else if (!acctManager.equals(other.acctManager))
        return false;
      if (endowmentManager == null) {
        if (other.endowmentManager != null)
          return false;
      } else if (!endowmentManager.equals(other.endowmentManager))
        return false;
      if (tManager == null) {
        if (other.tManager != null)
          return false;
      } else if (!tManager.equals(other.tManager))
        return false;
      if (valuation == null) {
        if (other.valuation != null)
          return false;
      } else if (!valuation.equals(other.valuation))
        return false;
      return true;
    }
    
    
}
