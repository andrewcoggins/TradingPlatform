package brown.system.client;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import brown.logging.Logging;
import brown.platform.messages.ErrorMessage;
import brown.platform.messages.RegistrationMessage;
import brown.platform.messages.StringMessage;
import brown.system.setup.ISetup;
import brown.system.setup.Startup;

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
  * @throws AgentCreationException
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
      Logging.log("Failed to Connect to Server");
      System.out.println(e); 
    }
  }  
  
  @Override
  public void onRegistration(RegistrationMessage registration) {
    Logging.log("[-] Registered To Server");
    this.ID = registration.getID();    
    Logging.log("ID: " + this.ID);
  }
  
  @Override
  public void onErrorMessage(ErrorMessage message) {
    Logging.log("[x] rej: " + message.error + ", agent ID: " +this.ID);
  }

  @Override
  public void onStringMessage(StringMessage message) {
    Logging.log("[-] Message Received: " + message.message + ", agent ID: " +this.ID);
  }  
}