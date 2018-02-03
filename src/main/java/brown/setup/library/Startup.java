package brown.setup.library;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import brown.accounting.library.Account;
import brown.accounting.library.Ledger;
import brown.accounting.library.Transaction;
import brown.agent.AbsAgent;
import brown.bid.interim.BidType;
import brown.bid.library.AuctionBid;
import brown.bidbundle.BundleType;
import brown.bidbundle.IBidBundle;
import brown.bidbundle.library.AuctionBidBundle;
import brown.channels.library.GameChannel;
import brown.market.marketstate.library.Order;
import brown.messages.library.AbsMessage;
import brown.messages.library.AccountResetMessage;
import brown.messages.library.ErrorMessage;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.PrivateInformationMessage;
import brown.messages.library.TradeMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.RegistrationMessage;
import brown.messages.library.TradeRequestMessage;
import brown.messages.library.ValuationInformationMessage;
import brown.tradeable.library.MultiTradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.tradeable.library.TradeableType;
import brown.value.generator.library.LabTwoValGenerator;

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
		return true;
	}

}
