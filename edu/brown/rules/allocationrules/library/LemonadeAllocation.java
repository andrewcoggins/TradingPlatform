package brown.rules.allocationrules.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import brown.assets.accounting.Account;
import brown.assets.value.EndState;
import brown.bundles.BidBundle;
import brown.bundles.BundleType;
import brown.channels.MechanismType;
import brown.marketinternalstates.MarketInternalState;
import brown.messages.auctions.Bid;
import brown.messages.auctions.BidRequest;
import brown.messages.markets.GameReport;
import brown.messages.markets.LemonadeReport;
import brown.rules.allocationrules.AllocationRule;
import brown.tradeables.Asset;
import brown.valuable.library.Tradeable;

public class LemonadeAllocation implements AllocationRule {

  private MechanismType TYPE; 
  private Integer ticks; 
  private Integer ID;
  private final Integer SIZE = 12; 
  private int[] slots;
  
  /*
   * a lemonade allocation has an array of 12 positions.
   */
  public LemonadeAllocation() {
    this.ID = null;
    this.slots = new int[SIZE];
  }
  
  public LemonadeAllocation(Integer ID) {
    this.ID = ID;
    this.slots = new int[SIZE];
  }
  
  @Override
  public void tick(long time) {
    if (time == -1) {
      this.ticks = 0;
    } else {
      this.ticks++;
    }
  }


  @Override
  public Map<Integer, Set<Asset>> getAllocations(Set<Bid> bids,
      Set<Asset> items) {
    //create a list of the people in the game at each position.
    @SuppressWarnings("unchecked")
    Map<Integer, Set<Asset>> securities = new HashMap<Integer, Set<Asset>>();
    List<Integer>[] positions = (List<Integer>[]) new List[SIZE];
    for(Bid bid : bids) {
      Integer place = (int) bid.Bundle.getCost() - 1;
      if(place >= 0 && place < 12) {
        if (positions[place] == null) {
          positions[place] = new LinkedList<Integer>();
        }
        positions[place].add(bid.AgentID);
       }  
    }
    //now run the logic of the game. 
    for (int i = 0; i < SIZE; i++) {
      //report on the status of the game.
      if (positions[i] == null) {
        continue;
      }
      else {
        this.slots[i] = positions[i].size();
      }
      //calculate payoffs based on nearest positions.
      double payoff = 0;

      int before = i;
      int after = i;
      for (int next = (i == 11 ? 0 : i+1); next != i; next++) {
        if (positions[next] !=  null) {
          if (after == i) {
            after = next;
          }
          before = next;
        }
        
        if (next == 11) {
          next = -1;
        }
      }
      payoff = after > i ? after - i : 11-i + after;
      payoff += before < i ? i - before : before;
      payoff /= (double) positions[i].size();
      //give people the payments. 
      
      for (Integer person : positions[i]) {
        Set<Asset> goods = new HashSet<Asset>();
        final double pay = payoff;
        //this puts money in your account.
        //wtf
        Function<EndState, List<Account>> giveMoney = f -> {
          List<Account> list = new LinkedList<Account>();
          list.add(new Account(person).add(pay));
          return list;
        };
        goods.add(new Asset(new Tradeable(), 1, giveMoney));
        securities.put(person, goods);
      }
    }
    return null;
  }

  @Override
  public BidRequest getBidRequest(Set<Bid> bids, Integer ID) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isPrivate() {
    return true;
  }

  @Override
  public boolean isOver() {
    return this.ticks > 2;
  }

  @Override
  public BundleType getBundleType() {
    return BundleType.Simple;
  }

  @Override
  public Set<Bid> withReserve(Set<Bid> bids) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isValid(Bid bid, Set<Bid> bids) {
    if (bid.AgentID == null || bid.Bundle == null
        || bid.Bundle.getCost() < 1 || bid.Bundle.getCost() > 12) {
      return false;
    }
    
    for (Bid b : bids) {
      if (b.AgentID.equals(bid.AgentID)) {
        return false;
      }
    }
    
    return true;
  }

  @Override
  public MechanismType getAllocationType() {
    return TYPE.Lemonade; 
  }

  @Override
  public GameReport getReport() {
    return new LemonadeReport(slots);
  } 
  
  @Override
  public BidBundle getAllocation(MarketInternalState state) {
    return null;
  }
  
}