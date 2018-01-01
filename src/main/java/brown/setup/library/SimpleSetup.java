package brown.setup.library;

import com.esotericsoftware.kryo.Kryo;

import brown.channels.agent.library.LemonadeChannel;
import brown.messages.library.GameReportMessage;
import brown.messages.library.LemonadeReportMessage;
import brown.setup.ISetup;
import brown.setup.Startup;

/**
 * additional setup for the lemonade game.
 * @author andrew
 *
 */
public class SimpleSetup implements ISetup {

  @Override
  public void setup(Kryo kryo) {
    // TODO Auto-generated method stub
    Startup.start(kryo);
    kryo.register(GameReportMessage.class);
    kryo.register(LemonadeChannel.class);
    kryo.register(LemonadeReportMessage.class);
  } 
  
}