import java.util.LinkedList;

class Sandwich {
  protected String type;
  protected byte size;
  protected LinkedList<String> toppings = new LinkedList<>();
  protected boolean hasBacon;
  protected boolean toasted;
  protected String toy;

  /**
   * @param type     the type of sandwich
   * @param size     size of the sandwich (2-15 in inches)
   * @param toppings is a Linked List of all the toppings on the sandwich
   */

  public Sandwich(String type, byte size, LinkedList<String> toppings, boolean hasBacon, boolean toasted) {
    this.type = type;
    this.size = size;
    this.toppings = toppings;
    this.hasBacon = hasBacon;
    this.toasted = toasted;
  }

  public void setToy(String toy) {
    this.toy = toy;
  }
}
