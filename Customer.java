public class Customer {
	   private String name;
	   private String email;
	   public Customer(String[] inputArr) {
	       this.name = inputArr[0];
	       this.email = inputArr[1];
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
		   return(""+this.name+","+this.email);
	   }
	}
