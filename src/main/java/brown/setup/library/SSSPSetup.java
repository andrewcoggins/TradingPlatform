package brown.setup.library;

import brown.channels.agent.library.SSSPChannel;
import brown.messages.library.SSSPReportMessage;
import brown.setup.ISetup;

import com.esotericsoftware.kryo.Kryo;

/**
 * Additional setup for SSSP.
 * @author andrew
 */
public class SSSPSetup implements ISetup{

  @Override
  public void setup(Kryo kryo) {
    Startup.start(kryo);
    kryo.register(SSSPChannel.class);
    kryo.register(SSSPReportMessage.class);    
  } 
  
}