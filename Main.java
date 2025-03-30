import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.awt.Frame;
public class Main {
	static ArrayList<Customer> customers = new ArrayList<>();
	public static void main(String[] args) {
		//when first run it will not be functional as there are no customers,
		//once a customer is put in customerData.txt in the res folder, it will save it
		//to manually create a customer through code just create a string array with the
		//desired information and place the new customer line after read customers then run.
		//delete the line of code for next time as it will be automatically saved.
		readCustomers(customers);
		Frame frame = new Frame();
		
		// TODO owen, move to terminate button.
		//writeCustomers(customers); - implemented when closing frame
  }
  //fills customer arraylist with values in customerData.txt
  public static void readCustomers(ArrayList<Customer> c) {
	   File file = new File("res/customerData.txt");
	   FileReader fr = null;
	   BufferedReader br = null;
	   try {
		   fr = new FileReader(file);
		   br = new BufferedReader(fr);
		   String input = br.readLine();
		   while(input!=null) {
			   //c.add(new Customer(input.split(",")));
			   input=br.readLine();
		   }
		   br.close();
	   }
	   catch (IOException e) {
		// TODO Auto-generated catch block
		   e.printStackTrace();
	   }
  }
  //writes data in customer arraylist to customersData.txt
  public static void writeCustomers(ArrayList<Customer> c) {
	   File file = new File("res/customerData.txt");
	   FileWriter fw = null;
	   BufferedWriter bw = null;
	   try {
		   fw = new FileWriter(file);
		   bw = new BufferedWriter(fw);
		   for(int i = 0; i < c.size(); i++) {
			   bw.write(c.get(i).customerString()+"\n");
		   }
		   bw.close();
		  
	   } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	   }
  }
}
