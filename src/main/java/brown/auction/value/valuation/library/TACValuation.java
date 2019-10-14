package brown.auction.value.valuation.library;

import brown.auction.value.valuation.ISpecificValuation;
import brown.platform.item.ICart;

public class TACValuation extends AbsSparseValuation
    implements ISpecificValuation {

  private int idealArrivalDate;
  private int idealDepartureDate;
  private int hotelValue;
  private int amusementValue;
  private int alligatorWrestlingValue;
  private int museumValue;

  public TACValuation() {
    this.idealArrivalDate = 0;
    this.idealDepartureDate = 0;
    this.hotelValue = 0;
    this.amusementValue = 0;
    this.alligatorWrestlingValue = 0;
    this.museumValue = 0;
  }

  public TACValuation(int iad, int idd, int hotelValue, int amValue,
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
    if (cart.containsItem("dayThreeArrivalTicket")) {
      arrivalDate = 3;
    }
    if (cart.containsItem("dayFourArrivalTicket")) {
      arrivalDate = 4;
    }
    if (cart.containsItem("dayTwoDepartureTicket")) {
      departureDate = 2;
    }
    if (cart.containsItem("dayThreeDepartureTicket")) {
      departureDate = 3;
    }
    if (cart.containsItem("dayFourDepartureTicket")) {
      departureDate = 4;
    }
    if (cart.containsItem("dayFiveDepartureTicket")) {
      departureDate = 5;
    }

    int travelPenalty = 100 * (Math.abs(idealArrivalDate - arrivalDate)
        + Math.abs(idealDepartureDate - departureDate));

    if (cart.containsItem("nightOneHotelGood")
        || cart.containsItem("nightTwoHotelGood")
        || cart.containsItem("nightThreeHotelGood")
        || cart.containsItem("nightFourHotelGood")) {
      hotelBonus = this.hotelValue;
    }

    if (cart.containsItem("amusementDayOneTicket")
        || cart.containsItem("amusementDayTwoTicket")
        || cart.containsItem("amusementDayThreeTicket")
        || cart.containsItem("amusementDayFourTicket")) {
      funBonus += amusementValue;
    }

    if (cart.containsItem("alligatorDayOneTicket")
        || cart.containsItem("alligatorDayTwoTicket")
        || cart.containsItem("alligatorDayThreeTicket")
        || cart.containsItem("alligatorDayFourTicket")) {
      funBonus += alligatorWrestlingValue;
    }

    if (cart.containsItem("museumDayOneTicket")
        || cart.containsItem("museumDayTwoTicket")
        || cart.containsItem("museumDayThreeTicket")
        || cart.containsItem("museumDayFourTicket")) {
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
    if (cart.containsItem("dayThreeArrivalTicket")
        && (cart.containsItem("dayThreeDepartureTicket")
            || cart.containsItem("dayTwoDepartureTicket"))) {
      return false;
    }
    if (cart.containsItem("dayFourArrivalTicket")
        && (cart.containsItem("dayFourDepartureTicket")
            || cart.containsItem("dayThreeDepartureTicket")
            || cart.containsItem("dayTwoDepartureTicket"))) {
      return false;
    }

    // same hotel every night
    if (cart.containsItem("nightOneHotelGood")) {
      if (cart.containsItem("nightTwoHotelFair")
          || cart.containsItem("nightThreeHotelFair")
          || cart.containsItem("nightFourHotelFair")) {
        return false;
      }
    }

    if (cart.containsItem("nightTwoHotelGood")) {
      if (cart.containsItem("nightThreeHotelFair")
          || cart.containsItem("nightFourHotelFair")) {
        return false;
      }
    }

    if (cart.containsItem("nightThreeHotelGood")) {
      if (cart.containsItem("nightFourHotelFair")) {
        return false;
      }
    }

    if (cart.containsItem("nightOneHotelFair")) {
      if (cart.containsItem("nightTwoHotelGood")
          || cart.containsItem("nightThreeHotelGood")
          || cart.containsItem("nightFourHotelGood")) {
        return false;
      }
    }

    if (cart.containsItem("nightTwoHotelFair")) {
      if (cart.containsItem("nightThreeHotelGood")
          || cart.containsItem("nightFourHotelGood")) {
        return false;
      }
    }

    if (cart.containsItem("nightThreeHotelFair")) {
      if (cart.containsItem("nightFourHotelGood")) {
        return false;
      }
    }

    // at most one entertainment event per night, and at most one entertainment
    // event of each type

    if (cart.containsItem("amusementDayOneTicket")) {
      if (cart.getItemByName("amusementDayOneTicket").getItemCount() > 1
          || cart.containsItem("alligatorDayOneTicket")
          || cart.containsItem("museumDayOneTicket")) {
        return false;
      }
      if (cart.getItemByName("amusementDayOneTicket").getItemCount() > 1
          || cart.containsItem("amusementDayTwoTicket")
          || cart.containsItem("amusementDayThreeTicket")
          || cart.containsItem("museumDayFourTicket")) {
        return false;
      }
    }

    if (cart.containsItem("amusementDayTwoTicket")) {
      if (cart.getItemByName("amusementDayTwoTicket").getItemCount() > 1
          || cart.containsItem("alligatorDayTwoTicket")
          || cart.containsItem("museumDayTwoTicket")) {
        return false;
      }
      if (cart.getItemByName("amusementDayTwoTicket").getItemCount() > 1
          || cart.containsItem("amusementDayOneTicket")
          || cart.containsItem("amusementDayThreeTicket")
          || cart.containsItem("museumDayFourTicket")) {
        return false;
      }
    }

    if (cart.containsItem("amusementDayThreeTicket")) {
      if (cart.getItemByName("amusementDayThreeTicket").getItemCount() > 1
          || cart.containsItem("alligatorDayThreeTicket")
          || cart.containsItem("museumDayThreeTicket")) {
        return false;
      }
      if (cart.getItemByName("amusementDayThreeTicket").getItemCount() > 1
          || cart.containsItem("amusementDayTwoTicket")
          || cart.containsItem("amusementDayOneTicket")
          || cart.containsItem("museumDayFourTicket")) {
        return false;
      }
    }

    if (cart.containsItem("amusementDayFourTicket")) {
      if (cart.getItemByName("amusementDayFourTicket").getItemCount() > 1
          || cart.containsItem("alligatorDayFourTicket")
          || cart.containsItem("museumDayFourTicket")) {
        return false;
      }
      if (cart.getItemByName("amusementDayFourTicket").getItemCount() > 1
          || cart.containsItem("amusementDayTwoTicket")
          || cart.containsItem("amusementDayThreeTicket")
          || cart.containsItem("museumDayOneTicket")) {
        return false;
      }
    }

    if (cart.containsItem("alligatorDayOneTicket")) {
      if (cart.getItemByName("alligatorDayOneTicket").getItemCount() > 1
          || cart.containsItem("amusementDayOneTicket")
          || cart.containsItem("museumDayOneTicket")) {
        return false;
      }
      if (cart.getItemByName("alligatorDayOneTicket").getItemCount() > 1
          || cart.containsItem("alligatorDayTwoTicket")
          || cart.containsItem("alligatorDayThreeTicket")
          || cart.containsItem("alligatorDayFourTicket")) {
        return false;
      }
    }

    if (cart.containsItem("alligatorDayTwoTicket")) {
      if (cart.getItemByName("alligatorDayTwoTicket").getItemCount() > 1
          || cart.containsItem("amusementDayTwoTicket")
          || cart.containsItem("museumDayTwoTicket")) {
        return false;
      }
      if (cart.getItemByName("alligatorDayTwoTicket").getItemCount() > 1
          || cart.containsItem("alligatorDayOneTicket")
          || cart.containsItem("alligatorDayThreeTicket")
          || cart.containsItem("alligatorDayFourTicket")) {
        return false;
      }
    }

    if (cart.containsItem("alligatorDayThreeTicket")) {
      if (cart.getItemByName("alligatorDayThreeTicket").getItemCount() > 1
          || cart.containsItem("amusementDayThreeTicket")
          || cart.containsItem("museumDayThreeTicket")) {
        return false;
      }
      if (cart.getItemByName("alligatorDayThreeTicket").getItemCount() > 1
          || cart.containsItem("alligatorDayTwoTicket")
          || cart.containsItem("alligatorDayOneTicket")
          || cart.containsItem("alligatorDayFourTicket")) {
        return false;
      }
    }

    if (cart.containsItem("alligatorDayFourTicket")) {
      if (cart.getItemByName("alligatorDayFourTicket").getItemCount() > 1
          || cart.containsItem("amusementDayFourTicket")
          || cart.containsItem("museumDayFourTicket")) {
        return false;
      }
      if (cart.getItemByName("alligatorDayFourTicket").getItemCount() > 1
          || cart.containsItem("alligatorDayTwoTicket")
          || cart.containsItem("alligatorDayThreeTicket")
          || cart.containsItem("alligatorDayOneTicket")) {
        return false;
      }
    }

    if (cart.containsItem("museumDayOneTicket")) {
      if (cart.getItemByName("museumDayOneTicket").getItemCount() > 1
          || cart.containsItem("amusementDayOneTicket")
          || cart.containsItem("alligatorDayOneTicket")) {
        return false;
      }
      if (cart.getItemByName("museumDayOneTicket").getItemCount() > 1
          || cart.containsItem("museumDayTwoTicket")
          || cart.containsItem("museumDayThreeTicket")
          || cart.containsItem("museumDayFourTicket")) {
        return false;
      }
    }

    if (cart.containsItem("museumDayTwoTicket")) {
      if (cart.getItemByName("museumDayTwoTicket").getItemCount() > 1
          || cart.containsItem("amusementDayTwoTicket")
          || cart.containsItem("alligatorDayTwoTicket")) {
        return false;
      }
      if (cart.getItemByName("museumDayTwoTicket").getItemCount() > 1
          || cart.containsItem("museumDayOneTicket")
          || cart.containsItem("museumDayThreeTicket")
          || cart.containsItem("museumDayFourTicket")) {
        return false;
      }
    }

    if (cart.containsItem("museumDayThreeTicket")) {
      if (cart.getItemByName("museumDayThreeTicket").getItemCount() > 1
          || cart.containsItem("amusementDayThreeTicket")
          || cart.containsItem("alligatorDayThreeTicket")) {
        return false;
      }
      if (cart.getItemByName("museumDayThreeTicket").getItemCount() > 1
          || cart.containsItem("museumDayTwoTicket")
          || cart.containsItem("museumDayOneTicket")
          || cart.containsItem("museumDayFourTicket")) {
        return false;
      }
    }

    if (cart.containsItem("museumDayFourTicket")) {
      if (cart.getItemByName("museumDayFourTicket").getItemCount() > 1
          || cart.containsItem("amusementDayFourTicket")
          || cart.containsItem("alligatorDayFourTicket")) {
        return false;
      }
      if (cart.getItemByName("museumDayFourTicket").getItemCount() > 1
          || cart.containsItem("museumDayTwoTicket")
          || cart.containsItem("museumDayThreeTicket")
          || cart.containsItem("museumDayOneTicket")) {
        return false;
      }
    }

    // at least one arrival and one departure
    
    if (!(cart.containsItem("dayOneArrivalTicket")
        || cart.containsItem("dayTwoArrivalTicket")
        || cart.containsItem("dayThreeArrivalTicket")
        || cart.containsItem("dayFourArrivalTicket"))
        && (!(cart.containsItem("dayTwoDepartureTicket")
            || cart.containsItem("dayThreeDepartureTicket")
            || cart.containsItem("dayFourDepartureTicket")
            || cart.containsItem("dayFiveDepartureTicket")))) {
      return false;
    }
    
    return true;

  }
}
