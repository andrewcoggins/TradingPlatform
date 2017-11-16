package brown.setup.library;

import com.esotericsoftware.kryo.Kryo;

import brown.channels.agent.library.LemonadeChannel;
import brown.messages.library.LemonadeReport;
import brown.setup.ISetup;

/**
 * additional setup for the lemonade game.
 * @author andrew
 *
 */
public class SimpleSetup implements ISetup {

  @Override
  public void setup(Kryo kryo) {
    // TODO Auto-generated method stub
    kryo.register(LemonadeChannel.class);
    kryo.register(LemonadeReport.class);
  } 
  
}