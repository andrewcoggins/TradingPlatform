package brown.agent;

import java.util.ArrayList;
import java.util.List;

import brown.accounting.library.Transaction;
import brown.bid.library.BidDirection;
import brown.bid.library.CancelBid;
import brown.bid.library.TwoSidedBid;
import brown.bidbundle.library.CancelBundle;
import brown.bidbundle.library.TwoSidedBidBundle;
import brown.channels.library.CallMarketChannel;
import brown.exceptions.AgentCreationException;
import brown.logging.Logging;
import brown.market.twosided.IOrderBook;
import brown.market.twosided.OrderBook;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.CallMarketReportMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.PredictionMarketReport;
import brown.messages.library.PrivateInformationMessage;
import brown.setup.library.CallMarketSetup;

/**
 * abstract agent used in prediction market project for cs1951k.
 * @author kerry
 *
 */
public abstract class AbsPredictionMarketAgent extends AbsCallMarketAgent {
	private List<Transaction> ledger;
	private IOrderBook orderbook;

	public AbsPredictionMarketAgent(String host, int port) throws AgentCreationException {
		super(host, port, new CallMarketSetup());
		this.ledger = new ArrayList<Transaction>();
		this.orderbook = new OrderBook();
	}
	
	public AbsPredictionMarketAgent(String host, int port, String name) throws AgentCreationException {
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
