package brown.user.agent.library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.auction.marketstate.IMarketPublicState;
import brown.auction.rules.activity.SMRAActivity;
import brown.auction.value.valuation.IGeneralValuation;
import brown.communication.bid.IBidBundle;
import brown.communication.bid.library.OneSidedBidBundle;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;
import brown.communication.messages.library.TradeMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;
import brown.user.agent.IAgent;

public abstract class AbsSpectrumAuctionAgent extends AbsAgent implements IAgent {
	protected static final double EPSILON = SMRAActivity.EPSILON;
	private IGeneralValuation valuation;
	private Integer position;
	private int round;
	private Integer auctionID;
	private Set<String> allocation;
	private List<Set<String>> allBids;
	private List<Map<String, Double>> allReserves;

	public AbsSpectrumAuctionAgent(String name) {
		super(name);
		this.valuation = null;
		this.auctionID = null;
		this.position = null;
		this.round = 0;
		this.name = name;
		this.allocation = new HashSet<>();
		this.allBids = new LinkedList<>();
		this.allReserves = new LinkedList<>();
	}

	@Override
	public void onInformationMessage(IInformationMessage informationMessage) {
		// noop
	}

	@Override
	public void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage) {
		synchronized (this) {
		this.auctionID = tradeRequestMessage.getAuctionID();
		
		// set minBids
		Map<String, Double> reserves = tradeRequestMessage.getState().getReserves();
		reserves.remove("demand_query");
		reserves.remove("reset");
		reserves.remove("position");
		
		Map<String, Double> minBids = new HashMap<>();
		for (Map.Entry<String, Double> ent : reserves.entrySet()) {
			if (this.isEligible(ent.getKey())) {
				minBids.put(ent.getKey(), ent.getValue());
			}
		}
		
		this.parseAllocation(tradeRequestMessage.getState());
		
		Map<String, Double> bids;
		if (this.isNationalBidder()) {
			bids = this.getNationalBids(Collections.unmodifiableMap(minBids));
		} else {
			bids = this.getRegionalBids(Collections.unmodifiableMap(minBids));
		}
		IBidBundle bundle = this.createBidBundle(bids);
		this.agentBackend.sendMessage(new TradeMessage(0, this.agentBackend.getPrivateID(), this.auctionID, bundle));
		
		this.allBids.add(bids.keySet());
		this.allReserves.add(reserves);
		
		this.round++;
		}
	}
	
	protected abstract void onAuctionStart();
	protected abstract Map<String, Double> getNationalBids(Map<String, Double> minBids);
	protected abstract Map<String, Double> getRegionalBids(Map<String, Double> minBids);
	protected abstract void onAuctionEnd(Map<Integer, Set<String>> allocations, Map<Integer, Double> payments, List<List<ITradeMessage>> tradeHistory);

	@Override
	public void onValuationMessage(IValuationMessage valuationMessage) {
		synchronized (this) {
		this.valuation = valuationMessage.getValuation();
		
		// get position
		ICart cart;
		cart = new Cart();
		cart.addToCart(new Item("position"));
		this.position = new Double(this.valuation.getValuation(cart)).intValue();
		
		this.allBids.clear();
		this.allReserves.clear();
		this.allocation.clear();
		this.round = 0;
		
		this.onAuctionStart();
		}
	}

	@Override
	public void onSimulationReportMessage(ISimulationReportMessage simReportMessage) {
		synchronized (this) {
		Map<Integer, Set<String>> alloc = new HashMap<>();
		Map<Integer, Double> payments = new HashMap<>();
		
		IMarketPublicState state = simReportMessage.getMarketResults().get(this.auctionID);
		for (Map.Entry<Integer, List<ICart>> ent : state.getAllocation().entrySet()) {
			alloc.putIfAbsent(ent.getKey(), new HashSet<>());
			for (ICart cart : ent.getValue()) {
				for (IItem item : cart.getItems()) {
					alloc.get(ent.getKey()).add(item.getName());
				}
			}
		}
		this.parseAllocation(state);
		
		for (IAccountUpdate upd : state.getPayments()) {
			payments.putIfAbsent(upd.getTo(), 0.0);
			payments.put(upd.getTo(), payments.get(upd.getTo()) + upd.getCost());
		}
		
		this.onAuctionEnd(alloc, payments, state.getTradeHistory());
		}
	}
	
	protected double getValuation(Collection<String> goods) {
		goods.remove("position");
		goods.remove("demand_query");
		goods.remove("reset");
		ICart cart = new Cart();
		goods.forEach(g -> cart.addToCart(new Item(g)));
		return this.valuation.getValuation(cart).doubleValue();
	}
	
	protected double getValuation(String... goods) {
		return this.getValuation(Arrays.asList(goods));
	}
	
	protected int getBidderPosition() {
		return this.position.intValue();
	}
	
	protected boolean isNationalBidder() {
		return this.getBidderPosition() == 7;
	}
	
	protected int getCurrentRound() {
		return this.round;
	}
	
	protected double clipBid(String good, double bid, Map<String, Double> minBids) {
		return Math.max(bid, minBids.getOrDefault(bid, 0.0));
	}
	
	protected Set<String> getTentativeAllocation() {
		return Collections.unmodifiableSet(this.allocation);
	}
	
	/*
	protected Set<String> demandQuery(Map<String, Double> prices) {
		Map<IItem, Double> itemMap = new HashMap<>();
		ICart pc = new Cart();
		prices.entrySet().forEach(ent -> pc.addToCart(new PricedItem(ent.getKey(), ent.getValue())));
		pc.addToCart(new Item("demand_query"));
		pc.addToCart(new Item("reset"));
		this.valuation.getValuation(pc);
		Set<String> result = new HashSet<>();
		for (String name : SATSUtil.ITEM_TO_GSVM_ID.keySet()) {
			ICart cart = new Cart();
			cart.addToCart(new Item("demand_query"));
			cart.addToCart(new Item(name));
			if (this.valuation.getValuation(cart) > 0) {
				result.add(name);
			}
		}
		return result;
	}
	*/
	
	private boolean checkRevealedPreference(Set<String> bids, Map<String, Double> reserve) {
		double newBundleNewReserve = 0.0;
		
		for (String s : bids) {
			newBundleNewReserve += reserve.getOrDefault(s, 0.0);
		}
		
		for (int r = 0; r < this.allBids.size(); r++) {
			double oldBundleNewReserve = 0.0;
			double newBundleOldReserve = 0.0;
			double oldBundleOldReserve = 0.0;
			
			for (String s : bids) {
				newBundleOldReserve += this.allReserves.get(r).getOrDefault(s, 0.0);
			}
			
			for (String s : this.allBids.get(r)) {
				oldBundleNewReserve += reserve.getOrDefault(s, 0.0);
				oldBundleOldReserve += this.allReserves.get(r).getOrDefault(s, 0.0);
			}
			
			if ((oldBundleNewReserve - 	oldBundleOldReserve) < (newBundleNewReserve - newBundleOldReserve)) {
				return false;
			}
		}
		return true;
	}
	
	protected boolean isEligible(String good) {
		return SATSUtil.ELIGIBLE_GOODS.get(this.position).contains(good);
	}
	
	protected boolean isValidBidBundle(Map<String, Double> myBids, Map<String, Double> minBids, boolean printWarnings) {
		if (myBids == null) {
			return true;
		}
		
		if (minBids == null) {
			return false;
		}
		
		boolean status = true;
		
		for (String s : myBids.keySet()) {
			if (myBids.get(s) == null) {
				if (printWarnings) {
					System.out.println("WARNING: null bid detected for good " + s);
				}
				status = false;
			}
		}
		
		for (String s : myBids.keySet()) {
			if (!this.isEligible(s)) {
				if (printWarnings) {
					System.out.println("WARNING: as bidder #" + this.position + ", you are ineligible to bid on good " + s);
				}
				status = false;
			}
		}
		
		if (!this.isNationalBidder() && myBids.size() > 4) {
			if (printWarnings) {
					System.out.println("WARNING: as regional bidder #" + this.position + ", you cannot bid on " + myBids.size() + " goods; you must choose 4 or fewer.");
				}
				status = false;
		}
		
		for (String s : myBids.keySet()) {
			if (myBids.containsKey(s) && minBids.containsKey(s) && myBids.get(s) < minBids.get(s)) {
				if (printWarnings) {
					System.out.println("WARNING: bid for good " + s + " is too low. Your bid is: " + myBids.get(s) + "; must be at least " + minBids.get(s));
				}
				status = false;
			}
		}
		
		if (!checkRevealedPreference(myBids.keySet(), minBids)) {
			if (printWarnings) {
				System.out.println("WARNING: failed revealed preference test");
			}
			status = false;
		}
		
		return status;
	}
	
	private IBidBundle createBidBundle(Map<String, Double> bids) {
		if (bids == null) {
			bids = new HashMap<>();
		}
		Map<ICart, Double> bundle = new HashMap<>();
		for (Map.Entry<String, Double> ent : bids.entrySet()) {
			ICart cart = new Cart();
			cart.addToCart(new Item(ent.getKey()));
			bundle.put(cart, ent.getValue());
		}
		IBidBundle bid = new OneSidedBidBundle(bundle);
		return bid;
	}
	
	private void parseAllocation(IMarketPublicState state) {
		Map<Integer, List<ICart>> allocations = new HashMap<>(state.getAllocation());
		Set<String> alloc = new HashSet<>();
		for (ICart cart : allocations.getOrDefault(this.agentBackend.getPublicID(), new ArrayList<>())) {
			for (IItem item : cart.getItems()) {
				alloc.add(item.getName());
			}
		}
		this.allocation = alloc;
	}

}
