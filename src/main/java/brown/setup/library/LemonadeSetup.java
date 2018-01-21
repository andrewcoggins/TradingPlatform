package brown.setup.library;

import com.esotericsoftware.kryo.Kryo;

import brown.channels.agent.library.LemonadeChannel;
import brown.messages.library.LemonadeReportMessage;
import brown.setup.ISetup;

/**
 * Additional setup for the Lemonade game.
 * @author andrew
 */
public class LemonadeSetup implements ISetup {

  @Override
  public void setup(Kryo kryo) {
    Startup.start(kryo);
    kryo.register(LemonadeChannel.class);
    kryo.register(LemonadeReportMessage.class);
  } 
  
}