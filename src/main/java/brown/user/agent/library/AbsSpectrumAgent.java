package brown.user.agent.library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.auction.marketstate.IMarketPublicState;
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

public abstract class AbsSpectrumAgent extends AbsAgent implements IAgent {
	private IGeneralValuation valuation;
	private Integer position;
	private int round;
	private Integer auctionID;
	private String name;
	private Map<Integer, List<ICart>> allocations;

	public AbsSpectrumAgent(String name) {
		super(name);
		this.valuation = null;
		this.auctionID = null;
		this.position = null;
		this.round = 0;
		this.name = name;
		this.allocations = new HashMap<>();
	}

	@Override
	public void onInformationMessage(IInformationMessage informationMessage) {
		// noop
	}

	@Override
	public void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage) {
		this.auctionID = tradeRequestMessage.getAuctionID();
		Map<String, Double> prices = tradeRequestMessage.getState().getReserves();
		Map<Integer, List<ICart>> allocations = new HashMap<>(tradeRequestMessage.getState().getAllocation());
		prices.remove("demand_query");
		prices.remove("reset");
		prices.remove("position");
		this.allocations = allocations;
		String s = "";
		List<String> st = new ArrayList<>(prices.keySet());
		Collections.sort(st);
		for (String str : st) {
			s += "[" + str + " " + prices.get(str) + "]";
		}
		System.out.println(round + " RESERVES:\t" + s);
		Map<String, Double> bids = this.getBids(prices);
		if (bids == null) {
			bids = new HashMap<>();
		}
		Map<ICart, Double> bundle = new HashMap<>();
		s = "";
		for (Map.Entry<String, Double> ent : bids.entrySet()) {
			s += "[" + ent.getKey() + " " + ent.getValue() + "]";
			ICart cart = new Cart();
			cart.addToCart(new Item(ent.getKey()));
			bundle.put(cart, ent.getValue());
		}
		System.out.println(round + " " + name + " BID:\t" + s);
		IBidBundle bid = new OneSidedBidBundle(bundle);
		this.agentBackend.sendMessage(new TradeMessage(0, this.agentBackend.getPrivateID(), this.auctionID, bid));
		this.round++;
		System.out.println("______________________________________________________________________");
	}
	
	protected abstract void onAuctionStart();
	protected abstract Map<String, Double> getBids(Map<String, Double> prices);
	protected abstract void onAuctionEnd(Map<Integer, Set<String>> allocations, Map<Integer, Double> payments, List<List<ITradeMessage>> tradeHistory);

	@Override
	public void onValuationMessage(IValuationMessage valuationMessage) {
		this.valuation = valuationMessage.getValuation();
		
		// get position
		ICart cart;
		cart = new Cart();
		cart.addToCart(new Item("position"));
		this.position = new Double(this.valuation.getValuation(cart)).intValue();
		
		this.allocations.clear();
		this.round = 0;
		
		this.onAuctionStart();
	}

	@Override
	public void onSimulationReportMessage(ISimulationReportMessage simReportMessage) {
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
		
		for (IAccountUpdate upd : state.getPayments()) {
			payments.putIfAbsent(upd.getTo(), 0.0);
			payments.put(upd.getTo(), payments.get(upd.getTo()) + upd.getCost());
		}
		
		this.onAuctionEnd(alloc, payments, state.getTradeHistory());
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
	
	protected int getBidderType() {
		return this.position.intValue();
	}
	
	protected boolean isNationalBidder() {
		return this.getBidderType() == 7;
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

}
