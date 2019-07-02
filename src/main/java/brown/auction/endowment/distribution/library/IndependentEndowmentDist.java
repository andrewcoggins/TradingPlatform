package brown.auction.endowment.distribution.library;

import java.util.LinkedList;
import java.util.List;

import brown.auction.endowment.IEndowment;
import brown.auction.endowment.distribution.IEndowmentDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.platform.item.IItem;
import brown.platform.item.library.Item;

public class IndependentEndowmentDist
    implements IEndowmentDistribution {

  private IValuationGenerator itemGenerator; 
  private IValuationGenerator moneyGenerator; 
  private List<String> itemNames; 
  
  
  public IndependentEndowmentDist(List<String> items, IValuationGenerator itemGenerator, IValuationGenerator moneyGenerator) {
    this.itemNames = items; 
    this.itemGenerator = itemGenerator; 
    this.moneyGenerator = moneyGenerator; 
  }
  
  @Override
  public IEndowment sample() {
    List<IItem> allItems = new LinkedList<IItem>(); 
    this.itemNames.forEach(itemName -> allItems.add(new Item(itemName, (int) Math.round(itemGenerator.makeValuation()))));
    // TODO Auto-generated method stub
    return null;
  }

}
