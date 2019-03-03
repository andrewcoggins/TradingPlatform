package brown.communication.messageserver.library;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import brown.communication.messages.IBankUpdateMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IInformationRequestMessage;
import brown.communication.messages.IRegistrationMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.library.RegistrationMessage;
import brown.communication.messageserver.IMessageServer;
import brown.logging.library.Logging;
import brown.logging.library.PlatformLogging;
import brown.platform.managers.ISimulationManager;
import brown.system.kryoserver.library.KryoServer;
import brown.system.setup.ISetup;

/**
 * Message passing server for Trading Platform
 * 
 * @author acoggins
 *
 */
public class MessageServer extends KryoServer implements IMessageServer {
  
  private ISimulationManager manager; 
  
  public MessageServer(int port, ISetup gameSetup, ISimulationManager manager) {
    super(port, gameSetup);
    
    kryoServer.addListener(new Listener() {
      public void received(Connection connection, Object message) {
        if (connections.containsKey(connection)) {
          // If the connection is already contained, check if message is a trade
          int id = connections.get(connection);
          if (message instanceof ITradeMessage) {
            PlatformLogging.log("[-] bid recieved from " + id);
            //aServer.onBid(connection, id, (TradeMessage) message);
          }
        } else if (message instanceof IRegistrationMessage) {
          // If connection is not contained, check if it is registration method
          PlatformLogging.log("[-] registration recieved from "
              + connection.getID());
          //aServer.onRegistration(connection, (RegistrationMessage) message);
          return;
        } else if (message instanceof IInformationRequestMessage) {
          PlatformLogging.log("[-] information request recieved from "
              + connection.getID());
        }}});
    Logging.log("[-] server started");
  }
  

  @Override
  public void onRegistration(Connection connection, IRegistrationMessage registrationMessage) {
    if (registrationMessage.getMessageID() == null) {
      Logging.log("[x] AbsServer-onRegistration: encountered null registration");
      return;
    }
    // TODO: try catch
      Integer agentID = this.manager.handleRegistration(registrationMessage, connection); 
      this.kryoServer.sendToTCP(connection.getID(), new RegistrationMessage(registrationMessage.getMessageID(), agentID));
    } 

  @Override
  public void onInformationRequest(
      IInformationRequestMessage informationRequestMessage) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onBid(ITradeMessage bidMessage) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void sendTradeRequest(ITradeRequestMessage tRequestMessage) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void sendInfomationMessage(IInformationMessage informationMessage) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void sendBankUpdate(IBankUpdateMessage bankUpdateMessage) {
    // TODO Auto-generated method stub
    
  }
}
