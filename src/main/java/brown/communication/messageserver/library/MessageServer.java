package brown.communication.messageserver.library;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IInformationRequestMessage;
import brown.communication.messages.IRegistrationMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.library.RegistrationMessage;
import brown.communication.messages.library.TradeMessage;
import brown.communication.messageserver.IMessageServer;
import brown.logging.library.Logging;
import brown.system.kryoserver.library.KryoServer;
import brown.system.setup.ISetup;
import brown.system.setup.library.Startup;

/**
 * Message passing server for Trading Platform
 * 
 * @author acoggins
 *
 */
public class MessageServer extends KryoServer implements IMessageServer {
  protected Server theServer;
  
  public MessageServer(int port, ISetup gameSetup) {
    super(port, gameSetup);
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

    final MessageServer aServer = this;
    theServer.addListener(new Listener() {
      public void received(Connection connection, Object message) {
        if (connections.containsKey(connection)) {
          // If the connection is already contained, check if message is a trade
          int id = connections.get(connection);
          if (message instanceof TradeMessage) {
            Logging.log("[-] bid recieved from " + id);
            //aServer.onBid(connection, id, (TradeMessage) message);
          }
        } else if (message instanceof RegistrationMessage) {
          // If connection is not contained, check if it is registration method
          Logging.log("[-] registration recieved from "
              + connection.getID());
          //aServer.onRegistration(connection, (RegistrationMessage) message);
          return;
        }}});
    Logging.log("[-] server started");
  }
  

  @Override
  public void openRegistration() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onRegistration(IRegistrationMessage registrationMessage) {
    // TODO Auto-generated method stub
    
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
}
