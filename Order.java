import jdk.javadoc.internal.doclets.toolkit.taglets.snippet.Style;

class Order {
  private LinkedList<Sandwich> sandwiches = new LinkedList<>();
  private boolean promotion;
  private int total;
  private String paymentMethod;
  private String confirmationCode;
  private long storeID;

  public Order() {
    // implement randomized IDs later
    // this.storeID
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
   * @return boolean
   */
  public boolean isPromotion() {
    return promotion;
  }

  /**
   * @return int
   */
  public int getTotal() {
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

  /*
    ---------------> Setters <---------------
   */

  /**
   * @param sandwiches set the entire sandwiches linked list [LinkedList<Sandwich>]
   */
  public boolean setSandwiches(LinkedList<Sandwich> sandwiches) {
    this.sandwiches = sandwiches;
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
  public void setTotal(int total) {
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
   */
  public void setConfirmationCode(String confirmationCode) {
    this.confirmationCode = confirmationCode;
  }

}
