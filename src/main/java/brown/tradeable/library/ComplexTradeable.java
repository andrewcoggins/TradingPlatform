package brown.tradeable.library;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.logging.Logging;
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
    List<SimpleTradeable> simples = this.flatten();
    int[] goods_as_ints = new int[(simples.size())];
    for (int i = 0; i < simples.size(); i++){
      goods_as_ints[i] = simples.get(i).getID();
    }
    return "ComplexTradeable [GOODS=" + Arrays.toString(goods_as_ints) + "]";
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
  
  public static void main(String[] args) throws InterruptedException {
    Set<ITradeable> tset = new HashSet<ITradeable>();
    tset.add(new SimpleTradeable(1));
    tset.add(new SimpleTradeable(2));
    tset.add(new SimpleTradeable(3));
    tset.add(new SimpleTradeable(4));    
    tset.add(new SimpleTradeable(10));    
    tset.add(new SimpleTradeable(25));    
    ComplexTradeable tcompl = new ComplexTradeable(0, tset);
    Logging.log(tcompl.toString());
  }
}
