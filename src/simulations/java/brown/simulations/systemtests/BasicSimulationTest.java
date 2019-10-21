package brown.simulations.systemtests;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import brown.simulations.BasicSimulation;

public class BasicSimulationTest {

  @Test
  public void testBasicSimulation() throws InterruptedException, IOException {
    List<String> agentList = new LinkedList<String>();
    agentList.add("brown.user.agent.library.SimpleAgent");
    BasicSimulation basicSim = new BasicSimulation(agentList,
        "input_configs/second_price_auction.json", "basicSimulationTest", true);
    basicSim.run();
    Thread.sleep(5000);
    Path current = new File(".").toPath(); 
    List<String> pathFiles = new LinkedList<String>(); 
    List<String> testPathFiles = new LinkedList<String>();
    
    Pattern p = Pattern.compile("basicSimulationTest.*.txt"); 
    Matcher m = p.matcher(""); 
    
    Files.list(current).forEach(path -> pathFiles.add(path.toString()));
    
    for (String path : pathFiles) {
      m = p.matcher(path); 
      if (m.find()) {
        testPathFiles.add(path); 
      }
    }
    
    boolean finalUtility = false; 
    boolean solo = false; 
    
    for (String path : testPathFiles) {
      BufferedReader br = new BufferedReader(new FileReader(path));
      
      String line; 
      while((line = br.readLine()) != null) {
        System.out.println(line); 
        if (line.trim().equals("Utility Manager: Final Utility:")) {
          finalUtility = true; 
        } else if (line.trim().equals("solo -> [100.0]")) {
          solo = true; 
        }  
      }
      
      assertTrue(finalUtility); 
      assertTrue(solo);
    }
  }
}
