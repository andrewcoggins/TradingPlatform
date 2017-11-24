package brown.agent;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import brown.exceptions.AgentCreationException;
import brown.setup.ISetup;
import brown.setup.Startup;

public abstract class AbsClient {
  
  public final Client CLIENT;
  
 public Integer ID;
  
 /** 
  * the basic client communication object. 
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
}