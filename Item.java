/**
 * An abstract class to group together Sandwich and Drink as Menu Items
 * @author Joaquin
 */

public abstract class Item {
    protected String type;
    protected double price;

    /*
    ---------------> Getters <---------------
   */

    /**
     * get the type of Item
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * get the price of Item
     * @return double
     */
    public double getPrice() {
        return price;
    }

    /*
    ---------------> Setters <---------------
    */

    /**
     * set the type of Item
     * @param type the type of whichever food subclass [String]
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * set the price of Item
     * @param price the price of the food [double]
     */
    public void setPrice(double price) {
        this.price = price;
    }
 }