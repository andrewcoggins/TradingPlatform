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
import brown.channels.agent.library.SimpleAgentChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.BankUpdate;
import brown.messages.library.BidRequest;
import brown.messages.library.GameReport;
import brown.messages.library.NegotiateRequest;
import brown.setup.ISetup;
import brown.setup.Startup;
import brown.setup.library.SimpleSetup;

/*
 * The AbsAgent serves as a listener.
 * This tests the listening capabilities of the abstract agent class 
 * to an array of different message types.
 */
public class AbsAgentTest {
  
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
            GameReport g = new GameReport(new Ledger(0));
            connection.sendTCP(g);
          } else if (object.equals("send me a BankUpdate")) {
            BankUpdate b = new BankUpdate(0, new Account(1), new Account(1));
            connection.sendTCP(b);
          } else if (object.equals("send me a BidRequest")) {
            BidRequest br = new BidRequest(0, 0, null, null, null);
            connection.sendTCP(br);
          } else if (object.equals("send me a NegotiateRequest")) {
            NegotiateRequest nr = new NegotiateRequest(1, 0, 100, null, 50, null);
            connection.sendTCP(nr);
          } else if (object.equals("send me a SimpleAgentChannel")) {
            SimpleAgentChannel sa = new SimpleAgentChannel(0, new Ledger(0), null, null, null, 0); 
            connection.sendTCP(sa);
          } else if (object.equals("send me a CDAAgentChannel")) {
            CDAAgentChannel cd = new CDAAgentChannel(null, new Ledger(0));
            connection.sendTCP(cd);
          }
        }
     });
    }
  }
  
  private class TestAgent extends AbsAgent {
    private String myMessage;
    
    public TestAgent(String host, int port, ISetup gameSetup)
        throws AgentCreationException {
      super(host, port, gameSetup);
    }
    
    @Test
    public void onMarketUpdate(GameReport marketUpdate) {
      assertTrue(marketUpdate instanceof GameReport);
      assertTrue(marketUpdate.LEDGER.equals(new Ledger(0)));
       this.myMessage = "Game Report Received";
    }

    @Test
    public void onBankUpdate(BankUpdate bankUpdate) {
      assertTrue(bankUpdate instanceof BankUpdate);
      assertTrue(bankUpdate.getID() == 0);
      // TODO Auto-generated method stub
      this.myMessage = "Bank Update Received"; 
    }

    @Test
    public void onTradeRequest(BidRequest bidRequest) {
      assertTrue(bidRequest instanceof BidRequest); 
      assertTrue(bidRequest.getID() == 0);
      // TODO Auto-generated method stub
      this.myMessage = "Bid Request Received";
    }

    @Test
    public void onNegotiateRequest(NegotiateRequest tradeRequest) {
      assertTrue(tradeRequest instanceof NegotiateRequest); 
      assertTrue(tradeRequest.toID == 1);
      assertTrue(tradeRequest.fromID == 0);
      // TODO Auto-generated method stub
      this.myMessage = "Negotiate Request Received";
    }
    
    //perhaps later.
    @Test
    public void onSimpleSealed(SimpleAgentChannel simpleWrapper) {
      // TODO Auto-generated method stub
      System.out.println("SimpleAuctionChannel Received: SS");
    }

    @Test
    public void onSimpleOpenOutcry(SimpleAgentChannel simpleWrapper) {
      // TODO Auto-generated method stub
      System.out.println("SimpleAuctionChannel Received: OO");
    }

    @Test
    public void onContinuousDoubleAuction(CDAAgentChannel market) {
      // TODO Auto-generated method stub
      System.out.println("CDAChannel Received: OO");
    }
     
    public String confirm() {
      return this.myMessage;
    }
  }
  
  @Test
  public void testAbsAgent() throws IOException, AgentCreationException, InterruptedException {
    TestServer ts = new TestServer(2121, new SimpleSetup());
    TestAgent t = new TestAgent("localhost", 2121, new SimpleSetup()); 
    t.CLIENT.sendTCP("send me a GameReport"); 
    Thread.sleep(100);
    assertEquals(t.confirm(), "Game Report Received");
    t.CLIENT.sendTCP("send me a BankUpdate");
    Thread.sleep(100);
    assertEquals(t.confirm(), "Bank Update Received");
    t.CLIENT.sendTCP("send me a BidRequest"); 
    Thread.sleep(100);
    assertEquals(t.confirm(),"Bid Request Received"); 
    t.CLIENT.sendTCP("send me a NegotiateRequest"); 
    Thread.sleep(100);
    assertEquals(t.confirm(),"Negotiate Request Received"); 
  }
  
}