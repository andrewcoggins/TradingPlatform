package brown.system.client.library;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import brown.communication.messages.IRegistrationResponseMessage;
import brown.communication.messages.IStatusMessage;
import brown.logging.library.SystemLogging;
import brown.system.client.IClient;
import brown.system.setup.ISetup;
import brown.system.setup.library.Setup;

public abstract class TPClient implements IClient {

  public final Client CLIENT;
  public Integer ID;
  public Integer publicID;
  public String simulationJsonFileName; 

  /**
   * TPClient constructor
   * 
   * @param host the name of the host. If local, this is "localhost"
   * @param port the port number
   * @param setup a class that registers the necessary classes with kryo
   */
  public TPClient(String host, int port, ISetup setup) {
    this.CLIENT = new Client(16384, 8192);
    this.ID = null;
    this.publicID = null; 
    CLIENT.start();
    Kryo agentKryo = CLIENT.getKryo();
    Setup.start(agentKryo);
    if (setup != null) {
      setup.setup(agentKryo);
    }
    try {
      CLIENT.connect(5000, host, port, port);
    } catch (IOException e) {
      SystemLogging.log("[x] Failed to Connect to Server");
      SystemLogging.log(e.toString());
    }
  }

  @Override
  public void
      onRegistrationResponse(IRegistrationResponseMessage registrationMessage) {
    SystemLogging.log("[-] Registered To Server");
    this.ID = registrationMessage.getAgentID(); 
    this.publicID = registrationMessage.getPublicAgentID(); 
    this.simulationJsonFileName = registrationMessage.getSimulationJsonFileName(); 
    SystemLogging.log("Private ID: " + this.ID);
    SystemLogging.log("Public ID: " + this.publicID);
    SystemLogging.log("simulation JSON filename: " + this.simulationJsonFileName);
  }

  @Override
  public void onStatusMessage(IStatusMessage message) {
    SystemLogging
        .log("[x] rej: " + message.getStatus() + ", agent ID: " + this.ID);
  }

}
