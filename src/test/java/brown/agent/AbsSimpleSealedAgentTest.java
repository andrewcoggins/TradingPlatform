package brown.agent;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import brown.accounting.Account;
import brown.accounting.Ledger;
import brown.accounting.MarketState;
import brown.accounting.bidbundle.library.AuctionBidBundle;
import brown.channels.MechanismType;
import brown.channels.agent.library.SimpleAgentChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.RegistrationMessage;
import brown.messages.library.TradeRequestMessage;
import brown.messages.library.ValuationRegistrationMessage;
import brown.setup.ISetup;
import brown.setup.library.LemonadeSetup;
import brown.setup.library.Startup;
import brown.todeprecate.PaymentType;
import brown.tradeable.library.MultiTradeable;
import brown.value.valuable.library.Value;
import brown.value.valuationrepresentation.library.SimpleValuation;

/**
 * test for the abstract simple sealed agent. 
 * C
 * @author andrew
 *
 */
public class AbsSimpleSealedAgentTest {
  
  
  private class TestServer {
    
      public TestServer(int port, ISetup GameSetup) throws IOException {
        Server server = new Server(); 
        Kryo serverKryo = server.getKryo();
        Startup.start(serverKryo);
        server.bind(port, port);
        server.start(); 
        server.addListener(new Listener() {
          public void received (Connection connection, Object object) {
            if (object.equals("send me a GameReport")) {
              GameReportMessage g = new GameReportMessage(new Ledger(0));
              connection.sendTCP(g);
            } else if (object.equals("send me a BankUpdate")) {
              BankUpdateMessage b = new BankUpdateMessage(0, new Account(1), new Account(1));
              connection.sendTCP(b);
            } else if (object.equals("send me a SimpleAgentChannel")) {
              Map<MultiTradeable, MarketState> junk = new HashMap<MultiTradeable, MarketState>();
              SimpleAgentChannel sa = new SimpleAgentChannel(0, new Ledger(0),
                  PaymentType.FirstPrice, MechanismType.SealedBid, new AuctionBidBundle(junk), 0); 
              connection.sendTCP(new TradeRequestMessage(0, sa, MechanismType.SealedBid));
            } else if (object.equals("send me a Registration")) {
              Map<MultiTradeable, Value> m = new HashMap<MultiTradeable, Value>();
              ValuationRegistrationMessage val = new ValuationRegistrationMessage(0,
                  new SimpleValuation(m));
              connection.sendTCP(val);
            }
          }
        });
      }
    }

  private class TestSimpleSealedAgent extends AbsSimpleSealedAgent {
    public TestSimpleSealedAgent(String host, int port, ISetup gameSetup)
        throws AgentCreationException {
      super(host, port, gameSetup);
    }
    private String myMessage;
      // TODO Auto-generated constructor stub
    @Override
    public void onMarketUpdate(GameReportMessage marketUpdate) {
      this.myMessage = "Game Report Received";
    }
    @Override
    public void onBankUpdate(BankUpdateMessage bankUpdate) {
      // TODO Auto-generated method stub
      this.myMessage = "Bank Update Received";
    }
    @Override
    public void onRegistration(RegistrationMessage reg) {
      if (reg instanceof ValuationRegistrationMessage) {
        this.myMessage = "Registration Received";
      }
    }
    @Override
    public void onSimpleSealed(SimpleAgentChannel simpleWrapper) {
     this.myMessage = "SimpleAgentChannel Received";
    }
    public String confirm() {
      return this.myMessage;
    }
    }

  
  @Test
  public void testAbsSimpleSealedAgent()
      throws InterruptedException, AgentCreationException, IOException {
    TestServer ts = new TestServer(2121, new LemonadeSetup());
    TestSimpleSealedAgent t = new TestSimpleSealedAgent("localhost", 2121, new LemonadeSetup()); 
    t.CLIENT.sendTCP("send me a GameReport"); 
    Thread.sleep(100);
    assertEquals(t.confirm(), "Game Report Received");
    t.CLIENT.sendTCP("send me a BankUpdate");
    Thread.sleep(100);
    assertEquals(t.confirm(), "Bank Update Received");
    t.CLIENT.sendTCP("send me a SimpleAgentChannel"); 
    Thread.sleep(100);
    assertEquals(t.confirm(), "SimpleAgentChannel Received");
    t.CLIENT.sendTCP("send me a Registration");
    Thread.sleep(100);
    assertEquals(t.confirm(), "Registration Received");
  }
}
 