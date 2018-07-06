package brown.messages.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import brown.platform.accounting.library.Ledger;
import brown.platform.messages.library.GameReportMessage;

/**
 * test for the game report message class. 
 * C
 * @author andrew
 *
 */
public class GameReportMessageTest {
  
  @Test 
  public void testGameReportMessage() {
    // complete functionality test.
    Ledger l = new Ledger(0);
    GameReportMessage gr = new GameReportMessage(l); 
    assertEquals(gr.LEDGER, l);
  }
}