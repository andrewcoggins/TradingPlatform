package brown.user.main.library;

import brown.user.main.IItemConfig;

public class ItemConfig implements IItemConfig {

  private String itemName;
  private Integer numItems;

  public ItemConfig(String itemName, Integer numitems) {
    this.itemName = itemName;
    this.numItems = numitems;
  }

  @Override
  public String getItemName() {
    return this.itemName;
  }

  @Override
  public Integer getNumItems() {

    return this.numItems;
  }

  @Override
  public String toString() {
    return "itemConfig [itemName=" + itemName + ", numItems=" + numItems + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((numItems == null) ? 0 : numItems.hashCode());
    result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
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
    ItemConfig other = (ItemConfig) obj;
    if (numItems == null) {
      if (other.numItems != null)
        return false;
    } else if (!numItems.equals(other.numItems))
      return false;
    if (itemName == null) {
      if (other.itemName != null)
        return false;
    } else if (!itemName.equals(other.itemName))
      return false;
    return true;
  }

}
