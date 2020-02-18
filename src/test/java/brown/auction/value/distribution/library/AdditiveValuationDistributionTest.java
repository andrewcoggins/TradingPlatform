package brown.auction.value.distribution.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.generator.library.NormalValGenerator;
import brown.auction.value.valuation.ISpecificValuation;
import brown.auction.value.valuation.library.AdditiveValuation;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;

public class AdditiveValuationDistributionTest {

  @Test
  public void testAdditiveValuationDistribution() {
//    Map<String, List<ITradeable>> goods = new HashMap<String, List<ITradeable>>(); 
//    List<ITradeable> ts = new LinkedList<ITradeable>(); 
//    ITradeable t = new Tradeable(0, "default"); 
//    ts.add(t); 
//    goods.put("default", ts);
    
    List<IItem> inputItems = new LinkedList<IItem>(); 
    
    inputItems.add(new Item("default")); 
    ICart goods = new Cart(inputItems); 
    
    List<IValuationGenerator> generators = new LinkedList<IValuationGenerator>(); 
    List<Double> params = new LinkedList<Double>(); 
    params.add(0.0); params.add(1.0); 
    generators.add(new NormalValGenerator(params));
    
    IValuationDistribution dist = new AdditiveValuationDistribution(goods, generators); 
    ISpecificValuation valuation = dist.sample(0, null); 
    
    Map<IItem, Double> vals = new HashMap<IItem, Double>(); 
    IItem anItem = new Item("default"); 
    
    List<IItem> items = new LinkedList<IItem>(); 
    items.add(anItem); 
    
    ICart aCart = new Cart(items); 
    
    vals.put(anItem, valuation.getValuation(aCart)); 
    ISpecificValuation testValuation = new AdditiveValuation(vals); 
    
    assertEquals(valuation, testValuation); 
  }
  
}
