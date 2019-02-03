package brown.platform.accounting;

import java.util.List;
import java.util.Map;

import brown.mechanism.tradeable.ITradeable;

/**
 * Accounts store agent monies and tradeables.
 * @author andrew
 *
 */
public interface IAccount {
  
  public int getID();
  
  public double getMoney(); 
  
  public List<ITradeable> getGoods(String name); 
  
  public Map<String, List<ITradeable>> getAllGoods(); 
  
  public void addTradeables(String name, List<ITradeable> tradeables); 
  
  public void removeTradeables(String name, List<ITradeable> tradeables); 
  
  public void addMoney(double money); 
  
  public void removeMoney(double newMoney); 
  
  public void clear(); 
  
}
