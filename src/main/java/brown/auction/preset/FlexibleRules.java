package brown.auction.preset;

import brown.auction.rules.*;

public class FlexibleRules extends AbsMarketRules {
    public FlexibleRules(IAllocationRule aRule,
                         IPaymentRule pRule,
                         IQueryRule qRule,
                         IGroupingRule gRule,
                         IActivityRule actRule,
                         IInformationRevelationPolicy irPolicy,
                         ITerminationCondition tCondition,
                         IRecordKeepingRule records
                         ) {
        super(aRule, pRule, qRule, gRule, actRule, irPolicy, tCondition, records);
    }

    public AbsMarketRules copy() {
        return new FlexibleRules(aRule, pRule, qRule, gRule, actRule, infoPolicy, innerTCondition, rRule);
    }
}
