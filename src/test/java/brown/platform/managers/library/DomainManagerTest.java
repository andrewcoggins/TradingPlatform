package brown.platform.managers.library;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import brown.platform.domain.Domain;
import brown.platform.domain.IDomain;
import brown.platform.managers.IAccountManager;
import brown.platform.managers.IDomainManager;
import brown.platform.managers.IEndowmentManager;
import brown.platform.managers.IItemManager;
import brown.platform.managers.IValuationManager;

public class DomainManagerTest {
  
  @Test
  public void testDomainManager() {
    IItemManager mockedTManager = mock(ItemManager.class); 
    IValuationManager mockedVManager = mock(ValuationManager.class); 
    IAccountManager mockedAManager = mock(AccountManager.class); 
    IEndowmentManager mockedEManager = mock(EndowmentManager.class); 
    
    IDomainManager dManager = new DomainManager(); 
    
    dManager.createDomain(mockedTManager, mockedVManager, mockedAManager, mockedEManager); 
    IDomain expected = new Domain(mockedTManager, mockedVManager, mockedAManager, mockedEManager); 
    assertEquals(dManager.getDomain(), expected); 
    
  }
  
}
