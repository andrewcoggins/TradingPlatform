package brown.server;

import java.util.List;

import brown.server.library.SimulMarkets;
import brown.tradeable.ITradeable;
import brown.value.config.ValConfig;

public interface ISimulation {

  List<SimulMarkets> getSequence();

  ValConfig getValInfo();

  List<ITradeable> getTradeables();

  double getInitialMonies();

  List<ITradeable> getInitialGoods();
}
