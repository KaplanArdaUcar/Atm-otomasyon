
package atm;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Account {
	
	private static String name;
	private final String uuid;
	
	private final Customer accHolder;
	private final ArrayList<AccountTransaction> transactions; 
	
	
	public Account(String name, Customer holder, Bank theBank) { 
		
		
		Account.name = name;
		this.accHolder = holder;
		
		
		this.uuid = theBank.getNewAccountUUID();
		
		
		this.transactions = new ArrayList<>();
		
	}
	
	
	public String getUUID() {
		return this.uuid;
	}
	
	public void addTransaction(double amount) {
		AccountTransaction newTrans = new AccountTransaction(amount, this); 
		this.transactions.add(newTrans);
		
	}
	public void addTransaction(double amount, String note) { 
		
		
		AccountTransaction newTrans = new AccountTransaction(amount, note, this); 
		this.transactions.add(newTrans);
		
	}
	
	public double getBalance() {
		
		double balance = 0;
                balance = this.transactions.stream().map((t) -> t.getAmount()).reduce(balance, (accumulator, _item) -> accumulator + _item);
		return balance;
		
	}
	public static String getName(){
		return name;
	}
	
	public String getSummaryLine() {
		
		double balance = this.getBalance();
		
		
		if (balance >= 0) {
			return String.format("%s : € %.02f : %s", this.uuid, balance,  
					Account.name);
		} else {
			return String.format("%s : € (%.02f) : %s", this.uuid, balance, 
					Account.name);
		}
		
	}
	
	public void printTransHistory() { 
		
		System.out.printf("\nTransaction history for account %s\n", this.uuid);
		for (int t = this.transactions.size()-1; t >= 0; t--) {
			System.out.println(this.transactions.get(t).getSummaryLine());
		}
		System.out.println();
		
	}
	public void filePrintTransHistory(PrintWriter writer) { 
	
		writer.printf("\nTransaction history for account %s\n", this.uuid);
		for (int t = this.transactions.size()-1; t >= 0; t--) {
			writer.println(this.transactions.get(t).getSummaryLine());
		}
		System.out.println();
		
	}

}
