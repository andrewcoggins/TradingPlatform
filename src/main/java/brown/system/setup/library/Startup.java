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

import com.esotericsoftware.kryo.Kryo;

import brown.communication.bid.library.OneSidedBidBundle;
import brown.communication.messages.library.AbsMessage;
import brown.communication.messages.library.BankUpdateMessage;
import brown.communication.messages.library.RegistrationMessage;
import brown.communication.messages.library.TradeMessage;
import brown.communication.messages.library.TradeRequestMessage;
import brown.platform.accounting.library.Account;
import brown.platform.accounting.library.AccountUpdate;
import brown.platform.accounting.library.Ledger;
import brown.platform.accounting.library.Transaction;
import brown.platform.tradeable.library.Tradeable;
import brown.platform.tradeable.library.TradeableType;
import brown.system.setup.ISetup;
import brown.user.agent.library.AbsAgent;

public final class Startup implements ISetup {
	
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
		kryo.register(AbsAgent.class);
		kryo.register(AbsMessage.class);
		kryo.register(BankUpdateMessage.class);
		kryo.register(TradeMessage.class);
		kryo.register(Transaction.class);
		kryo.register(RegistrationMessage.class);
		kryo.register(Account.class);
		kryo.register(TradeRequestMessage.class);
		kryo.register(Timestamp.class);
		kryo.register(Date.class);
		kryo.register(OneSidedBidBundle.class); 
		kryo.register(AccountUpdate.class);
		kryo.register(Ledger.class);
		kryo.register(HashMap.class);
		kryo.register(TradeableType.class);
		kryo.register(Integer[].class);
		kryo.register(Tradeable.class);	
    kryo.register(List.class);
		return true;
	}

  @Override
  public void setup(Kryo kryo) {
    start(kryo); 
  }

}
