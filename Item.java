/**
 * @author Joaquin
 */

public abstract class Item {
    protected String type;
    protected double price;

    /*
    ---------------> Getters <---------------
   */

    /**
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * @return double
     */
    public double getPrice() {
        return price;
    }

    /*
    ---------------> Setters <---------------
    */

    /**
     * @param type the type of whichever food subclass [String]
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @param price the price of the food [double]
     */
    public void setPrice(double price) {
        this.price = price;
    }
 }