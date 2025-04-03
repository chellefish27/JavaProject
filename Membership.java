import java.util.LinkedList;

public class Membership {
    private int points;

    /*
    ---------------> Methods <---------------
    */

    public void updatePoints(Order order) {
        points += (3 * order.getSandwiches().size());
        points += (1 * order.getDrinks().size());
        if (order.getPaidWithPoints()) {
            points -= order.getTotal();
        }

    }

    /*
    ---------------> Getters <---------------
    */

    /**
     *
     * @return int
     */
    public int getPoints() {
        return points;
    }
}
