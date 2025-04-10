/**
 * A class for the Sandwich+
 * @author Joaquin
 * @see Sandwich
 */

public class SandwichPlus extends Sandwich {
    /**
     * sets the default values of the instance variables
     * size is always 15 and unchangeable
     */
    public SandwichPlus() {
        this.size = 15;
        this.hasBacon = true;
        this.toasted = true;
    }

    /**
     * overrides the setSize in Sandwich so nobody erroneously uses it when it is not allowed
     * @param size [byte]
     */
    @Override
    public void setSize(byte size) {
        System.out.println("You shall have a 15 inch sandwich!");
    }
}
