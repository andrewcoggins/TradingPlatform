package brown.platform.managers;

import com.esotericsoftware.kryonet.Connection;

import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IInformationRequestMessage;
import brown.communication.messages.IRegistrationMessage;
import brown.communication.messages.ITradeMessage;

public interface ISimulationManager {

    void createSimulation (int numSimulationRuns, IWorldManager worldManager);

    void lock();
    
    Integer handleRegistration(IRegistrationMessage registrationMessage, Connection connection); 
    
    void giveTradeMessage(ITradeMessage tradeMessage); 
    
    IInformationMessage handleInformationRequest(IInformationRequestMessage informationRequest); 
    
    void runSimulation(int waitTime, int numRuns) throws InterruptedException;

}
