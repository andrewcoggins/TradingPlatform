package brown.platform.world.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import brown.platform.managers.IDomainManager;
import brown.platform.managers.IMarketManager;
import brown.platform.managers.library.DomainManager;
import brown.platform.managers.library.MarketManager;
import brown.platform.world.IWorld;

public class WorldTest {

  @Test 
  public void testWorld() {
    IDomainManager aManager = new DomainManager(); 
    IMarketManager mManager = new MarketManager() ;
    
    IWorld aWorld = new World(aManager, mManager); 
    
    assertEquals(aWorld.getDomainManager(), aManager); 
    assertEquals(aWorld.getMarketManager(), mManager); 
  }
  
}
