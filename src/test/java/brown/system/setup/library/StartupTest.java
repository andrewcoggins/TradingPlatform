package brown.system.setup.library;

import java.io.IOException;

import org.junit.Test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

public class StartupTest {

  @Test
  public void testStartup() {
    
    // just check that no errors are thrown..?
    
    Startup s = new Startup(); 
    Client c = new Client(16384, 8192);
    c.start();
    Kryo agentKryo = c.getKryo();
    s.setup(agentKryo);
  }
}
