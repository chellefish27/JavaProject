import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Joaquin
 */

public class Order {
  private LinkedList<Sandwich> sandwiches = new LinkedList<>();
  private LinkedList<Drink> drinks = new LinkedList<>();
  private boolean promotion;
  private double total;
  private String paymentMethod;
  private String confirmationCode;
  private long storeID;
  private final LocalDate dateOrdered;

  public Order() {
    // implement randomized IDs later
    // this.storeID

    dateOrdered = LocalDate.now();
  }

  /*
    ---------------> Methods <---------------
   */

  /**
   * @param sandwich add a sandwich to the sandwiches linked list [Sandwich]
   * @return offerLast() returns boolean if successful in adding or not
   */
  public boolean addSandwich(Sandwich sandwich) {
    return sandwiches.offerLast(sandwich);
  }

  /**
   * @param drink add a drink to the drinks linked list [Drink]
   * @return offerLast() returns boolean if successful in adding or not
   */
  public boolean addDrink(Drink drink) {
    return drinks.offerLast(drink);
  }

  /**
   * this method uses an iterator because a 'for' loop + .get() is O(n^2) for a linked list
   * @return returns the total after calculating it
   */
  public double calcTotal() {
    Iterator<Sandwich> iterator = sandwiches.iterator();
    while (iterator.hasNext()) {
      Sandwich sandwich = iterator.next();
      total += sandwich.getPrice();
    }

    Iterator<Drink> iterator2 = drinks.iterator();
    while (iterator2.hasNext()) {
      Drink drink = iterator2.next();
      total += drink.getPrice();
    }

    return total;
  }

  /*
    ---------------> Getters <---------------
   */

  /**
   * @return LinkedList<Sandwich>
   */
  public LinkedList<Sandwich> getSandwiches() {
    return sandwiches;
  }

  /**
   * @return LinkedList<Drink>
   */
  public LinkedList<Drink> getDrinks() {
    return drinks;
  }

  /**
   * @return boolean
   */
  public boolean isPromotion() {
    return promotion;
  }

  /**
   * @return int
   */
  public double getTotal() {
    return total;
  }

  /**
   * @return String
   */
  public String getPaymentMethod() {
    return paymentMethod;
  }

  /**
   * @return String
   */
  public String getConfirmationCode() {
    return confirmationCode;
  }

  /**
   * @return long
   */
  public long getStoreID() {
    return storeID;
  }

  /**
   * @return LocalDateTime from java.time.LocalDateTime
   */
  public LocalDate getDate() {
    return dateOrdered;
  }

  /*
    ---------------> Setters <---------------
   */

  /**
   * @param sandwiches set the entire sandwiches linked list [LinkedList<Sandwich>]
   */
  public void setSandwiches(LinkedList<Sandwich> sandwiches) {
    this.sandwiches = sandwiches;
  }

  /**
   * @param drinks set the entire drinks linked list [LinkedList<Drink>]
   */
  public void setDrinks(LinkedList<Drink> drinks) {
    this.drinks = drinks;
  }

  /**
   * @param promotion whether the store had a promotion at the time of the ordered [boolean]
   */
  public void setPromotion(boolean promotion) {
    this.promotion = promotion;
  }

  /**
   * @param total the total price of the order [int]
   */
  public void setTotal(double total) {
    this.total = total;
  }

  /**
   * @param paymentMethod which payment method was used [String]
   */
  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  /**
   * @param confirmationCode the code from the financial institution (3 Letters, 4 Numbers. E.g. RHN3421) [String]
   * @return boolean if the code is valid or not
   */
  public boolean setConfirmationCode(String confirmationCode) {
    for(int i = 0; i < 3; ++i) {
      // checks if the first 3 characters are a letter
      if (!Character.isLetter(confirmationCode.charAt(i))) {
        System.err.println("Not a valid confirmation code");
        return false;
      }
    }
    for(int i = 3; i < 7; ++i) {
      // checks if the last 4 characters are a number
      if (!Character.isDigit(confirmationCode.charAt(i))) {
        System.err.println("Not a valid confirmation code");
        return false;
      }
    }
    this.confirmationCode = confirmationCode;
    return true;
  }

  /*
    ---------------> Overrides <---------------
   */

  /**
   * @return String with number of sandwiches and drinks
   */
  public String toString() {
    if (drinks.isEmpty())
      return String.format("%d Sandwiches ", sandwiches.size());
    else if (sandwiches.isEmpty())
      return String.format("%d Drinks", drinks.size());

    return String.format("%d Sandwiches\n %d Drinks", sandwiches.size(), drinks.size());
  }
}
