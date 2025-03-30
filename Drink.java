/**
 * @author Joaquin
 * @see Item
 */

enum Size {
    SMALL,
    MEDIUM
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

class Drink extends Item {
    private Size size;
    private Temperature temp;
    private IceLevel ice;


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