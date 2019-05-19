package brown.system.setup;

import com.esotericsoftware.kryo.Kryo;

/**
 * Setup registers necessary classes with kryo.
 * 
 * @author andrewcoggins
 *
 */
public interface ISetup {

  /**
   * registers necessary classes with kryo.
   * 
   * @param kryo
   */
  void setup(Kryo kryo);

}
