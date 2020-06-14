
package atm;
import java.util.Date;

public class AccountTransaction {
	
	private double amount;
	private Date timestamp; 
	private String txReference; 
	private Account inAccount;
	
	public AccountTransaction(double amount, Account inAccount) { 
		
		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
		this.txReference = "";
		
	}
	
	public AccountTransaction(double amount, String txReference, Account inAccount) { 
		
		
		this(amount, inAccount);
		
		this.txReference = txReference;
		
	}
	
	public double getAmount() { 
		return this.amount;
	}
	
	public String getSummaryLine() { 
		
		if (this.amount >= 0) {
			return String.format("%s, € %.02f : %s", 
					this.timestamp.toString(), this.amount, this.txReference);
		} else {
			return String.format("%s, € (%.02f) : %s", 
					this.timestamp.toString(), -this.amount, this.txReference);
		}
	}

}