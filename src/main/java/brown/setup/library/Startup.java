package brown.setup.library;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import brown.accounting.bid.AuctionBid;
import brown.accounting.bid.GameBid;
import brown.accounting.bidbundle.IBidBundle;
import brown.accounting.bidbundle.library.BundleType;
import brown.accounting.bidbundle.library.GameBidBundle;
import brown.accounting.library.Account;
import brown.accounting.library.Ledger;
import brown.accounting.library.Transaction;
import brown.accounting.bidbundle.library.AuctionBidBundle;
import brown.agent.AbsAgent;
import brown.channels.MechanismType;
import brown.channels.agent.library.CDAAgentChannel;
import brown.channels.agent.library.LemonadeChannel;
import brown.channels.agent.library.SimpleAgentChannel;
import brown.channels.server.TwoSidedAuction;
import brown.channels.server.library.CDAServerChannel;
import brown.market.marketstate.library.Order;
import brown.messages.library.AbsMessage;
import brown.messages.library.AckMessage;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.TradeMessage;
import brown.messages.library.BidRequestMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.MarketOrderMessage;
import brown.messages.library.NegotiateRequestMessage;
import brown.messages.library.RegistrationMessage;
import brown.messages.library.NegotiateMessage;
import brown.messages.library.TradeRequestMessage;
import brown.messages.library.ValuationRegistrationMessage;
import brown.rules.clearingrules.IClearingRule;
import brown.tradeable.TradeableType;
import brown.tradeable.library.MultiTradeable;
import brown.twosided.ITwoSidedAuction;
import brown.value.config.ComplexConfig;
import brown.value.generator.AbsValuationGenerator;
import brown.value.generator.library.NormalValGenerator;
import brown.value.valuable.library.Value;
import brown.value.valuation.library.AdditiveValuation;
import brown.value.valuation.library.BundleValuation;

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
		kryo.register(BankUpdateMessage.class);
		kryo.register(GameReportMessage.class);
		kryo.register(TradeMessage.class);
		kryo.register(BidRequestMessage.class);
		kryo.register(Transaction.class);
		kryo.register(RegistrationMessage.class);
		kryo.register(NegotiateMessage.class);
		kryo.register(NegotiateRequestMessage.class);
		kryo.register(Account.class);
		kryo.register(TradeRequestMessage.class);
		kryo.register(AckMessage.class);
		kryo.register(Timestamp.class);
		kryo.register(Date.class);
		kryo.register(IBidBundle.class);
		kryo.register(BundleType.class);
		kryo.register(MarketOrderMessage.class);
		kryo.register(TwoSidedAuction.class);
		kryo.register(ITwoSidedAuction.class);
		kryo.register(MechanismType.class);
		kryo.register(CDAAgentChannel.class);
		kryo.register(IClearingRule.class);
		kryo.register(MultiTradeable.class);
		kryo.register(CDAServerChannel.class);
		kryo.register(Order.class);
		kryo.register(Ledger.class);
		kryo.register(HashMap.class);
		kryo.register(SimpleAgentChannel.class);
		kryo.register(AuctionBidBundle.class);
		kryo.register(AuctionBid.class);
		kryo.register(AdditiveValuation.class);
		kryo.register(BundleValuation.class);
		kryo.register(ValuationRegistrationMessage.class);
		kryo.register(Value.class);
		kryo.register(AdditiveValuation.class);
		kryo.register(Value.class);
		kryo.register(NormalValGenerator.class);
		kryo.register(TradeableType.class);
		kryo.register(AbsValuationGenerator.class);
		kryo.register(GameBidBundle.class);
    kryo.register(GameBid.class);
		kryo.register(Integer[].class);
		return true;
	}

}
