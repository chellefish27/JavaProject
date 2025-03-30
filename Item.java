/**
 * @author Joaquin
 */
public abstract class Item {
    protected String type;
    protected short price;

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
     * @return short
     */
    public short getPrice() {
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
     * @param price the price of the food [short]
     */
    public void setPrice(short price) {
        this.price = price;
    }
 }