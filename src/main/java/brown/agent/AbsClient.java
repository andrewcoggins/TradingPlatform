package brown.agent;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import brown.exceptions.AgentCreationException;
import brown.messages.library.ErrorMessage;
import brown.messages.library.RegistrationMessage;
import brown.setup.ISetup;
import brown.setup.Logging;
import brown.setup.library.Startup;

/**
 * abstract client starts an agent with kryo. 
 * All agents will extend this class.
 * @author andrew
 *
 */
public abstract class AbsClient {
  
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
  public AbsClient(String host, int port, ISetup gameSetup) throws AgentCreationException {
    this.CLIENT = new Client(8192, 4096);
    this.ID = null;

    CLIENT.start();
    Log.TRACE();
    Kryo agentKryo = CLIENT.getKryo();
    Startup.start(agentKryo);
    if (gameSetup != null) {
      gameSetup.setup(agentKryo);
    }

    try {
      CLIENT.connect(5000, host, port, port);
    } catch (IOException e) {
      throw new AgentCreationException("Failed to connect to server");
    }
  }  
  
  public void onRegistration(RegistrationMessage registration) {
    Logging.log("[-] Registered To Server");
    this.ID = registration.getID();    
  }
  
  public void onErrorMessage(ErrorMessage message) {
    Logging.log("[x] rej: " + message.error + ", agent ID: " +this.ID);
  }  
}