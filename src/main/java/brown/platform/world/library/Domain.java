package brown.platform.world.library;

import brown.auction.value.distribution.IValuationDistribution;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.accounting.IAccountManager;
import brown.platform.world.IDomain;

import java.util.List;

/**
 * a domain consists of Tradeables, an IValuationGenerator, and an AccountManager.
 */
public class Domain implements IDomain {

    public final List<ITradeable> tradeables;
    public final  IValuationDistribution valuation;
    public final IAccountManager acctManager;

    public Domain (List<ITradeable> tradeables, IValuationDistribution valuation, IAccountManager acctManager) {
        this.tradeables = tradeables;
        this.valuation = valuation;
        this.acctManager = acctManager;
    }
}
