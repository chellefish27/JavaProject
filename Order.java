class Order {
  private Sandwich sandwich;
  private boolean promotion;
  private int total;
  private String paymentMethod;
  private String confirmationCode;
  private long storeID;

  /**
   * @param storeID          id of the store where the sandwich was ordered
   * @param sandwich         sandwich that was ordered
   * @param promotion        whether the store had a promotion at the time of the
   *                         ordered
   * @param total            the total price of the order
   * @param paymentMethod    the payment method used to complete the order (debit,
   *                         credit, bitcoin, etc.)
   * @param confirmationCode the code from the financial institution (3 Letters, 4
   *                         Numbers. E.g. RHN3421)
   */

  public Order(Sandwich sandwich, boolean promotion, int total, String paymentMethod, String confirmationCode) {
    this.sandwich = sandwich;
    this.promotion = promotion;
    this.total = total;
    this.paymentMethod = paymentMethod;
    this.confirmationCode = confirmationCode;

    // implement randomized IDs later
    // this.storeID
  }

}
