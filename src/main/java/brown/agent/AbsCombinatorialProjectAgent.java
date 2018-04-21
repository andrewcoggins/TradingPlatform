package brown.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import brown.bid.interim.BidType;
import brown.bid.library.QueryBid;
import brown.bidbundle.library.AuctionBidBundle;
import brown.bidbundle.library.QueryBundle;
import brown.channels.library.OpenOutcryChannel;
import brown.channels.library.QueryChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.CombinatorialClockReport;
import brown.messages.library.GameReportMessage;
import brown.messages.library.PrivateInformationMessage;
import brown.messages.library.SpecValValuationMessage;
import brown.messages.library.SpecValValuationReport;
import brown.setup.library.SpecValSetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.ComplexTradeable;
import brown.tradeable.library.SimpleTradeable;

public abstract class AbsCombinatorialProjectAgent extends AbsSpecValAgent {
	
	private double[] prices;
	private double[] allocations;
	private Map<Set<Integer>, Double> myValuation;

	public AbsCombinatorialProjectAgent(String host, int port, String name) throws AgentCreationException {
		super(host, port, new SpecValSetup(), name);
	}

	@Override
	public void onQueryMarket(QueryChannel channel) {
		// set queries
		List<Set<Integer>> queries = onQueryRound().subList(0, 10);
		
		// parse queries into a list of complex tradeables
		List<ComplexTradeable> ctList = new ArrayList<>();
		for (Set<Integer> bundle : queries) {
			Set<ITradeable> tradeableSet = new HashSet<>();
			for (Integer i : bundle) {
				int good = Math.max(0, Math.min(99, zeroIfNull(i)));
				tradeableSet.add(new SimpleTradeable(good));
			}
			
			ctList.add(new ComplexTradeable(0, tradeableSet));
		}
		
		// submit queries
		channel.bid(this, new QueryBundle(new QueryBid(ctList)));
	}

	@Override
	public void onClockMarket(OpenOutcryChannel channel) {
		// update current prices
		Map<ITradeable, Double> priceMap = channel.getReserve();
		for (ITradeable t : priceMap.keySet()) {
			prices[t.getID()] = priceMap.get(t);
		}
		
		// get agent's bundle of interested goods
		Set<Integer> bundle = onBidRound();
		
		// submit bid to server
		Map<ITradeable, BidType> bid = new HashMap<>();
		for (Integer i : bundle) {
			int good = Math.max(0, Math.min(99, zeroIfNull(i)));
			SimpleTradeable st = new SimpleTradeable(good);
			BidType bt = new BidType(prices[good], 1);
			bid.put(st, bt);
		}
		channel.bid(this, new AuctionBidBundle(bid));
	}
	
	@Override
	public void onGameReport(GameReportMessage gameReport) {
		if (gameReport instanceof SpecValValuationReport) {
			// cast to SpecValValuationReport
			SpecValValuationReport report = (SpecValValuationReport) gameReport;
			
			// add query results to our valuation map
			Map<Set<Integer>, Double> newBundles = valMapToIntMap(report.getValuation());
			myValuation.putAll(newBundles);
			
			// anything else students want to do after receiving query results
			onQueryResults(newBundles);
		} else if (gameReport instanceof CombinatorialClockReport) {
			// cast to CombinatorialClockReport
			CombinatorialClockReport report = (CombinatorialClockReport) gameReport;
			
			// get goods that we're currently allocated
			for (Entry<ITradeable, Double> entry : report.winnings.entrySet()) {
				allocations[entry.getKey().getID()] = entry.getValue();
			}
			
			// anything else students want to do after a bid round
			onBidResults(allocations);
		}
	}
	
	@Override
	public void onPrivateInformation(PrivateInformationMessage privateInfo) {
		// reset local copy of prices and local valuations
		this.prices = new double[100];
		this.allocations = new double[100];
		this.myValuation = new HashMap<>();
		
		// get our values for initial random bundles
		
		if (privateInfo instanceof SpecValValuationMessage) {
			// cast PrivateInformation object to correct message
			SpecValValuationMessage message = (SpecValValuationMessage) privateInfo;
			
			// add initial bundle values to our valuation map
			Map<Set<Integer>, Double> newBundles = valMapToIntMap(message.getValuation());
			myValuation.putAll(newBundles);
		}
		
		// whatever else students want to do when an auction begins
		onAuctionStart();
	}
	
	@Override
	public synchronized void onBankUpdate(BankUpdateMessage update) {
		super.onBankUpdate(update);
		
		// get the bundle of goods we won
		Set<Integer> bundle = new HashSet<>();
		for (SimpleTradeable st : update.tradeableAdded.flatten()) {
			bundle.add(st.ID);
		}
		
		// whatever students want to do after an auction ends
		onAuctionEnd(bundle);
	}
	
	public double[] getPrices() {
		return prices;
	}
	
	public double[] getAllocations() {
		return allocations;
	}
	
	public Map<Set<Integer>, Double> getValuation() {
		return myValuation;
	}
	
	private int zeroIfNull(Integer i) {
		return i == null ? 0 : i;
	}
	
	// converts a (ComplexTradeable -> Double) map to a (Integer -> Double) map
	private Map<Set<Integer>, Double> valMapToIntMap(Map<ComplexTradeable, Double> valMap) {
		Map<Set<Integer>, Double> intMap = new HashMap<>();
		for (ComplexTradeable ct : valMap.keySet()) {
			Set<Integer> bundle = new HashSet<>();
			for (SimpleTradeable st : ct.flatten()) {
				bundle.add(st.ID);
			}
			
			intMap.put(bundle, valMap.get(ct));
		}
		
		return intMap;
	}
	
	public abstract List<Set<Integer>> onQueryRound();
	public abstract void onQueryResults(Map<Set<Integer>, Double> results);
	public abstract Set<Integer> onBidRound();
	public abstract void onBidResults(double[] allocations);
	public abstract void onAuctionStart();
	public abstract void onAuctionEnd(Set<Integer> finalBundle);

}
