package brown.tradeable.library;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.tradeable.ITradeable;

/**
* A complex tradeable is a set of tradeables.
* COUNT must be 1!
* @author amy
*/
public class ComplexTradeable extends AbsTradeable { 
  
  public final int ID; 
  public final Set<ITradeable> GOODS;
  /**
   * For Kryo
   * DO NOT USE
   */
  public ComplexTradeable() {
    super();
    this.GOODS = null;
    this.ID = 0; 
  }
  
  public ComplexTradeable(Integer ID, Set<ITradeable> GOODS) {
    super(ID, 1, TradeableType.Complex);
    this.GOODS = GOODS;
    this.ID = ID; 
  }
  

  @Override
  public String toString() {
    return "ComplexTradeable [ID=" + ID + ", GOODS=" + GOODS + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((GOODS == null) ? 0 : GOODS.hashCode());
    result = prime * result + ID;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ITradeable && ((ITradeable) obj).getCount().equals(this.COUNT)){
      List<SimpleTradeable> thisList = this.flatten();
      List<SimpleTradeable> otherList = ((ITradeable) obj).flatten();
      
      for (SimpleTradeable t: thisList){
        if (!otherList.remove(t)){
          return false;
        }
      }
      return true; 
    } else {
      return false;
    }
  }

  @Override
  public List<SimpleTradeable> flatten(){
    LinkedList<SimpleTradeable> toReturn = new LinkedList<SimpleTradeable>();
    for (ITradeable t: this.GOODS){
      toReturn.addAll(t.flatten());
    }
    return toReturn;
  }
}
