package brown.server.library;

import brown.server.TradingServer;
import brown.setup.Setup;

/**
 * all servers on top of the trading server will implement this.
 * @author andrew
 *
 */
public abstract class DummyServer extends TradingServer {

  public DummyServer(int port, Setup gameSetup) {
    super(port, gameSetup);
    // TODO Auto-generated constructor stub
  }
  
}