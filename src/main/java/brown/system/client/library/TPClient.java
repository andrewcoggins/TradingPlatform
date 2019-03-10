package brown.system.client.library;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import brown.communication.messages.IRegistrationMessage;
import brown.logging.library.SystemLogging;
import brown.system.client.IClient;
import brown.system.setup.ISetup;
import brown.system.setup.library.Startup;

/**
 * abstract client starts an agent with kryo. 
 * All agents will extend this class.
 * @author andrew
 *
 */
public abstract class TPClient implements IClient {
  
  public final Client CLIENT;
  public Integer ID;
  
 /** 
  * The basic client communication object.
  * 
  * @param host
  * @param port
  * @param gameSetup
  */
  public TPClient(String host, int port, ISetup gameSetup) {
    this.CLIENT = new Client(16384, 8192);
    this.ID = null;
    CLIENT.start();
    //Log.TRACE();
    Kryo agentKryo = CLIENT.getKryo();
    Startup.start(agentKryo);
    if (gameSetup != null) {
      gameSetup.setup(agentKryo);
    }
    try {
      CLIENT.connect(5000, host, port, port);
    } catch (IOException e) {
      SystemLogging.log("[x] Failed to Connect to Server");
      SystemLogging.log(e.toString());
    }
  }  
  
  @Override
  public void onRegistration(IRegistrationMessage registrationMessage) {
    SystemLogging.log("[-] Registered To Server");
    this.ID = registrationMessage.getMessageID();    
    SystemLogging.log("ID: " + this.ID);
  }
  
  @Override
  public void onErrorMessage(IErrorMessage message) {
    SystemLogging.log("[x] rej: " + message.error + ", agent ID: " +this.ID);
  }

  
}