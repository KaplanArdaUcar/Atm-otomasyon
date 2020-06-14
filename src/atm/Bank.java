
package atm;
import java.util.ArrayList;
import java.util.Random;

public class Bank {
	
	
	private final String name;
	private final ArrayList<Customer> usersList;
	private final ArrayList<Account> accountsList; 
	
	public Bank(String name) {
		
		this.name = name;
		
		usersList = new ArrayList<>();
		accountsList = new ArrayList<>();
		
	}
	public String getNewUserUUID() { 
		
		String uuid;
		Random rng = new Random();
		int len = 6;
		boolean nonUnique;
		
		
		do {
			
			
			uuid = "";
			for (int c = 0; c < len; c++) {
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			
			
			nonUnique = false;
			for (Customer u : this.usersList) {
				if (uuid.compareTo(u.getUUID()) == 0) {  
					nonUnique = true;
					break;
				}
			}
			
		} while (nonUnique); 
		return uuid;
	}
	
	public String getNewAccountUUID() { 
		
		String uuid;
		Random rng = new Random();
		int len = 10;
		boolean nonUnique = false;
		
		do {
			uuid = "";
			for (int c = 0; c < len; c++) {
				uuid += ((Integer)rng.nextInt(10)).toString(); 
			}
			
			for (Account a : this.accountsList) {
				if (uuid.compareTo(a.getUUID()) == 0) { 
					nonUnique = true;
					break;
				}
			}
			
		} while (nonUnique);
		
		return uuid;
				
	}

	public Customer addUser(String firstName, String lastName, String pin) { 
		
		
		Customer newUser = new Customer(firstName, lastName, pin, this);
		this.usersList.add(newUser);
		
	
		Account newAccount = new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.accountsList.add(newAccount);
		
		return newUser;
		
	}
	
	public void addAccount(Account newAccount) { 
		this.accountsList.add(newAccount);
	}

	public Customer userLogin(String userID, String pin) {	
		
		
		for (Customer u : this.usersList) { 
			
			
			if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
				return u;
			}
		}
		
		return null; 
		
	}
	public String getName() {
		return this.name;
	}

}