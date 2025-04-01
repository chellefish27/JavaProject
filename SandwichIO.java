/**
 * @author Joaquin
 */

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;

public class SandwichIO extends Thread {
    private Gson gson = new Gson();
    private LinkedList<Customer> customers = new LinkedList<>();

    @Override
    public void run() {
        try {
            // open the file for reading
            BufferedReader br = new BufferedReader(new FileReader("data.json"));

            // read each line individually
            String line;
            while ((line = br.readLine()) != null) {
                customers.addLast(gson.fromJson(line, Customer.class));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * write all data in a customer object into the file in JSON format
     * @param customer [Customer]
     */
    public void customerWrite(Customer customer) {
        try {
            // open the file
            FileChannel channel = FileChannel.open(Paths.get("data.json"), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

            // allocate the number of bytes to use for the buffer
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024); // Allocate a direct buffer of 1MB

            String line = gson.toJson(customer);

            // getBytes() writes the bytes directly to the file, reportedly is significantly faster for writing
            byte[] bytes = line.getBytes(); // write the object in JSON format
            byte[] nextLine = "\n".getBytes(); // add a next line character

            buffer.clear(); // clear the buffer
            buffer.put(bytes); // write bytes of the JSON object to buffer
            buffer.put(nextLine); // write next line character to buffer
            buffer.flip(); // prepare to read from the buffer

            channel.write(buffer); // write data directly to the file
        } catch (Exception e) {
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
