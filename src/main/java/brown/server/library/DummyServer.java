package brown.server.library;

import brown.server.AbsServer;
import brown.setup.ISetup;

/**
 * all servers on top of the trading server will implement this.
 * @author andrew
 *
 */
public abstract class DummyServer extends AbsServer {

  public DummyServer(int port, ISetup gameSetup) {
    super(port, gameSetup);
    // TODO Auto-generated constructor stub
  }
  
}