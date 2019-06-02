package brown.auction.rules;

public abstract class AbsRule {

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    return true; 
  }

  @Override
  public String toString() {
    return "AbsRule []";
  }
  
}
