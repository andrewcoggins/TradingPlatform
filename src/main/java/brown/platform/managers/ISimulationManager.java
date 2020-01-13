package brown.platform.managers;

import com.esotericsoftware.kryonet.Connection;

import brown.communication.messages.IRegistrationMessage;
import brown.communication.messages.ITradeMessage;

/**
 * stores and creates Simulations. 
 * @author andrewcoggins
 *
 */
public interface ISimulationManager {

  /**
   * create a simulation. 
   * @param numSimulationRuns
   *  number of times the simulation will be run
   * @param worldManager
   * Simulation's WorldManager
   */
  void createSimulation(int numSimulationRuns, IWorldManager worldManager);

  /**
   * Lock the SimulationManager. After it is locked, no more simulations can be created. 
   */
  void lock();

  /**
   * Run the simulation
   * @param startingDelayTime
   * delay time at the beginning of the simulation, during which agents can register.
   * @param simulationDelayTime
   * delay time between when markets send TradeRequestMessage and when they cycle again. 
   * Amount of time that agent has to 'think' 
   * @param numRuns
   * number of times the simulation will be run.  
   * @throws InterruptedException
   */
  void runSimulation(int startingDelayTime, double simulationDelayTime,
      int numRuns) throws InterruptedException;

  /**
   * handle an IRegistrationMessage sent from an agent. 
   * @param registrationMessage
   * Registration message from agent. 
   * @param connection
   * Trading Platform Server's connection with agent. 
   * @return
   */
  Integer handleRegistration(IRegistrationMessage registrationMessage,
      Connection connection);

  /**
   * pass on a Trade Message to the SimulationManager's marketManager. 
   * invoked by the SimulationManager's MessageServer
   * @param tradeMessage
   * trade message from agent. 
   */
  void giveTradeMessage(ITradeMessage tradeMessage);

}
