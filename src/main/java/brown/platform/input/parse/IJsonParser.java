package brown.platform.input.parse;

import java.util.List;

import brown.platform.input.config.library.SimulationConfig;

public interface IJsonParser extends IInputParser {
  
  public List<SimulationConfig> parseJSON(String jsonString); 
  
}