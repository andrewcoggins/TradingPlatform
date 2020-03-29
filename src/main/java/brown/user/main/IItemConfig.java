package brown.user.main;

/**
 * config for specifying tradeables from user-given input. See implementation
 * for details.
 * 
 * @author andrewcoggins
 *
 */
public interface IItemConfig extends IInputConfig {

  /**
   * get item name.
   * 
   * @return
   */
  public String getItemName();

  /**
   * get number of items
   * 
   * @return
   */
  public Integer getNumItems();

}
