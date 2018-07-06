package brown.user.agent.library;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import brown.platform.accounting.library.Ledger;
import brown.platform.accounting.library.Transaction;
import brown.platform.twosided.BuyOrder;
import brown.platform.twosided.SellOrder;

/**
 * Library with static methods to support IEBot.s
 * 
 * @author Enrique Areyan Viqueira
 * @author Ishaan Agarwal
 */
public class IELib {

  protected static final Table<Integer, Boolean, Double> InitialValues = HashBasedTable
      .create();;

  /**
   * Initialize static structures.
   */
  static {
    for (int i = 1; i < 5; i++) {
      IELib.InitialValues.put(i, true, getFairValue(true, i));
      IELib.InitialValues.put(i, false, getFairValue(false, i));
    }
  }

  /**
   * Given a buy book, returns highest buying price.
   * 
   * @param buyBook
   * @return
   */
  public static int HighestBuyer(Map<Double, Double> buyBook) {

    int max = 0;
    for (Entry<Double, Double> Entry : buyBook.entrySet()) {
      int price = Entry.getKey().intValue();
      if (max < price) {
        max = price;
      }
    }
    return max;
  }

  /**
   * Given a sell book, returns the lowest selling price.
   * 
   * @param Book
   * @return
   */
  public static int LowestSeller(Map<Double, Double> Book) {

    int min = 100;
    for (Entry<Double, Double> Entry : Book.entrySet()) {
      int price = Entry.getKey().intValue();
      if (min > price) {
        min = price;
      }
    }
    return min;
  }

  /**
   * Given the buy book, returns a list of predictions of user informations
   * 
   * @param buys
   */
  public static List<CoinInfo> BookAnalysis(Object[] buys,
      Object[] sells, Set<Integer> buyHist,
      Set<Integer> sellHist) {

    
    int threshold = 50;
    double temp = threshold;

    List<CoinInfo> prediction = new ArrayList<CoinInfo>();
    for ( Object a : buys) {
      
      BuyOrder e = (BuyOrder) a;
      
      if(e.price == null) continue;
      int price = (int)e.price.doubleValue();
      if (price < threshold || price < temp || buyHist.contains(price)) {
        continue;
      }
      // int quantity = buyBookEntry.getValue().intValue();
      // System.out.println("price = " + price + ", quantity = " + quantity);
      for (int i = InitialValues.size() / 2; i > 0; i--) {
        double tempfv = InitialValues.get(i, true);
        if (price < tempfv) {
          if (!prediction.contains(new CoinInfo(true, i))) {
            prediction.add(new CoinInfo(true, i));
            temp = tempfv;
          }
        }
        // System.out.println(x);
      }
    }
    temp = threshold;
    for (Object a : sells) {
      SellOrder e = (SellOrder) a;
      if(e.price == null) continue;
      int price = (int)e.price.doubleValue();
      if (price > threshold || price > temp || sellHist.contains(price)) {
        continue;
      }
      // int quantity = sellBookEntry.getValue().intValue();
      // System.out.println("price = " + price + ", quantity = " + quantity);
      for (int i = InitialValues.size() / 2; i > 0; i--) {
        double tempfv = InitialValues.get(i, false);
        if (price > tempfv) {
          if (!prediction.contains(new CoinInfo(false, i))) {
            prediction.add(new CoinInfo(false, i));
            temp = tempfv;
          }
        }
        // System.out.println(x);
      }
    }

    return prediction;
  }

  /**
   * Given whether a coin was heads or tails, and the number of Decoys, returns
   * the fair value estimate of the contract.
   * 
   * @param head
   * @param numOfDecoys
   * @return
   */
  public static double getFairValue(boolean head, int numOfDecoys) {

    List<CoinInfo> listOfInfo = new ArrayList<CoinInfo>();
    listOfInfo.add(new CoinInfo(head, numOfDecoys));
    return IELib.formula(listOfInfo);
  }

  /**
   * Compute the average of the ledger.
   * 
   * @param l
   * @param history
   * @return
   */
  public static double average(Ledger l, int history) {

    List<Transaction> t = l.getList();

    double total = 0, quantity = 0;
    Transaction temp;

    for (int i = 0; i < Math.min(t.size(), history); i++) {
      temp = t.get(i);
      total += temp.PRICE * temp.QUANTITY;
      quantity += temp.QUANTITY;
    }
    return total / quantity;
  }

  /**
   * Given a list of all market participants' information, i.e., how many decoys
   * and the head/tails value; computes the fair value of the contract.
   * 
   * @param listOfInfo
   *          - a list with the information of market participants.
   * @return the fair value of the contract, a number between 0 and 100.
   */
  public static double formula(List<CoinInfo> listOfInfo) {

    double fairValue = 0.0;
    double termA = 1, termB = 1;

    for (CoinInfo coinInfo : listOfInfo) {
      termA *= (2 * coinInfo.getInfo() + coinInfo.getNumberOfDecoys());
      termB *= (2 * (1 - coinInfo.getInfo()) + coinInfo.getNumberOfDecoys());
    }
    fairValue = termA / (termA + termB);

    return fairValue * 100;
  }

  /**
   * Given the private info and the list of predictions, returns an epsilon,
   * i.e., a range of bidding.
   * 
   * @param MyDecoys
   * @param listOfInfo
   * @return
   */
  public static double epsilon(int MyDecoys, List<CoinInfo> listOfInfo) {

    double e = 4 * Math.log(MyDecoys);

    double myConfidence = 0;
    double totalConfidence = 20;

    for (CoinInfo coinInfo : listOfInfo) {
      myConfidence += Math.max((5 - coinInfo.getNumberOfDecoys()), 0);
    }

    double scale = 1 - (myConfidence / totalConfidence);

    return Math.max(1.0, e * scale);
  }

}