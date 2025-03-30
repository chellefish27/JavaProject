/**
 * @author Joaquin
 * @see Item
 */

import java.util.LinkedList;
import java.util.List;

public class Sandwich extends Item {
  protected byte size;
  protected LinkedList<String> toppings = new LinkedList<>();
  protected boolean hasBacon;
  protected boolean toasted;
  protected String toy = "No toy ordered";

  /*
    ---------------> Methods <---------------
   */

  /**
   *
   * @param topping topping to be added to list [String]
   * @return offerLast() returns boolean if successful in adding or not
   */
  public boolean addTopping(String topping) {
    // this may be changed depending on what makes most sense, both offerFirst and offerLast are O(1). It can also be changed to addLast/addFirst instead
    return toppings.offerLast(topping);
  }

  /*
    ---------------> Getters <---------------
   */

  /**
   * @return byte
   */
  public byte getSize() {
    return size;
  }

  /**
   *
   * @return List<String> a List of all the toppings
   */
  public List<String> getToppings() {
    return toppings;
  }

  /**
   * @return boolean
   */
  public boolean hasBacon() {
    return hasBacon;
  }

  /**
   * @return boolean
   */

  public boolean isToasted() {
    return toasted;
  }

  /**
   * @return String
   */
  public String getToy() {
    return toy;
  }

  /*
    ---------------> Setters <---------------
   */

  /**
   * @param size size of the sandwich (2-15 in inches) [byte]
   */
  public void setSize(byte size) {
    this.size = size;
  }

  /**
   * @param toppings is a List of all the toppings on the sandwich [List<String>]
   */
  public void setToppings(List<String> toppings) {
    this.toppings = new LinkedList<>(toppings);
  }

  /**
   * @param hasBacon if the sandwich has bacon [boolean]
   */
  public void setHasBacon(boolean hasBacon) {
    this.hasBacon = hasBacon;
  }

  /**
   * @param toasted if the sandwich has toasted bread [boolean]
   */
  public void setToasted(boolean toasted) {
    this.toasted = toasted;
  }

  /**
   * @param toy a string that denotes the toy included [String]
   */
  public void setToy(String toy) {
    this.toy = toy;
  }
}
