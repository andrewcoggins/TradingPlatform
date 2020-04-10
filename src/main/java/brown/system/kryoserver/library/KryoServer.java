package brown.system.kryoserver.library;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import brown.logging.library.SystemLogging;
import brown.system.kryoserver.IKryoServer;
import brown.system.setup.ISetup;
import brown.system.setup.library.Setup;

public class KryoServer implements IKryoServer {

  protected final int PORT;
  public final Server kryoServer;
  protected Map<Connection, Integer> connections;

  /**
   * a KryoServer needs a port and a setup
   * 
   * @param port the port number
   * @param setup a class that registers the necessary classes with kryo
   */
  public KryoServer(int port, ISetup setup) {
    this.PORT = port;
    this.connections = new ConcurrentHashMap<Connection, Integer>();

    kryoServer = new Server(65536, 65536);
    kryoServer.start();
    Kryo serverKryo = kryoServer.getKryo();
    Setup.start(serverKryo);
    if (setup != null) {
      setup.setup(serverKryo);
    }
    try {
      kryoServer.bind(PORT, PORT);
    } catch (IOException e) {
      SystemLogging.log(e + " [X] Server failed to start due to port conflict");
      return;
    }
  }
  
}
