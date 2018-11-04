package brown.auction.value.manager;


import brown.auction.value.distribution.IValuationDistribution;

public interface IValuationManager {

    void createValuation(int auctionID, IValuationDistribution distribution);

    IValuationDistribution getDistribution(int auctionID);

    void lock();
}
