package brown.setup;

import com.esotericsoftware.kryo.Kryo;

/**
 * set up Kryo, which handles agent-server communications.
 * @author lcamery
 *
 */
public interface ISetup {
	void setup(Kryo kryo);
}
