package brown.auction.value.manager;
import brown.auction.value.distribution.IValuationDistribution;
import brown.logging.library.PlatformLogging;

import java.util.Map;
import java.util.HashMap;

public class ValuationManager implements IValuationManager {

    private Map<Integer, IValuationDistribution> distributions;
    private boolean lock;

    public ValuationManager() {
        this.distributions = new HashMap<Integer, IValuationDistribution>();
        this.lock = false;
    }

    public void createValuation(int auctionID, IValuationDistribution distribution){
        if (!this.lock) {
            this.distributions.put(auctionID, distribution);
        } else {
            PlatformLogging.log("Creation denied: valuation manager locked.");
        }
    }

    public IValuationDistribution getDistribution(int auctionID) {
        return this.distributions.get(auctionID);
    }

    public void lock() {
        this.lock = true;
    }
}
