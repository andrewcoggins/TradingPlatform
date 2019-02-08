package brown.platform.managers.library;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import brown.platform.managers.IDomainManager;
import brown.platform.managers.IMarketManager;
import brown.platform.managers.IWorldManager;
import brown.platform.world.IWorld;
import brown.platform.world.library.World;


public class WorldManagerTest {
  
  @Test
  public void testWorldManager() {
    IDomainManager mockedDomain = mock(DomainManager.class); 
    IMarketManager mockedMarketManager = mock(MarketManager.class); 
    
    IWorldManager worldManager = new WorldManager(); 
    worldManager.createWorld(mockedDomain, mockedMarketManager);
    
    IWorld expectedWorld = new World(mockedDomain, mockedMarketManager); 
    
    assertEquals(worldManager.getWorld(), expectedWorld); 
  }

}
