package brown.auction.endowment.distribution.library;

import java.util.LinkedList;
import java.util.List;

import brown.auction.endowment.IEndowment;
import brown.auction.endowment.distribution.IEndowmentDistribution;
import brown.auction.endowment.library.Endowment;
import brown.auction.value.generator.IValuationGenerator;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;

public class IndependentEndowmentDist
    implements IEndowmentDistribution {

  private IValuationGenerator itemGenerator; 
  private IValuationGenerator moneyGenerator; 
  private ICart items;  
  
  
  public IndependentEndowmentDist(ICart items, List<IValuationGenerator> generators) {
    this.items = items; 
    this.itemGenerator = generators.get(0); 
    this.moneyGenerator = generators.get(1); 
  }
  
  @Override
  public IEndowment sample() {
    List<IItem> allItems = new LinkedList<IItem>(); 
    this.items.getItems().forEach(item -> allItems.add(new Item(item.getName(), (int) Math.round(itemGenerator.makeValuation()))));
    return new Endowment(new Cart(allItems), this.moneyGenerator.makeValuation()); 
  }

}
