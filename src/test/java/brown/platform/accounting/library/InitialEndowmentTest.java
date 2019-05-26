package brown.platform.accounting.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.platform.accounting.IInitialEndowment;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;


public class InitialEndowmentTest {

    @Test
    public void testInitialEndowment() {
       
      List<IItem> initItems = new LinkedList<IItem>(); 
      
      initItems.add(new Item("default", 2));
      
      ICart aCart = new Cart(initItems); 
      
      IInitialEndowment anEndowment = new InitialEndowment(100.0, aCart); 
      
      assertTrue(anEndowment.getMoney() == 100.0); 
      assertTrue(anEndowment.getMoney() != 50.0); 
      
      assertEquals(anEndowment.getGoods(), aCart); 
      
      List<IItem> otherItems = new LinkedList<IItem>(); 
      otherItems.add(new Item("a", 1)); 
     
      assertTrue(!(anEndowment.getGoods().equals(new Cart(otherItems)))); 
      
    }
    
}
