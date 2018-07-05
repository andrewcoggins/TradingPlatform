package brown.system.setup;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import brown.auction.value.generator.LabTwoValGenerator;
import brown.auction.value.generator.UniformValGenerator;
import brown.mechanism.bid.AuctionBid;
import brown.mechanism.bid.BidType;
import brown.mechanism.bidbundle.AuctionBidBundle;
import brown.mechanism.bidbundle.BundleType;
import brown.mechanism.bidbundle.IBidBundle;
import brown.mechanism.channel.GameChannel;
import brown.mechanism.tradeable.ComplexTradeable;
import brown.mechanism.tradeable.MultiTradeable;
import brown.mechanism.tradeable.SimpleTradeable;
import brown.mechanism.tradeable.TradeableType;
import brown.platform.accounting.Account;
import brown.platform.accounting.Ledger;
import brown.platform.accounting.Order;
import brown.platform.accounting.Transaction;
import brown.platform.messages.AbsMessage;
import brown.platform.messages.AccountResetMessage;
import brown.platform.messages.BankUpdateMessage;
import brown.platform.messages.ErrorMessage;
import brown.platform.messages.GameReportMessage;
import brown.platform.messages.PrivateInformationMessage;
import brown.platform.messages.RegistrationMessage;
import brown.platform.messages.TradeMessage;
import brown.platform.messages.TradeRequestMessage;
import brown.platform.messages.ValuationInformationMessage;
import brown.user.agent.AbsAgent;

import com.esotericsoftware.kryo.Kryo;

public final class Startup {
	
  /**
   * registers most necessary classes with kryo
   * @param kryo
   * instance of the kryo object
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
		kryo.register(GameChannel.class);
		kryo.register(AbsAgent.class);
		kryo.register(AbsMessage.class);
		kryo.register(BankUpdateMessage.class);
		kryo.register(GameReportMessage.class);
		kryo.register(TradeMessage.class);
		kryo.register(Transaction.class);
		kryo.register(RegistrationMessage.class);
		kryo.register(Account.class);
		kryo.register(TradeRequestMessage.class);
		kryo.register(ErrorMessage.class);
		kryo.register(Timestamp.class);
		kryo.register(Date.class);
		kryo.register(IBidBundle.class);
		kryo.register(AuctionBidBundle.class);
		kryo.register(AuctionBid.class);
		kryo.register(BidType.class);
		kryo.register(BundleType.class);
		kryo.register(MultiTradeable.class);
		kryo.register(ComplexTradeable.class); 
		kryo.register(Order.class);
		kryo.register(Ledger.class);
		kryo.register(HashMap.class);
		kryo.register(TradeableType.class);
		kryo.register(Integer[].class);
		kryo.register(SimpleTradeable.class);
		kryo.register(ValuationInformationMessage.class);
    kryo.register(PrivateInformationMessage.class);		
    kryo.register(List.class);
    kryo.register(AccountResetMessage.class);
    kryo.register(LabTwoValGenerator.class);
    kryo.register(UniformValGenerator.class);
		return true;
	}

}
