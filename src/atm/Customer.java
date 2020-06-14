
package atm;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Customer {
	
	private String firstName;
	private String lastName;
	private final String uuid; 
	private byte accountPinHash[];
	private final ArrayList<Account> custsAccounts; 
	
	public Customer (String firstName, String lastName, String pin, Bank theBank) { 
		
	
		this.firstName = firstName;
		this.lastName = lastName;
	
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.accountPinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error, caught exeption : " + e.getMessage());
			System.exit(1);
		}
		
		this.uuid = theBank.getNewUserUUID();
		
		
		this.custsAccounts = new ArrayList<>();
		
		
		System.out.printf("New user %s, %s with ID %s created.\n", 
				lastName, firstName, this.uuid);
		
	}

	public String getUUID() {
		return this.uuid;
	}
	
	public void addAccount(Account anAcct) {
		this.custsAccounts.add(anAcct);
	}
	
	public int numAccounts() {
		return this.custsAccounts.size();
	}
	
	public double getAcctBalance(int acctIdx) {
		return this.custsAccounts.get(acctIdx).getBalance(); 
	}
	
	public double getoDBalance(int acctIdx) {
		return this.custsAccounts.get(acctIdx).getBalance(); 
	}
	
	public String getAcctUUID(int acctIdx) { 
		return this.custsAccounts.get(acctIdx).getUUID();
	}
	
	public void displayAcctHistory(int acctIdx) {
		this.custsAccounts.get(acctIdx).printTransHistory(); 
	}
	public void printAcctHistory(int acctIdx, PrintWriter writer) {
		this.custsAccounts.get(acctIdx).filePrintTransHistory(writer); 
	}
	
	public void addAcctTransaction(int acctIdx, double amount, String memo) {
		this.custsAccounts.get(acctIdx).addTransaction(amount, memo);
	}
	
	public boolean validatePin(String aPin) {
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()), 
					this.accountPinHash);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error, caught exeption : " + e.getMessage()); 
			System.exit(1);
		}
		
		return false; 
	}
	
	public void printAccountsSummary() {
		
		System.out.printf("|\t\t\t\t\t\t\t|\n|%s's accounts summary\t\t\t\t|\n", this.firstName);
		System.out.println("|_______________________________________________________|");
		for (int a = 0; a < this.custsAccounts.size(); a++) {
			System.out.printf("%d) %s\n", a+1, 
					this.custsAccounts.get(a).getSummaryLine());
		}
		System.out.println();
		
	}
	public void getODRate (){
		for (int a = 0; a < this.custsAccounts.size(); a++) {
			System.out.printf("%d) %s\n", a+1, 
					this.custsAccounts.get(a).getBalance());
		}
		
		
	}
	
}