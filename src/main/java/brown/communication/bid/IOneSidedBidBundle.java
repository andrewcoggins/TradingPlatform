package brown.communication.bid;

import java.util.Map;

import brown.platform.tradeable.ITradeable;

public interface IOneSidedBidBundle extends IBid {

  public Map<ITradeable, Double> getOneSidedBids(); 
}
