package brown.agent.library;

import java.util.PriorityQueue;

import brown.agent.AbsPredictionMarketAgent;
import brown.channels.library.CallMarketChannel;
import brown.exceptions.AgentCreationException;
import brown.market.marketstate.library.BuyOrder;
import brown.market.marketstate.library.OrderBook;
import brown.market.marketstate.library.SellOrder;

public class Agent extends AbsPredictionMarketAgent {
	
	boolean print;
	double price;
	int round = 0;

	public Agent(String host, int port, double price, boolean print) throws AgentCreationException {
		super(host, port);
		this.price = price;
		this.print = print;
	}
	
	public Agent(String host, int port, String name, double price, boolean print) throws AgentCreationException {
		super(host, port, name);
		this.price = price;
		this.print = print;
	}

	@Override
	public void onMarketStart() {
		round = 0;
	}

	@Override
	public void onMarketRequest(CallMarketChannel channel) {
		if (round < 10) {
			buy(price+round, 1, channel);
		}
		
		if (print && round < 11) {
			OrderBook book = getOrderBook();
			PriorityQueue<BuyOrder> buys =  book.getBuys();
			PriorityQueue<SellOrder> sells = book.getSells();
			
			if (buys.size() > 0) {
				for (BuyOrder b : buys) {
					System.out.println("id: " + b.agent + ", buy price: " + b.price);
				}
			}
			
			if (sells.size() > 0) {
				for (SellOrder b : sells) {
					System.out.println("id: " + b.agent + ", sell price: " + b.price);
				}
			}
		}
		
		round++;
	}

	@Override
	public void onTransaction(int quantity, double price) {

	}

	@Override
	public double getHighestBuy() {
		return 0;
	}

	@Override
	public double getLowestSell() {
		return 0;
	}
	
	public static void main(String[] args) throws AgentCreationException {
		new Agent("localhost", 2121, "buy1", 1, false);
		new Agent("localhost", 2121, "buy20", 20, false);
		new Agent("localhost", 2121, "buy40", 40, false);
		new Agent("localhost", 2121, "buy60", 60, true);
		
		while (true) {}
	}

}
