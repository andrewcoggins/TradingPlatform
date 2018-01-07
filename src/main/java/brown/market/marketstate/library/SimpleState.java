package brown.market.marketstate.library;

import java.util.List;
import java.util.Set;

import brown.accounting.BundleType;
import brown.accounting.Order;
import brown.accounting.bidbundle.AbsBidBundle;
import brown.accounting.bidbundle.Allocation;
import brown.channels.MechanismType;
import brown.market.marketstate.IMarketState;
import brown.messages.library.BidMessage;
import brown.messages.library.BidRequestMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.TradeRequestMessage;
import brown.todeprecate.PaymentType;

public class SimpleState implements IMarketState {

  @Override
  public void addBid(BidMessage bid) {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public void clear() {
    
  }

  @Override
  public List<BidMessage> getBids() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Integer getID() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setLastPayments(List<Order> payments) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public List<Order> getLastPayments() {
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
  public void setReserve(AbsBidBundle o) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public AbsBidBundle getbundleReserve() {
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
  public Allocation getAllocation() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BidRequestMessage getRequest() {
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
  public Set<BidMessage> getReserve() {
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
  public GameReportMessage getReport() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setTime(long t) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setAllocation(Allocation alloc) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setRequest(BidRequestMessage request) {
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
  public void setReserve(Set<BidMessage> r) {
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
  public void setReport(GameReportMessage g) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public List<Order> getPayments() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PaymentType getPaymentType() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public AbsBidBundle getReserveBundle() {
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
  public void setPaymentType(PaymentType p) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setReserveBundle(AbsBidBundle b) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setShort(boolean b) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public TradeRequestMessage getTRequest() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setTRequest(TradeRequestMessage t) {
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

  @Override
  public boolean getInnerOver() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setInnerOver(boolean b) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean getOuterOver() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setOuterOver(boolean b) {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public void incrementOuter() {
    
  }
  
  @Override
  public Integer getOuterRuns() {
    return null;
  }
	
}
