package brown.setup.library;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import brown.accounting.library.Account;
import brown.accounting.library.Ledger;
import brown.accounting.library.Transaction;
import brown.agent.AbsAgent;
import brown.bid.bidbundle.BundleType;
import brown.bid.bidbundle.IBidBundle;
import brown.bid.bidbundle.library.AuctionBidBundle;
import brown.bid.bidbundle.library.GameBidBundle;
import brown.bid.library.AuctionBid;
import brown.bid.library.GameBid;
import brown.channels.agent.library.CDAAgentChannel;
import brown.channels.agent.library.LemonadeChannel;
import brown.channels.agent.library.SSSPChannel;
import brown.channels.server.TwoSidedAuction;
import brown.channels.server.library.CDAServerChannel;
import brown.market.marketstate.library.Order;
import brown.messages.library.AbsMessage;
import brown.messages.library.ErrorMessage;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.PrivateInformationMessage;
import brown.messages.library.TradeMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.MarketOrderMessage;
import brown.messages.library.NegotiateRequestMessage;
import brown.messages.library.RegistrationMessage;
import brown.messages.library.NegotiateMessage;
import brown.messages.library.TradeRequestMessage;
import brown.messages.library.ValuationInformationMessage;
import brown.rules.IClearingRule;
import brown.tradeable.TradeableType;
import brown.tradeable.library.MultiTradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.twosided.ITwoSidedAuction;
import brown.value.distribution.library.AdditiveValuationDistribution;
import brown.value.generator.library.NormalValGenerator;
import brown.value.valuation.library.AdditiveValuation;

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
		kryo.register(Transaction.class);
		kryo.register(RegistrationMessage.class);
		kryo.register(NegotiateMessage.class);
		kryo.register(NegotiateRequestMessage.class);
		kryo.register(Account.class);
		kryo.register(TradeRequestMessage.class);
		kryo.register(ErrorMessage.class);
		kryo.register(Timestamp.class);
		kryo.register(Date.class);
		kryo.register(IBidBundle.class);
		kryo.register(BundleType.class);
		kryo.register(MarketOrderMessage.class);
		kryo.register(TwoSidedAuction.class);
		kryo.register(ITwoSidedAuction.class);
		kryo.register(CDAAgentChannel.class);
		kryo.register(IClearingRule.class);
		kryo.register(MultiTradeable.class);
		kryo.register(CDAServerChannel.class);
		kryo.register(Order.class);
		kryo.register(Ledger.class);
		kryo.register(HashMap.class);
		kryo.register(SSSPChannel.class);
		kryo.register(AuctionBidBundle.class);
		kryo.register(AuctionBid.class);
		kryo.register(AdditiveValuation.class);
		kryo.register(AdditiveValuation.class);
		kryo.register(NormalValGenerator.class);
		kryo.register(TradeableType.class);
		kryo.register(GameBidBundle.class);
    kryo.register(GameBid.class);
		kryo.register(Integer[].class);
		kryo.register(SimpleTradeable.class);
		kryo.register(ValuationInformationMessage.class);
    kryo.register(PrivateInformationMessage.class);		
    kryo.register(AdditiveValuationDistribution.class);
		return true;
	}

}
