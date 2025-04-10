import java.util.LinkedList;

/**
 * A class to hold membership information for each customer
 * @author Joaquin
 */
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
     * add the points based on the amount of sandwiches and drinks in the order
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
     * get the current points
     * @return double
     */
    public double getPoints() {
        return points;
    }
}
