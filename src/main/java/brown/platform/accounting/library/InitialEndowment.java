package brown.platform.accounting.library;

import brown.platform.accounting.IInitialEndowment;
import brown.platform.tradeable.ITradeable;

import java.util.List;
import java.util.Map;

/**
 * Initial Endowment specifies what an agent has at the beginning of a game.
 */
public class InitialEndowment implements IInitialEndowment {

    private double money;
    private Map<String, List<ITradeable>> goods;

    /**
     * Agent's initial endowment.
     * @param money starting money
     * @param goods starting goods.
     */
    public InitialEndowment(double money, Map<String, List<ITradeable>> goods) {
        this.money = money;
        this.goods = goods;
    }

    @Override
    public double getMoney() {
      return this.money;
    }

    @Override
    public Map<String, List<ITradeable>> getGoods() {
      return this.goods;
    }

    @Override
    public String toString() {
      return "InitialEndowment [money=" + money + ", goods=" + goods + "]";
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((goods == null) ? 0 : goods.hashCode());
      long temp;
      temp = Double.doubleToLongBits(money);
      result = prime * result + (int) (temp ^ (temp >>> 32));
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
      InitialEndowment other = (InitialEndowment) obj;
      if (goods == null) {
        if (other.goods != null)
          return false;
      } else if (!goods.equals(other.goods))
        return false;
      if (Double.doubleToLongBits(money) != Double
          .doubleToLongBits(other.money))
        return false;
      return true;
    }
    
    
}
