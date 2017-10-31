package brown.agent;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

/**
 * this tests basic kryonet communication. More of a test for me to understand what it does
 * and the basic things that kryonet can and can't do.
 * TODO: get this to work. 
 * @author andrew
 *
 */
public class BasicComTest {
  
  private class BasicServer {
    protected Server theServer;
    public String message; 
      public BasicServer(int port) {
        theServer = new Server();
        Kryo kryo = theServer.getKryo();
        kryo.register(String.class);
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
    
    public TestClient(String host, int port) throws IOException {
      // TODO Auto-generated constructor stub
      this.CLIENT = new Client();
      Kryo agentKryo = CLIENT.getKryo();
      agentKryo.register(String.class);
      final TestClient agent = this;
      try {
      CLIENT.connect(5000, host, port, port);
      } catch (IOException e) {
        System.err.println("ERROR: " + e);
        System.exit(1);
      }
      CLIENT.start();
      CLIENT.sendTCP("THIS IS A TEST");
    }
  }
  
  @Test
  public void testComs() throws IOException {
    // TODO: find an open port.
    BasicServer s = new BasicServer(61629);
    TestClient t = new TestClient("localhost", 61629);
    assertEquals("THIS IS A TEST", s.message);
  }
  
  }