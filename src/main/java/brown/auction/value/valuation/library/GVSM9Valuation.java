package brown.auction.value.valuation.library;

import java.util.HashMap;
import java.util.Map;

import brown.auction.value.valuation.ISpecificValuation;
import brown.platform.item.ICart;
import brown.platform.item.IItem;

public class GVSM9Valuation extends AbsSparseValuation implements ISpecificValuation {
  
  private static final int NATL = 0, REG1 = 1, REG2 = 2, REG3 = 3;
  
  private int agentNum;
  private Map<String, Double> vals;
  
  public GVSM9Valuation(int agentNum) {
	  this.agentNum = agentNum;
	  this.vals = new HashMap<>();
	  makeValuations();
  }
  
  // rand val up to
  private double rv(double max) {
	  return max * Math.random();
  }
  
  private void putVals(double a, double b, double c, double d,
		  double e, double f, double g, double h, double i) {
	  this.vals.put("A", rv(a));
	  this.vals.put("B", rv(b));
	  this.vals.put("C", rv(c));
	  this.vals.put("D", rv(d));
	  this.vals.put("E", rv(e));
	  this.vals.put("F", rv(f));
	  this.vals.put("G", rv(g));
	  this.vals.put("H", rv(h));
	  this.vals.put("I", rv(i));
  }
  
  private void makeValuations() {
	  this.vals.clear();
	  
	  switch (this.agentNum) {
		  case NATL:
			  this.putVals(15, 15, 30, 30, 15, 15, 0, 0, 0);
			  break;
		  case REG1:
			  this.putVals(20, 20, 40, 40, 0, 0, 20, 0, 0);
			  break;
		  case REG2:
			  this.putVals(0, 0, 40, 40, 20, 20, 0, 20, 0);
			  break;
		  case REG3:
			  this.putVals(20, 20, 0, 0, 20, 20, 0, 0, 20);
			  break;
	  }
  }
  
  public Double getValuation(ICart cart) {
	double val = 0;
    for (IItem item : cart.getItems()) {
    	val = 1.2 * (val + this.vals.get(item.getName()));
    }
    return val;
  }
}
