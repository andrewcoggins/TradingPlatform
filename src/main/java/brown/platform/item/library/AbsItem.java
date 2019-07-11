package brown.platform.item.library;

import brown.platform.item.IItem;


public abstract class AbsItem implements IItem {

  private Integer count; 
  private String name; 
  
  public AbsItem() {
    this.name = null; 
    this.count = null; 
  }
  
  public AbsItem(String name, int count) {
    this.name = name; 
    this.count = count; 
  }

  @Override
  public String getName() {
    return this.name;
  }
  
  @Override
  public void addItemCount(int itemCount) {
      this.count += itemCount; 
  }
  
  @Override 
  public void removeItemCount(int itemCount) {
    this.count -= itemCount; 
  }
  
  @Override
  public int getItemCount() {
    return this.count;
  }

  @Override
  public String toString() {
    return "AbsItem [count=" + count + ", name=" + name + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + count;
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
    AbsItem other = (AbsItem) obj;
    if (count != other.count)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

}
