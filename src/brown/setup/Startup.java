package brown.setup;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import brown.accounting.Account;
import brown.accounting.BundleType;
import brown.accounting.Ledger;
import brown.accounting.MarketState;
import brown.accounting.Order;
import brown.accounting.Transaction;
import brown.accounting.bid.SimpleBid;
import brown.accounting.bidbundle.IBidBundle;
import brown.accounting.bidbundle.SimpleBidBundle;
import brown.agent.AbsAgent;
import brown.agent.AbsAgent;
import brown.channels.MechanismType;
import brown.channels.agent.ITwoSidedAuction;
import brown.channels.agent.library.CDAAgentChannel;
import brown.channels.agent.library.LemonadeChannel;
import brown.channels.agent.library.SimpleAgentChannel;
import brown.channels.server.TwoSidedAuction;
import brown.channels.server.library.CDAServerChannel;
import brown.market.library.LMSRBackend;
import brown.messages.AbsMessage;
import brown.messages.library.Ack;
import brown.messages.library.BankUpdate;
import brown.messages.library.Bid;
import brown.messages.library.BidRequest;
import brown.messages.library.GameReport;
import brown.messages.library.MarketOrder;
import brown.messages.library.NegotiateRequest;
import brown.messages.library.Registration;
import brown.messages.library.Trade;
import brown.messages.library.TradeRequest;
import brown.messages.library.ValuationRegistration;
import brown.rules.clearingrules.IClearingRule;
import brown.todeprecate.PaymentType;
import brown.tradeable.library.Tradeable;
import brown.value.valuation.library.AdditiveValuation;
import brown.value.valuation.library.BundleValuation;
import brown.value.valuationrepresentation.AbsValuationRepresentation;

import org.apache.commons.math3.random.RandomGenerator;

import com.esotericsoftware.kryo.Kryo;

public final class Startup {
	
	//TODO: Consider reflection for dynamic loading
  /**
   * registers most necessary classes with Kryo.
   * @param kryo
   * instance of the kryo object.
   * @return
   */
	public static boolean start(Kryo kryo) {
		kryo.register(IllegalArgumentException.class);
		kryo.register(java.util.LinkedList.class);
		kryo.register(ArrayList.class);
		kryo.register(Set.class);
		kryo.register(TreeSet.class);
		kryo.register(HashSet.class);
		kryo.register(TreeMap.class);
		kryo.register(java.util.Collections.reverseOrder().getClass());
		kryo.register(int[].class);
		kryo.register(LemonadeChannel.class);
		kryo.register(AbsAgent.class);
		kryo.register(AbsMessage.class);
		kryo.register(BankUpdate.class);
		kryo.register(GameReport.class);
		kryo.register(Bid.class);
		kryo.register(BidRequest.class);
		kryo.register(Transaction.class);
		kryo.register(Registration.class);
		kryo.register(Trade.class);
		kryo.register(NegotiateRequest.class);
		kryo.register(Account.class);
		kryo.register(TradeRequest.class);
		kryo.register(Ack.class);
		kryo.register(LMSRBackend.class);
		kryo.register(Timestamp.class);
		kryo.register(Date.class);
		kryo.register(IBidBundle.class);
		kryo.register(MarketState.class);
		kryo.register(BundleType.class);
		kryo.register(MarketOrder.class);
		kryo.register(TwoSidedAuction.class);
		kryo.register(ITwoSidedAuction.class);
		kryo.register(MechanismType.class);
		kryo.register(PaymentType.class);
		kryo.register(CDAAgentChannel.class);
		kryo.register(IClearingRule.class);
		kryo.register(Tradeable.class);
		kryo.register(CDAServerChannel.class);
		kryo.register(Order.class);
		kryo.register(Ledger.class);
		kryo.register(HashMap.class);
		kryo.register(SimpleAgentChannel.class);
		kryo.register(SimpleBidBundle.class);
		kryo.register(SimpleBid.class);
		kryo.register(AbsValuationRepresentation.class);
		kryo.register(AdditiveValuation.class);
		kryo.register(BundleValuation.class);
		kryo.register(ValuationRegistration.class);
		return true;
	}

}
