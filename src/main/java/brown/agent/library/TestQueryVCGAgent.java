package brown.agent.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.agent.AbsSpecValAgent;
import brown.bid.interim.BidType;
import brown.bid.library.AuctionBid;
import brown.bid.library.QueryBid;
import brown.bidbundle.library.AuctionBidBundle;
import brown.bidbundle.library.QueryBundle;
import brown.channels.library.AuctionChannel;
import brown.channels.library.OpenOutcryChannel;
import brown.channels.library.QueryChannel;
import brown.exceptions.AgentCreationException;
import brown.logging.Logging;
import brown.setup.ISetup;
import brown.setup.library.SpecValSetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.ComplexTradeable;
import brown.tradeable.library.SimpleTradeable;

public class TestQueryVCGAgent extends AbsSpecValAgent {
  int low;
  int high;
  
  public TestQueryVCGAgent(String host, int port, ISetup gameSetup, String name ,int low, int high)
      throws AgentCreationException {
    super(host, port, gameSetup,name);
    // TODO Auto-generated constructor stub
    this.low = low;
    this.high=high;
  }

  @Override
  public void onQueryMarket(QueryChannel channel) {
    Set<ITradeable> tset1 = new HashSet<ITradeable>();
    Set<ITradeable> tset2 =  new HashSet<ITradeable>();
    
    for (int i = low; i< high; i++){
      tset1.add(new SimpleTradeable(i));
      tset2.add(new SimpleTradeable(99-i));
    }
    ComplexTradeable ct1 = new ComplexTradeable(0,tset1);
    ComplexTradeable ct2 = new ComplexTradeable(0,tset2);
    
    List<ComplexTradeable> ctlist = new LinkedList<ComplexTradeable>();
    ctlist.add(ct1);
    ctlist.add(ct2);
    
    channel.bid(this, new QueryBundle(new QueryBid(ctlist)));
  }

  @Override
  public void onClockMarket(OpenOutcryChannel channel) {
    Map<ITradeable, Double> reserves = channel.getReserve();
    Map<ITradeable, BidType> bid = new HashMap<ITradeable, BidType>();
    double total_price = 0;
    Set<ITradeable> goods = new HashSet<ITradeable>();
    for (int i = low ; i<high; i++){
      Logging.log("Reserve for good " + i + " is " + reserves.get(new SimpleTradeable(i)));
      total_price = total_price + reserves.get(new SimpleTradeable(i));
      bid.put(new SimpleTradeable(i), new BidType(reserves.get(new SimpleTradeable(i)),1));
      goods.add(new SimpleTradeable(i));
    }
    double my_val = this.valuation.getOrDefault(new ComplexTradeable(0,goods), 0.0);
    Logging.log("Total price: " + total_price + ", my val: " + my_val);
    if (my_val >= total_price){
      channel.bid(this, new AuctionBidBundle(bid));      
    }
  }

  public static void main(String[] args) throws AgentCreationException {

    new TestQueryVCGAgent("localhost", 2121,new SpecValSetup(),"test1",1,10);
    new TestQueryVCGAgent("localhost", 2121,new SpecValSetup(),"test2",10,20);
    new TestQueryVCGAgent("localhost", 2121,new SpecValSetup(),"test3",20,30);
    new TestQueryVCGAgent("localhost", 2121,new SpecValSetup(),"test4",1,30);
//    new TestQueryVCGAgent("localhost", 2121,new SpecValSetup(),"test3",15,35);
//    new TestQueryVCGAgent("localhost", 2121,new SpecValSetup(),"test4",1,11);            
//    new TestQueryVCGAgent("localhost", 2121,new SpecValSetup(),"test4",95,100);                
      while(true){}      
  }  
}
