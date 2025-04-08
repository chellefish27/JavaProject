import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;


import java.awt.Frame;

import javax.swing.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {
	static ArrayList<Customer> customers = new ArrayList<>();
	static SandwichIO sandwichIO = new SandwichIO();
	public static void main(String[] args) {
		//when first run it will not be functional as there are no customers,
		//once a customer is put in customerData.txt in the res folder, it will save it
		//to manually create a customer through code just create a string array with the
		//desired information and place the new customer line after read customers then run.
		//delete the line of code for next time as it will be automatically saved.
		//readCustomers(customers);

		sandwichIO.run();

		LoginFrame loginFrame = new LoginFrame();



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

  //mainly made by Jay, with a few slight tweaks
  public static Customer[] sortEmails(String query, String selectedEnding) {
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for(int i = 0; i < customers.size(); i++) {
			//if customer email string greater than or equal to query length
			if(customers.get(i).getEmail().toLowerCase().startsWith(query.toLowerCase())) {
				if (selectedEnding.equalsIgnoreCase("other") || customers.get(i).getEmail().toLowerCase().endsWith(selectedEnding)) ids.add(i);
			}
		}
		Customer returnArray[] = new Customer[ids.size()];
		for(int i = 0; i < ids.size(); i++) {
			returnArray[i] = customers.get(ids.get(i)); 
		}
		boolean tmp = false;
		int dynamicLength = returnArray.length-1;
		while(!tmp) {
			tmp = true;
			for(int i = 0; i<dynamicLength;i++) {
				if(returnArray[i].getEmail().compareTo(returnArray[i+1].getEmail())>0) {
					Customer tmpCustomer = returnArray[i];
					returnArray[i] = returnArray[i+1];
					returnArray[i+1] = tmpCustomer;
					tmp = false;
				}
			}
			dynamicLength--;
		}
		return returnArray;
	}
}
