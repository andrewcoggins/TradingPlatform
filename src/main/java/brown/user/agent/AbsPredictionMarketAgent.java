package brown.user.agent;

import java.util.ArrayList;
import java.util.List;

import brown.logging.library.Logging;
import brown.mechanism.bid.BidDirection;
import brown.mechanism.bid.CancelBid;
import brown.mechanism.bid.TwoSidedBid;
import brown.mechanism.bidbundle.CancelBundle;
import brown.mechanism.bidbundle.TwoSidedBidBundle;
import brown.mechanism.channel.CallMarketChannel;
import brown.platform.accounting.Transaction;
import brown.platform.messages.BankUpdateMessage;
import brown.platform.messages.CallMarketReportMessage;
import brown.platform.messages.GameReportMessage;
import brown.platform.messages.PredictionMarketReport;
import brown.platform.messages.PrivateInformationMessage;
import brown.platform.twosided.IOrderBook;
import brown.platform.twosided.OrderBook;
import brown.system.setup.library.CallMarketSetup;

/**
 * abstract agent used in prediction market project for cs1951k.
 * @author kerry
 *
 */
public abstract class AbsPredictionMarketAgent extends AbsCallMarketAgent {
	private List<Transaction> ledger;
	private IOrderBook orderbook;

	public AbsPredictionMarketAgent(String host, int port) {
		super(host, port, new CallMarketSetup());
		this.ledger = new ArrayList<Transaction>();
		this.orderbook = new OrderBook();
	}
	
	public AbsPredictionMarketAgent(String host, int port, String name) {
		super(host, port, new CallMarketSetup(), name);
		this.ledger = new ArrayList<Transaction>();
		this.orderbook = new OrderBook();   
	}
	
	@Override
	public void onCallMarket(CallMarketChannel channel) {
		this.orderbook = channel.getOrderBook();
		this.onMarketRequest(channel);
		if (channel.isTest()){
		  Logging.log("Agent: " + this.ID + ", coin: " + this.coin + ", decoys: " + this.numDecoys + ", Highest Buy: " + 
		      this.getHighestBuy() + ", Lowest Sell: " + this.getLowestSell());
		}
	}
	
	@Override
	public synchronized void onBankUpdate(BankUpdateMessage update) {
		int quantity = update.quantity == null ? 0 : (int) update.quantity.doubleValue();
		double price = update.moniesChanged / ((double) quantity);
		onTransaction(quantity, price);
	}
	
	@Override
	public synchronized void onGameReport(GameReportMessage gmReport) {
		if (gmReport instanceof PredictionMarketReport) {
			PredictionMarketReport pmReport = (PredictionMarketReport) gmReport;
			System.out.println("Real coin result: " + pmReport.getCoin());
		} else if (gmReport instanceof CallMarketReportMessage) {
			CallMarketReportMessage cmReport = (CallMarketReportMessage) gmReport;
			ledger.addAll(cmReport.getTransactions());
		}
	}
	
	@Override
	public void onPrivateInformation(PrivateInformationMessage privateInfo) {
		super.onPrivateInformation(privateInfo);
		this.ledger = new ArrayList<Transaction>();
		this.orderbook = new OrderBook();   
		onMarketStart();
	}
	
	public void buy(double price, int quantity, CallMarketChannel channel) {
		TwoSidedBid bid = new TwoSidedBid(BidDirection.BUY, price, quantity);
		channel.bid(this, new TwoSidedBidBundle(bid));
	}
	
	public void sell(double price, int quantity, CallMarketChannel channel) {
		TwoSidedBid bid = new TwoSidedBid(BidDirection.SELL, price, quantity);
		channel.bid(this, new TwoSidedBidBundle(bid));
	}
	
	public void cancel(double price, boolean buy, CallMarketChannel channel) {
		BidDirection dir = buy ? BidDirection.BUY : BidDirection.SELL;
		channel.bid(this, new CancelBundle(new CancelBid(dir, price)));
	}
	
	public boolean getCoin() {
		return this.coin;
	}
	
	public int getNumDecoys() {
		return this.numDecoys;
	}
	
	public List<Transaction> getLedger() {
		return this.ledger;
	}
	
	public IOrderBook getOrderBook() {
		return this.orderbook;
	}
	
	public abstract void onMarketStart();
	public abstract void onMarketRequest(CallMarketChannel channel);
	public abstract void onTransaction(int quantity, double price);
	public abstract double getHighestBuy();
	public abstract double getLowestSell();
}
