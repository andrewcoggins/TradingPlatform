package brown.user.main;

import java.util.List;

import brown.user.main.library.SimulationConfig;

public interface IJsonParser extends IInputParser {
  
  public List<SimulationConfig> parseJSON(String jsonString); 
  
}