package brown.messages.library;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Test;

import brown.mechanism.tradeable.MultiTradeable;
import brown.platform.accounting.Account;
import brown.platform.messages.BankUpdateMessage;

/**
 * test for the bank update message class. 
 * C
 * @author andrew
 *
 */
public class BankUpdateMessageTest {
  
  @Test 
  public void testBankUpdateMessage() { 
    // just test constructor.
    Account anAccount = new Account(0);
    anAccount.add(1000);
    Account anotherAccount = anAccount.copyAccount(); 
    anotherAccount.add(-100.0);
    BankUpdateMessage bu = new BankUpdateMessage(0, anAccount, anotherAccount);
    assertEquals(bu.oldAccount, anAccount); 
    assertEquals(bu.newAccount, anotherAccount); 
  }
  
}