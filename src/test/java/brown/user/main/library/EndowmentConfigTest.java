package brown.user.main.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.user.main.library.EndowmentConfig;

public class EndowmentConfigTest {
  
  @Test
  public void testEndowmentConfigOne() {
    Map<String, Integer> eMap = new HashMap<String, Integer>(); 
    eMap.put("a", 1); 
    EndowmentConfig eConfig = new EndowmentConfig("trade", eMap, 100.0); 
    assertEquals(eConfig.getName(), "trade"); 
    assertEquals(eConfig.getEndowmentMapping(), eMap); 
    assertEquals(eConfig.getFrequency(), new Integer(1)); 
    assertEquals(eConfig.getMoney(), new Double(100.0)); 
  }
  
  @Test
  public void testEndowmentConfigTwo() {
    Map<String, Integer> eMap = new HashMap<String, Integer>(); 
    Map<String, List<String>> iMap = new HashMap<String, List<String>>(); 
    eMap.put("a", 1);
    EndowmentConfig eConfig = new EndowmentConfig("trade", eMap, 100.0); 
    assertEquals(eConfig.getName(), "trade"); 
    assertEquals(eConfig.getEndowmentMapping(), eMap); 
    assertEquals(eConfig.getMoney(), new Double(100.0)); 
    assertEquals(eConfig.getFrequency(), new Integer(1));
    
  }
  
  
  @Test
  public void testEndowmentConfigThree() {
    Map<String, Integer> eMap = new HashMap<String, Integer>(); 
    eMap.put("a", 1);
    EndowmentConfig eConfig = new EndowmentConfig("trade", eMap, 100.0, 5); 
    assertEquals(eConfig.getName(), "trade"); 
    assertEquals(eConfig.getEndowmentMapping(), eMap); 
    assertEquals(eConfig.getMoney(), new Double(100.0)); 
    assertEquals(eConfig.getFrequency(), new Integer(5));
  }
  
  @Test
  public void testEndowmentConfigFour() {
    Map<String, Integer> eMap = new HashMap<String, Integer>(); 
    Map<String, List<String>> iMap = new HashMap<String, List<String>>(); 
    eMap.put("a", 1);
    iMap.put("b", new LinkedList<String>()); 
    EndowmentConfig eConfig = new EndowmentConfig("trade", eMap, 100.0, 5); 
    assertEquals(eConfig.getName(), "trade"); 
    assertEquals(eConfig.getEndowmentMapping(), eMap); 
    assertEquals(eConfig.getMoney(), new Double(100.0)); 
    assertEquals(eConfig.getFrequency(), new Integer(5));
  }
  
}
