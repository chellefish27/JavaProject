/**
 * A class to create a Drink object with all the needed information
 * @author Joaquin
 * @see Item
 */


public class Drink extends Item {
    enum Size {
        SMALL,
        MEDIUM,
        LARGE;
    }

    enum Temperature {
        HOT,
        WARM,
        COLD;
    }

    enum IceLevel {
        NONE,
        LOW,
        MEDIUM,
        HIGH;
    }

    private Size size;
    private Temperature temp;
    private IceLevel ice;

    public Drink() {}


    // for debugging purposes only
    public Drink(Size size, Temperature temp, IceLevel ice) {
        this.size = size;
        this.temp = temp;
        this.ice = ice;
    }

    /*
    ---------------> Getters <---------------
   */

    /**
     * get the size of the drink
     * @return Size enum
     */
    public Size getSize() {
        return size;
    }

    /**
     * get the temperature of the drink
     * @return Temperature enum
     */
    public Temperature getTemp() {
        return temp;
    }

    /**
     * get the level of ice in the drink
     * @return IceLevel enum
     */
    public IceLevel getIce() {
        return ice;
    }

    /*
    ---------------> Setters <---------------
    */

    /**
     * set the size of the drink
     * @param size an instance of Size enum [Size]
     * @see Size
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * set the temperature
     * @param temp an instance of Temperature enum [Temperature]
     * @see Temperature
     */
    public void setTemp(Temperature temp) {
        this.temp = temp;
    }

    /**
     * set the amount of ice
     * precondition: the drink must be cold
     * @param ice an instance of IceLevel enum [IceLevel]
     * @see IceLevel
     */
    public void setIce(IceLevel ice) {
        if (temp != Temperature.COLD)
            System.err.println("Drink is not cold, ice can not be added");
        else
            this.ice = ice;
    }
}