import java.util.ArrayList;

public class Customer {
    private String name;
    private String email;
    private String ID;
    private ArrayList<Order> pastOrders = new ArrayList<>();
    private Membership membership;

    public Customer(String name, String email, ArrayList<Order> pastOrders) {
        this.name = name;
        this.email = email;
        this.pastOrders = pastOrders;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    @Override
    public String toString() {
        return name;
    }
    public String customerString() {
        return (name + "," + email);
    }
    public ArrayList<Order> getPastOrders() {
        return pastOrders;
    }
}
