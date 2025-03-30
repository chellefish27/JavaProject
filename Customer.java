import java.util.ArrayList;

public class Customer {
    private String name;
    private String email;
    private ArrayList<String> pastOrders = new ArrayList<>();

    public Customer(String name, String email, ArrayList<String> pastOrders) {
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
    public ArrayList<String> getPastOrders() {
        return pastOrders;
    }
}
