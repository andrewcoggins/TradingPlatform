package brown.platform.input.config.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.platform.input.config.IEndowmentConfig;

public class EndowmentConfig implements IEndowmentConfig {
   
  private String name; 
  private Map<String, Integer> endowmentMapping; 
  private Map<String, List<String>> includeMapping; 
  private Double money; 
  private Integer frequency; 
  
  
  public EndowmentConfig(String name, Map<String, Integer> endowmentMapping, Double money) {
    this.name = name; 
    this.endowmentMapping = endowmentMapping;
    this.includeMapping = new HashMap<String, List<String>>(); 
    this.money = money;
    this.frequency = 1; 
  }
  
  public EndowmentConfig(String name, Map<String, Integer> endowmentMapping, Map<String, List<String>> includeMapping, Double money) {
    this.name = name; 
    this.endowmentMapping = endowmentMapping;
    this.includeMapping = includeMapping; 
    this.money = money;
    this.frequency = 1; 
  }
  
  public EndowmentConfig(String name, Map<String, Integer> endowmentMapping, Double money, Integer frequency) {
    this.name = name; 
    this.endowmentMapping = endowmentMapping; 
    this.includeMapping = new HashMap<String, List<String>>();
    this.money = money;
    this.frequency = frequency; 
  }
  
  public EndowmentConfig(String name, Map<String, Integer> endowmentMapping,  Map<String, List<String>> includeMapping, Double money, Integer frequency) {
    this.name = name; 
    this.endowmentMapping = endowmentMapping; 
    this.includeMapping = includeMapping; 
    this.money = money;
    this.frequency = frequency; 
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Map<String, Integer> getEndowmentMapping() {
    return this.endowmentMapping;
  }

  @Override
  public Map<String, List<String>> getIncludeMapping() {
    return this.includeMapping;
  }

  @Override
  public Double getMoney() {
    return this.money;
  }

  @Override
  public Integer getFrequency() {
    return this.frequency;
  }
  
}