package brown.agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import brown.accounting.Account;
import brown.accounting.Ledger;
import brown.channels.agent.library.CDAAgentChannel;
import brown.channels.agent.library.LemonadeChannel;
import brown.channels.agent.library.SimpleAgentChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.BidRequestMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.NegotiateRequestMessage;
import brown.messages.library.TradeRequestMessage;
import brown.setup.ISetup;
import brown.setup.Startup;
import brown.setup.library.SimpleSetup;

/*
 * Tests the Abstract Lemonade Agent as a listener for the datatypes it will 
 * use. Specifically: a Bankupdate, TradeRequest(LemonadeChannel), GameReport.
 */
public class AbsLemonadeAgentTest {
  
 private class TestServer {
    
    public TestServer(int port, ISetup GameSetup) throws IOException {
      Server server = new Server(); 
      Kryo serverKryo = server.getKryo();
      Startup.start(serverKryo);
      server.bind(port, port);
      server.start(); 
      server.addListener(new Listener() {
        public void received (Connection connection, Object object) {
          if (object.equals("send me a LemonadeReport")) {
            GameReportMessage g = new GameReportMessage(new Ledger(0));
            connection.sendTCP(g);
          } else if (object.equals("send me a LemonadeChannel")) { 
            TradeRequestMessage t = new TradeRequestMessage(0, new LemonadeChannel(0, new Ledger(0), null, null), null);
            connection.sendTCP(t);
          } else if (object.equals("send me a BankUpdate")) {
            BankUpdateMessage b = new BankUpdateMessage(0, new Account(1), new Account(1));
            connection.sendTCP(b); 
          }
        }
     });
    }
  }
  
  private class TestAgent extends AbsLemonadeAgent {
    private String myMessage;
    
    public TestAgent(String host, int port, ISetup gameSetup)
        throws AgentCreationException {
      super(host, port, gameSetup);
    }
    
    @Override
    public void onLemonade(LemonadeChannel channel) {
      assertTrue(channel instanceof LemonadeChannel);
      assertTrue(channel.getAuctionID() == 0); 
      assertTrue(channel.getLedger().equals(new Ledger(0)));
      this.myMessage = "Lemonade Channel Received"; 
    }
    
    @Override
    public void onBankUpdate(BankUpdateMessage bankUpdate) {
      // TODO Auto-generated method stub
      assertTrue(bankUpdate instanceof BankUpdateMessage);
      assertTrue(bankUpdate.oldAccount.ID == 1);
      this.myMessage = "Bank Update Received";
    }
    
    @Override
    public void onMarketUpdate(GameReportMessage marketUpdate) {
      assertTrue(marketUpdate instanceof GameReportMessage);
      assertTrue(marketUpdate.LEDGER.equals(new Ledger(0))); 
      this.myMessage = "Lemonade Report Received";
    }

    public String confirm() {
      return this.myMessage;
    }
  }
  
  @Test
  public void testAgent() throws IOException, AgentCreationException, InterruptedException {
    TestServer ts = new TestServer(2121, new SimpleSetup());
    TestAgent t = new TestAgent("localhost", 2121, new SimpleSetup()); 
    t.CLIENT.sendTCP("send me a LemonadeReport"); 
    Thread.sleep(100);
    assertEquals(t.confirm(), "Lemonade Report Received");
    t.CLIENT.sendTCP("send me a BankUpdate");
    Thread.sleep(100);
    assertEquals(t.confirm(), "Bank Update Received");
    t.CLIENT.sendTCP("send me a LemonadeChannel"); 
    Thread.sleep(100);
    assertEquals(t.confirm(), "Lemonade Channel Received");
  }
}
