/**
 * @author Joaquin
 * @see Sandwich
 */

public class SandwichPlus extends Sandwich {
    public SandwichPlus() {
        this.size = 15;
        this.hasBacon = true;
        this.toasted = true;
    }

    @Override
    public void setSize() {
        System.out.println("You shall have a 15 inch sandwich!");
    }
}
