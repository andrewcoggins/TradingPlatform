package brown.system.setup;

import com.esotericsoftware.kryo.Kryo;

/**
 * Set up Kryo, which handles agent-server communications.
 * @author lcamery
 */
public interface ISetup {
  
  void setup(Kryo kryo);
	
}
