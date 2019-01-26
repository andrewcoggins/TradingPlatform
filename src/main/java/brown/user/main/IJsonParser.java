package brown.user.main;

import java.util.List;

public interface IJsonParser extends IInputParser {
  
  public List<SimulationConfig> parseJSON(String jsonString); 
  
}