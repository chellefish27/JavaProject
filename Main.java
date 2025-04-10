import java.util.ArrayList;

/**
 * @author Owen Reid
 * @author Joaquin Tagle
 */

public class Main {
	static ArrayList<Customer> customers = new ArrayList<>();
	static SandwichIO sandwichIO = new SandwichIO();
	public static void main(String[] args) {


		sandwichIO.run(); //loads data

		LoginFrame loginFrame = new LoginFrame(); //make silly little login frame



		// TODO owen, move to terminate button.
		//writeCustomers(customers); - implemented when closing frame
  }
  //fills customer arraylist with values in customerData.txt

  //mainly made by Jay, with a few slight tweaks
  public static Customer[] sortEmails(String query, String selectedEnding) {
		ArrayList<Integer> ids = new ArrayList<>();
		for(int i = 0; i < customers.size(); i++) {
			//check to see if the customer email starts with given query (text from email search)
			if(customers.get(i).getEmail().toLowerCase().startsWith(query.toLowerCase())) {
				//check email endings (other = any, else check if it has ending of selected value), if so add to ids
				if (selectedEnding.equalsIgnoreCase("other") || customers.get(i).getEmail().toLowerCase().endsWith(selectedEnding)) ids.add(i);
			}
		}
		Customer[] returnArray = new Customer[ids.size()];
		//creates customer array to return
		for(int i = 0; i < ids.size(); i++) {
			returnArray[i] = customers.get(ids.get(i)); 
		}
		boolean tmp = false;
		int dynamicLength = returnArray.length-1;
		while(!tmp) {
			tmp = true;
			for(int i = 0; i<dynamicLength;i++) {
				//returns customers in lexicographical order
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
