/**
 * a class to hold necessary Sandwich data
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
   * adds a topping to the list of toppings
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
   * get the size of sandwich
   * @return byte
   */
  public byte getSize() {
    return size;
  }

  /**
   * get the list of toppings
   * @return LinkedList<String> a Linked List of all the toppings
   */
  public LinkedList<String> getToppings() {
    return toppings;
  }

  /**
   * if the sandwich has bacon
   * @return boolean
   */
  public boolean hasBacon() {
    return hasBacon;
  }

  /**
   * if
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
   * set the size of the
   * precondition: 2-15 in inches
   * @param size [byte]
   */
  public void setSize(byte size) {
    this.size = size;
  }

  /**
   * set the toppings list
   * mostly for debug purposes as addTopping is better
   * @param toppings is a Linked List of all the toppings on the sandwich [LinkedList<String>]
   */
  public void setToppings(LinkedList<String> toppings) {
    this.toppings = toppings;
  }

  /**
   * set if the sandwich has bacon
   * @param hasBacon [boolean]
   */
  public void setHasBacon(boolean hasBacon) {
    this.hasBacon = hasBacon;
  }

  /**
   * set if the sandwich has toasted bread
   * @param toasted [boolean]
   */
  public void setToasted(boolean toasted) {
    this.toasted = toasted;
  }

  /**
   * set a string that denotes the toy included
   * @param toy [String]
   */
  public void setToy(String toy) {
    this.toy = toy;
  }
}
