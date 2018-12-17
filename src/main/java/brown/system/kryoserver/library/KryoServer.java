package brown.system.kryoserver.library;

import java.io.IOException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import brown.logging.library.SystemLogging;
import brown.system.server.IKryoServer;
import brown.system.setup.ISetup;
import brown.system.setup.library.Startup;

public class KryoServer implements IKryoServer {
  
  // Server stuff
  protected final int PORT;
  protected Server kryoServer;
  protected Map<Connection, Integer> connections;
  
  public KryoServer(int port, ISetup gameSetup) {
    this.PORT = port;
    this.connections = new ConcurrentHashMap<Connection, Integer>();

    // Kryo Stuff
    kryoServer = new Server(16384, 8192);
    kryoServer.start();    
    Kryo serverKryo = kryoServer.getKryo();
    Startup.start(serverKryo);
    if (gameSetup != null) {
      gameSetup.setup(serverKryo);
    }
    // Set up Server
    try {
      kryoServer.bind(PORT, PORT);
    } catch (IOException e) {
      SystemLogging.log(e + " [X] Server failed to start due to port conflict");
      return;
    }
  }
}