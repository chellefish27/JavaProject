/**
 * @author Joaquin
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.Strictness;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;

public class SandwichIO extends Thread {
    private Gson formattedGson = new GsonBuilder().setPrettyPrinting().create();
    private LinkedList<Customer> customers = new LinkedList<>();

    @Override
    public void run() {
        try {
            // open the file for reading
            JsonReader fr = new JsonReader(new FileReader("data.json"));
            fr.setStrictness(Strictness.LENIENT);
            // read the json into an array of Customer
            Customer[] customerArray = formattedGson.fromJson(fr, Customer[].class);

            // transfer the array to a linkedlist
            for(Customer customer : customerArray) {
                customers.addLast(customer);
                Main.customers.add(customer);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param customer [Customer]
     */
    public void addCustomer(Customer customer) {
        customers.addLast(customer);
    }

    /**
     * write all data in a customer object into the file in JSON format
     */
    public void customerWrite() {
        try {
            // add Customer object to the list
            // customers.addLast(customer);
            // open the file
            FileChannel channel = FileChannel.open(Paths.get("data.json"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);

            // allocate the number of bytes to use for the buffer
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024); // Allocate a direct buffer of 1MB

            String line = formattedGson.toJson(customers);

            // getBytes() writes the bytes directly to the file, reportedly is significantly faster for writing
            byte[] bytes = line.getBytes(); // write the object in JSON format
            // byte[] nextLine = "\n".getBytes(); // add a next line character

            buffer.clear(); // clear the buffer
            buffer.put(bytes); // write bytes of the JSON object to buffer
            buffer.flip(); // prepare to read from the buffer

            channel.write(buffer); // write data directly to the file
            channel.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return LinkedList<Customer>
     */
    public LinkedList<Customer> getCustomerList() {
        return customers;
    }
}
