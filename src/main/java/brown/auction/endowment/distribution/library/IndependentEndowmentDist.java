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

public class IndependentEndowmentDist extends AbsEndowmentDistribution
    implements IEndowmentDistribution {

  public IndependentEndowmentDist(ICart items,
      List<IValuationGenerator> generators) {
    super(items, generators);
  }

  @Override
  public IEndowment sample() { 
    IValuationGenerator itemGenerator = this.generators.get(0);
    IValuationGenerator moneyGenerator = this.generators.get(1);

    List<IItem> allItems = new LinkedList<IItem>();
    this.items.getItems().forEach(item -> allItems.add(new Item(item.getName(),
        (int) Math.round(itemGenerator.makeValuation()))));
    return new Endowment(new Cart(allItems), moneyGenerator.makeValuation());
  }

}
