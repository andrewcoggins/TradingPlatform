package brown.user.main.library;

import java.util.Map;

import brown.user.main.IEndowmentConfig;

public class EndowmentConfig implements IEndowmentConfig {
   
  private String name; 
  private Map<String, Integer> endowmentMapping; 
  private Double money; 
  private Integer frequency; 
  
  
  public EndowmentConfig(String name, Map<String, Integer> endowmentMapping, Double money) {
    this.name = name; 
    this.endowmentMapping = endowmentMapping;
    this.money = money;
    this.frequency = 1; 
  }
  

  
  public EndowmentConfig(String name, Map<String, Integer> endowmentMapping, Double money, Integer frequency) {
    this.name = name; 
    this.endowmentMapping = endowmentMapping; 
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
  public Double getMoney() {
    return this.money;
  }

  @Override
  public Integer getFrequency() {
    return this.frequency;
  }

  @Override
  public String toString() {
    return "EndowmentConfig [name=" + name + ", endowmentMapping="
        + endowmentMapping + ", money=" + money + ", frequency=" + frequency
        + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((endowmentMapping == null) ? 0 : endowmentMapping.hashCode());
    result = prime * result + ((frequency == null) ? 0 : frequency.hashCode());
    result = prime * result + ((money == null) ? 0 : money.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    EndowmentConfig other = (EndowmentConfig) obj;
    if (endowmentMapping == null) {
      if (other.endowmentMapping != null)
        return false;
    } else if (!endowmentMapping.equals(other.endowmentMapping))
      return false;
    if (frequency == null) {
      if (other.frequency != null)
        return false;
    } else if (!frequency.equals(other.frequency))
      return false;
    if (money == null) {
      if (other.money != null)
        return false;
    } else if (!money.equals(other.money))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
  
}