package brown.server;

import java.io.IOException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import brown.logging.Logging;
import brown.setup.ISetup;
import brown.setup.library.Startup;

public abstract class KryoServer {
  
  // Server stuff
  private final int PORT;
  protected Server theServer;
  protected Map<Connection, Integer> connections;
  
  public KryoServer(int port, ISetup gameSetup) {
    this.PORT = port;
    this.connections = new ConcurrentHashMap<Connection, Integer>();

    // Kryo Stuff
    theServer = new Server(16384, 8192);
    theServer.start();    
    Kryo serverKryo = theServer.getKryo();
    Startup.start(serverKryo);
    if (gameSetup != null) {
      gameSetup.setup(serverKryo);
    }
    
    // Set up Server
    try {
      theServer.bind(PORT, PORT);
    } catch (IOException e) {
      Logging.log(e + " [X] Server failed to start due to port conflict");
      return;
    }
  }
}