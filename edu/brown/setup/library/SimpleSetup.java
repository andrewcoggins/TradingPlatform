package brown.setup.library;

import com.esotericsoftware.kryo.Kryo;

import brown.channels.library.LemonadeChannel;
import brown.messages.markets.LemonadeReport;
import brown.setup.Setup;

/**
 * additional setup for the lemonade game.
 * @author andrew
 *
 */
public class SimpleSetup implements Setup {

  @Override
  public void setup(Kryo kryo) {
    // TODO Auto-generated method stub
    kryo.register(LemonadeChannel.class);
    kryo.register(LemonadeReport.class);
  } 
  
}