package brown.auction.value.valuation.library;

import brown.auction.value.valuation.ISpecificValuation;
import brown.platform.item.ICart;

/**
 * a simplified version of the TACValuation, that uses fewer items
 * @author andrewcoggins
 *
 */
public class TACValuationSimple extends AbsSparseValuation
    implements ISpecificValuation {

  private int idealArrivalDate;
  private int idealDepartureDate;
  private int hotelValue;
  private int amusementValue;
  private int alligatorWrestlingValue;
  private int museumValue;

  public TACValuationSimple() {
    this.idealArrivalDate = 0;
    this.idealDepartureDate = 0;
    this.hotelValue = 0;
    this.amusementValue = 0;
    this.alligatorWrestlingValue = 0;
    this.museumValue = 0;
  }

  public TACValuationSimple(int iad, int idd, int hotelValue, int amValue,
      int awValue, int mValue) {
    this.idealArrivalDate = iad;
    this.idealDepartureDate = idd;
    this.hotelValue = hotelValue;
    this.amusementValue = amValue;
    this.alligatorWrestlingValue = awValue;
    this.museumValue = mValue;
  }

  @Override
  public Double getValuation(ICart cart) {

    // feasibility constraints
    if (!isFeasible(cart)) {
      return 0.0;
    }
    
    int arrivalDate = 0;
    int departureDate = 0;
    int hotelBonus = 0;
    int funBonus = 0;
    if (cart.containsItem("dayOneArrivalTicket")) {
      arrivalDate = 1;
    }
    if (cart.containsItem("dayTwoArrivalTicket")) {
      arrivalDate = 2;
    }
    if (cart.containsItem("dayTwoDepartureTicket")) {
      departureDate = 2;
    }
    if (cart.containsItem("dayThreeDepartureTicket")) {
      departureDate = 3;
    }

    int travelPenalty = 100 * (Math.abs(idealArrivalDate - arrivalDate)
        + Math.abs(idealDepartureDate - departureDate));

    if (cart.containsItem("nightOneHotelGood")) {
      hotelBonus = this.hotelValue;
    }

    if (cart.containsItem("amusementDayOneTicket")) {
      funBonus += amusementValue;
    }

    if (cart.containsItem("alligatorDayOneTicket")) {
      funBonus += alligatorWrestlingValue;
    }

    if (cart.containsItem("museumDayOneTicket")) {
      funBonus += museumValue;
    }

    return (double) 1000 - travelPenalty + hotelBonus + funBonus;
  }

  private boolean isFeasible(ICart cart) {

    // departure before arrival
    if (cart.containsItem("dayTwoArrivalTicket")
        && cart.containsItem("dayTwoDepartureTicket")) {
      return false;
    }


    // at most one entertainment event per night, and at most one entertainment
    // event of each type

    if (cart.containsItem("amusementDayOneTicket")) {
      if (cart.getItemByName("amusementDayOneTicket").getItemCount() > 1
          || cart.containsItem("alligatorDayOneTicket")
          || cart.containsItem("museumDayOneTicket")) {
        return false;
      }
    }


    // at least one arrival and one departure
    if (!(cart.containsItem("dayOneArrivalTicket")
        || cart.containsItem("dayTwoArrivalTicket"))
        || (!(cart.containsItem("dayTwoDepartureTicket")
            || cart.containsItem("dayThreeDepartureTicket")))) {
      return false;
    }
    return true;
  }
  
  
}