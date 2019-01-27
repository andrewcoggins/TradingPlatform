package brown.system.setup.library;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import brown.auction.value.generator.library.LabTwoValGenerator;
import brown.auction.value.generator.library.UniformValGenerator;
import brown.mechanism.bid.library.OneSidedBidBundle;
import brown.mechanism.channel.library.GameChannel;
import brown.mechanism.messages.library.AbsMessage;
import brown.mechanism.messages.library.AccountResetMessage;
import brown.mechanism.messages.library.BankUpdateMessage;
import brown.mechanism.messages.library.ErrorMessage;
import brown.mechanism.messages.library.GameReportMessage;
import brown.mechanism.messages.library.PrivateInformationMessage;
import brown.mechanism.messages.library.RegistrationMessage;
import brown.mechanism.messages.library.TradeMessage;
import brown.mechanism.messages.library.TradeRequestMessage;
import brown.mechanism.messages.library.ValuationInformationMessage;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.mechanism.tradeable.library.TradeableType;
import brown.platform.accounting.library.Account;
import brown.platform.accounting.library.Ledger;
import brown.platform.accounting.library.Order;
import brown.platform.accounting.library.Transaction;
import brown.user.agent.library.AbsAgent;

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
		kryo.register(OneSidedBidBundle.class); 
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
