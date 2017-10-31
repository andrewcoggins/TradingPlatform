package brown.agent;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import brown.assets.accounting.Account;
import brown.assets.accounting.Ledger;
import brown.channels.IAgentChannel;
import brown.exceptions.AgentCreationException;
import brown.marketinternalstates.SimpleInternalState;
import brown.markets.Market;
import brown.messages.Message;
import brown.registration.ValuationRegistration;
import brown.rules.allocationrules.library.SimpleHighestBidderAllocation;
import brown.rules.paymentrules.library.SimpleSecondPrice;
import brown.server.TradingServer;
import brown.setup.Logging;
import brown.setup.Setup;
import brown.setup.library.SimpleSetup;
import brown.valuable.library.Tradeable;

/**
 * test the ability of the system to send and receive messages.
 * @author andrew
 *
 */
public class TPClientTest extends TradingServer {
  
  

  public TPClientTest(int port, Setup gameSetup) {
    super(port, gameSetup);
    // TODO Auto-generated constructor stub
  }

  /***
   * this mimics an agent.
   * @author andrew
   *
   */
  private class TestClient extends TPClient {
    public TestClient(String host, int port, Setup gameSetup)
        throws AgentCreationException {
      super(host, port, gameSetup);
      // TODO Auto-generated constructor stub
      final TestClient agent = this;
      CLIENT.addListener(new Listener() {
        public void received(Connection connection, Object message) {
          synchronized (agent) {
            if (message instanceof TestMessage) {
              TestMessage theMessage = (TestMessage) message;
              theMessage.dispatch(agent);
            }
          }
        }
      });
    }
    //this invokes the channel to send the argument.
    public void communicate(TestChannel channel) {
      channel.send(this, "This is a Test!");
    }
  }
  
  
  /**
   * this represents a server that will receive communications.
   * @author andrew
   *
   */
  private class TestServer {

    private String message; 
    
    public TestServer(int port, Setup gameSetup) {
      
      // TODO Auto-generated constructor stub
      theServer = new Server();
      final TestServer aServer = this;
      theServer.addListener(new Listener() {
        public void received (Connection connection, Object object) {
           if (object instanceof TestMessage) {
              TestMessage request = (TestMessage)object;
              System.out.println(request.message);
              aServer.message = request.message; 
           }
        }
     });
    }

    public String getMessage() {
      return this.message; 
    }

  }
  
  /**a test message 
   * 
   * @author andrew
   *
   */
  private class TestMessage extends Message {
    
   public String message; 
    
   public TestMessage(Integer ID, String message) {
      super(ID);
      this.message = message;
    }
  public void dispatch(TestClient agent) {
     
   }
  @Override
  public void dispatch(Agent agent) {
    // TODO Auto-generated method stub
    
  } 

  }
    
    
    /**
     * this represents an agent-side channel. 
     * @author andrew
     *
     */
    private class TestChannel implements IAgentChannel {
      @Override
      public Integer getAuctionID() {
        // TODO Auto-generated method stub
        return null;
      }
      @Override
      public Ledger getLedger() {
        // TODO Auto-generated method stub
        return null;
      }
      //noop
      @Override
      public void dispatchMessage(Agent agent) {
        // TODO Auto-generated method stub
      }
      //this call invokes the agent to invoke send below. 
      public void dispatchMessage(TestClient tClient) { 
        tClient.communicate(this);
      }
      //this actually sends the message.
      public void send(TestClient t, String arg) {
        t.CLIENT.sendTCP(new TestMessage(0, arg));
      }
    }
    
    private class TestSetup implements Setup { 
      @Override
      public void setup(Kryo kryo) {
        kryo.register(TestMessage.class);
        // TODO Auto-generated method stub
      }
    }
    
    @Test
    public void testCommunication() throws AgentCreationException {
      TestServer server = new TestServer(2121, new TestSetup());
      TestClient client = new TestClient("localhost", 2121, new TestSetup());
      TestChannel channel = new TestChannel();
      TestMessage message = new TestMessage(null, null);
      
      client.communicate(channel);
      channel.dispatchMessage(client);
      assertEquals(server.getMessage(), "This is a test.");
    }

  
}






