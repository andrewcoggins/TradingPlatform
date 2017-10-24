package brown.rules.allocationrules.library;

import java.util.Map;
import java.util.Set;

import brown.bundles.BidBundle;
import brown.bundles.BundleType;
import brown.channels.MechanismType;
import brown.marketinternalstates.MarketInternalState;
import brown.messages.auctions.Bid;
import brown.messages.auctions.BidRequest;
import brown.messages.markets.GameReport;
import brown.rules.allocationrules.AllocationRule;
import brown.tradeables.Asset;

public class LemonadeAllocation implements AllocationRule {

  private MechanismType TYPE; 
  private Integer ticks; 
  private Integer ID;
  private int[] slots;
  
  @Override
  public void tick(long time) {
    if (time == -1) {
      this.ticks = 0;
    } else {
      this.ticks++;
    }
  }

  @Override
  public BidBundle getAllocation(MarketInternalState state) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<Integer, Set<Asset>> getAllocations(Set<Bid> bids,
      Set<Asset> items) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BidRequest getBidRequest(Set<Bid> bids, Integer ID) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isPrivate() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isOver() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public BundleType getBundleType() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<Bid> withReserve(Set<Bid> bids) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isValid(Bid bid, Set<Bid> bids) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public MechanismType getAllocationType() {
    return TYPE.Lemonade; 
  }

  @Override
  public GameReport getReport() {
    // TODO Auto-generated method stub
    return null;
  } 
  
}