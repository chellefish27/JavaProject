import java.util.LinkedList;

public class Membership {
    private double points;

    /*
    ---------------> Methods <---------------
    */

    /**
     * subtract points in the membership of the customer if they paid using them
     * @param cost [double]
     */
    public void subtractPoints(double cost) {
        points -= cost;

    }

    /**
     * @param sandwichAmount size of sandwiches array
     * @param drinkAmount size of drinks array
     */
    public void addPoints(int sandwichAmount, int drinkAmount) {
        points += 1 * sandwichAmount;
        points += 0.5 * drinkAmount;
    }

    /*
    ---------------> Getters <---------------
    */

    /**
     *
     * @return double
     */
    public double getPoints() {
        return points;
    }
}
