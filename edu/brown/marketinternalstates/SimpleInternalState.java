package brown.marketinternalstates;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.assets.accounting.Order;
import brown.bundles.BidBundle;
import brown.bundles.BundleType;
import brown.bundles.MarketState;
import brown.bundles.SimpleBidBundle;
import brown.channels.MechanismType;
import brown.messages.auctions.Bid;
import brown.messages.auctions.BidRequest;
import brown.messages.markets.GameReport;
import brown.messages.markets.TradeRequest;
import brown.rules.paymentrules.PaymentType;
import brown.tradeables.Asset;
import brown.valuable.library.Tradeable;

public class SimpleInternalState implements MarketInternalState {

  @Override
  public void addBid(Bid bid) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setAllocation(BidBundle allocation) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public BidBundle getAllocation() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Bid> getBids() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<Asset> getTradeables() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Integer getID() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setPayments_two(List<Order> payments) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public List<Order> getPayments_two() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void tick(long time) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public int getTicks() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void setReserve(BidBundle o) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public BidBundle getbundleReserve() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void clearBids() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public double getIncrement() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void setMaximizingRevenue(boolean b) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean isMaximizingRevenue() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public int getEligibility() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public long getTime() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public BidBundle getAlloc() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<Integer, Set<Asset>> getAllocs() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BidRequest getRequest() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean getPrivate() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean getOver() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public BundleType getBundleType() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<Bid> getReserve() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean getValid() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public MechanismType getMType() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public GameReport getReport() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setTime(long t) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setAlloc(BidBundle alloc) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setAllocs(Map<Integer, Set<Asset>> allocs) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setRequest(BidRequest request) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setPrivate(boolean p) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setOver(boolean o) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setBundleType(BundleType b) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setReserve(Set<Bid> r) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setValid(boolean v) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setMType(MechanismType m) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setReport(GameReport g) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public List<Order> getPayments() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<BidBundle, Set<Asset>> getOPayments() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PaymentType getPaymentType() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BidBundle getReserveBundle() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean permitShort() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setPayments(List<Order> orders) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setOPayments(Map<BidBundle, Set<Asset>> m) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setPaymentType(PaymentType p) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setReserveBundle(BidBundle b) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setShort(boolean b) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public TradeRequest getTRequest() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setTRequest(TradeRequest t) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean getAcceptable() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setAcceptable(boolean b) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean getTOver() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setTOver(boolean b) {
    // TODO Auto-generated method stub
    
  }
//	private final double INCREMENT = 20.0;
//	private final Integer ID;
//	private final List<Bid> BIDS;
//	private final Set<Asset> TRADEABLES;
//	
//	private BidBundle lastAlloc;
//	private List<Order> lastPayments;
//	private int ticks;
//	private BidBundle reserve;
//	private boolean maximizing;
//	
//	public SimpleInternalState(Integer ID, Set<Asset> tradeables) {
//		this.BIDS = new LinkedList<Bid>();
//		this.lastAlloc = null;
//		this.lastPayments = null;
//		this.TRADEABLES = tradeables;
//		this.ID = ID;
//		this.ticks = 0;
//		Map<Tradeable, MarketState> reserve = new HashMap<Tradeable, MarketState>();
//		for (Asset t : this.TRADEABLES) {
//			reserve.put(t.getType(), new MarketState(null,0));
//		}
//		this.reserve = new SimpleBidBundle(reserve);
//		this.maximizing = false;
//	}
//	
//	public SimpleInternalState(Integer ID, Set<Asset> tradeables, Map<Tradeable, MarketState> reserve) {
//		this.BIDS = new LinkedList<Bid>();
//		this.lastAlloc = null;
//		this.lastPayments = null;
//		this.TRADEABLES = tradeables;
//		this.ID = ID;
//		this.ticks = 0;
//		this.reserve = new SimpleBidBundle(reserve);
//	}
//
//	@Override
//	public void addBid(Bid bid) {
//	  System.out.println("A BID ADDED " + (SimpleBidBundle) bid.Bundle);
//		this.ticks = 0;
//		this.BIDS.add(bid);
//	}
//
//	@Override
//	public void setAllocation(BidBundle allocation) {
//		this.lastAlloc = allocation;
//	}
//
//	@Override
//	public List<Bid> getBids() {
//		return this.BIDS;
//	}
//
//	@Override
//	public Set<Asset> getTradeables() {
//		return this.TRADEABLES;
//	}
//
//	@Override
//	public BidBundle getAllocation() {
//		return this.lastAlloc;
//	}
//
//	@Override
//	public Integer getID() {
//		return this.ID;
//	}
//
//	@Override
//	public void setPayments(List<Order> payments) {
//		this.lastPayments = payments;
//	}
//
//	@Override
//	public List<Order> getPayments() {
//		return this.lastPayments;
//	}
//
//	@Override
//	public void tick(long time) {
//		this.ticks++;		
//	}
//
//	@Override
//	public int getTicks() {
//		return this.ticks;
//	}
//
//	@Override
//	public void setReserve(BidBundle o) {
//		this.reserve = o;
//	}
//
//	@Override
//	public BidBundle getReserve() {
//		return this.reserve;
//	}
//
//	@Override
//	public void clearBids() {
//		this.BIDS.clear();
//	}
//
//	@Override
//	public double getIncrement() {
//		return this.INCREMENT;
//	}
//
//	@Override
//	public void setMaximizingRevenue(boolean b) {
//		this.maximizing = b;
//	}
//
//	@Override
//	public boolean isMaximizingRevenue() {
//		return this.maximizing;
//	}
//
//	@Override
//	public int getEligibility() {
//		int elig = 0;
//		if (this.reserve == null) {
//			return 0;
//		}
//		SimpleBidBundle bundle = (SimpleBidBundle) this.reserve;
//		for (Tradeable type : bundle.getDemandSet()) {
//			MarketState state = bundle.getBid(type);
//			if (state != null && state.AGENTID != null) {
//				elig+=1;
//			}
//		}
//		return elig;
//	}
	
}
