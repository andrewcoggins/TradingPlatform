package brown.agent;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import brown.setup.Setup;

/**
 * this tests basic kryonet communication. More of a test for me to understand what it does
 * and the basic things that kryonet can and can't do.
 * @author andrew
 *
 */
public class BasicComTest {
  
  private class BasicServer {
    protected Server theServer;
    public String message; 
      public BasicServer(int port, Setup gameSetup) {
        theServer = new Server();
        final BasicServer aServer = this;
        theServer.addListener(new Listener() {
          public void received (Connection connection, Object object) {
             if (object instanceof String) {
                System.out.println((String) object);
                aServer.message = (String) object; 
             }
          }
       });
      }
    }
  
  
  private class TestClient {
    
    public final Client CLIENT;
    
    public TestClient(String host, int port, Setup gameSetup) {
      // TODO Auto-generated constructor stub
      this.CLIENT = new Client();
      final TestClient agent = this;
      CLIENT.start();
      CLIENT.sendTCP("THIS IS A TEST");
    }
  }
  
  @Test
  public void testComs() {
    BasicServer s = new BasicServer(0, null);
    TestClient t = new TestClient(null, 0, null);
    assertEquals(s.message, "THIS IS A TEST");
  }
  
  }