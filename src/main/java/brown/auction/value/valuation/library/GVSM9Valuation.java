package brown.auction.value.valuation.library;

import java.util.HashMap;
import java.util.Map;

import brown.auction.value.valuation.ISpecificValuation;
import brown.platform.item.ICart;
import brown.platform.item.IItem;

public class GVSM9Valuation extends AbsSparseValuation implements ISpecificValuation {
  
  private static final int NATL = 0, REG1 = 1, REG2 = 2, REG3 = 3;
  
  private int agentType;
  private Map<String, Double> vals;
  
  public GVSM9Valuation() {
	  super();
	  this.agentType = 0;
	  this.vals = new HashMap<>();
  }
  
  public GVSM9Valuation(int agentType) {
	  super();
	  this.agentType = agentType;
	  this.vals = new HashMap<>();
	  makeValuations();
  }
  
  // rand val up to
  private double rv(double max) {
	  return max * Math.random();
  }
  
  private void putVals(int a, int b, int c, int d,
		  int e, int f, int g, int h, int i) {
	  this.vals.put("A", (a == 0) ? -1 : rv(a));
	  this.vals.put("B", (b == 0) ? -1 : rv(b));
	  this.vals.put("C", (c == 0) ? -1 : rv(c));
	  this.vals.put("D", (d == 0) ? -1 : rv(d));
	  this.vals.put("E", (e == 0) ? -1 : rv(e));
	  this.vals.put("F", (f == 0) ? -1 : rv(f));
	  this.vals.put("G", (g == 0) ? -1 : rv(g));
	  this.vals.put("H", (h == 0) ? -1 : rv(h));
	  this.vals.put("I", (i == 0) ? -1 : rv(i));
  }
  
  private void makeValuations() {
	  this.vals.clear();
	  this.vals.put("agentType", this.agentType + 0.0);
	  
	  switch (this.agentType) {
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
    	if (item.getName().equals("agentType")) {
    		return this.vals.get("agentType");
    	}
    	
    	val += this.vals.get(item.getName());
    }
    return val * Math.pow(1.2, cart.getItems().size() - 1);
  }
}
