package brown.platform.managers;

import com.esotericsoftware.kryonet.Connection;

import brown.communication.messages.IRegistrationMessage;
import brown.communication.messages.ITradeMessage;

public interface ISimulationManager {

    void createSimulation (int numSimulationRuns, IWorldManager worldManager);

    void lock();
    
    void runSimulation(int startingDelayTime, int simulationDelayTime, int numRuns) throws InterruptedException;
    
    Integer handleRegistration(IRegistrationMessage registrationMessage, Connection connection); 
    
    void giveTradeMessage(ITradeMessage tradeMessage); 

}
