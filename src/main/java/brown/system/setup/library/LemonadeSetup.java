package brown.system.setup.library;

import com.esotericsoftware.kryo.Kryo;

import brown.mechanism.bid.GameBid;
import brown.mechanism.bidbundle.GameBidBundle;
import brown.mechanism.channel.GameChannel;
import brown.platform.messages.LemonadeReportMessage;
import brown.system.setup.ISetup;

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