package brown.auction.value.manager;
import brown.auction.value.distribution.IValuationDistribution;

public class ValuationManager {

    public final IValuationDistribution distribution;

    public ValuationManager(IValuationDistribution distribution) {
        this.distribution = distribution;
    }
}
