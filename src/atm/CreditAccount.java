
package atm;
import java.util.ArrayList;

public class CreditAccount extends Account {
	

	
	private final ArrayList<AccountTransaction> transactions;
	private final double overDraftLimit;
	private final String name;

	public CreditAccount(String name, Customer holder, Bank theBank,double overDraftLimit) {
		super(name, holder, theBank);

		
		this.name = name;
		
		 this.overDraftLimit = overDraftLimit;	
		
		this.transactions = new ArrayList<>();
		
		
	}

    @Override
	public double getBalance() { 
		
		int balance = 0;
		balance = (int) overDraftLimit;
		return balance;
		
	}
	
	
	
	

}
