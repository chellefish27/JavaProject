import java.util.LinkedList;

public class SandwichPlus extends Sandwich {

    public SandwichPlus(String type, LinkedList<String> toppings) {
        super(type, (byte) 15, toppings, true, true);

    }
}
