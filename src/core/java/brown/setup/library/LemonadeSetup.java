package brown.setup.library;

import com.esotericsoftware.kryo.Kryo;

import brown.bid.library.GameBid;
import brown.bidbundle.library.GameBidBundle;
import brown.channels.library.GameChannel;
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
    kryo.register(GameBidBundle.class);
    kryo.register(GameBid.class);    
    kryo.register(GameChannel.class);
    kryo.register(LemonadeReportMessage.class);
    kryo.register(java.util.List[].class);    
  } 
  
}