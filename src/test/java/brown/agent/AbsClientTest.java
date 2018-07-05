package brown.agent;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import brown.exceptions.AgentCreationException;
import brown.system.client.TPClient;
import brown.system.setup.ISetup;
import brown.system.setup.LemonadeSetup;

/**
 * test the ability of the system to send messages. This is all that AbsClient
 * does.
 * @author andrew
 *
 */
public class AbsClientTest {
  
  /*
   * dummy client sends a message.
   */
  private class TestClient extends TPClient {
    public TestClient(String host, int port, ISetup gameSetup)
        throws AgentCreationException {
      super(host, port, gameSetup);
      // TODO Auto-generated constructor stub
    }
    public void send() {
      this.CLIENT.sendTCP("This is a test message");
    } 
  }
  
  //server receives this message.
  @Test
  public void testClientSend() throws AgentCreationException, IOException {
    Server server = new Server(); 
    server.bind(2121, 2121);
    server.start(); 
    server.addListener(new Listener() {
      public void received (Connection connection, Object object) {
         assertEquals(object, "This is a test message");
      }
   });
    TestClient c = new TestClient("localhost", 2121, new LemonadeSetup());
    c.send();
  }
}
