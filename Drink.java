/**
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
     *
     * @return Size enum
     */
    public Size getSize() {
        return size;
    }

    /**
     *
     * @return Temperature enum
     */
    public Temperature getTemp() {
        return temp;
    }

    /**
     *
     * @return IceLevel enum
     */
    public IceLevel getIce() {
        return ice;
    }

    /*
    ---------------> Setters <---------------
    */

    /**
     * @param size an instance of Size enum [Size]
     * @see Size
     */
    public void setTemp(Size size) {
        this.size = size;
    }

    /**
     * @param temp an instance of Temperature enum [Temperature]
     * @see Temperature
     */
    public void setTemp(Temperature temp) {
        this.temp = temp;
    }

    /**
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