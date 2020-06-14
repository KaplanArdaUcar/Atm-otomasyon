
package atm;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

public class ATM implements Overdraft{
	public static void main(String[] args) throws FileNotFoundException {
		
		
		Scanner sc = new Scanner(System.in);
		Bank theBank = new Bank("Kaplan Bank");	
		Customer defaultUser = theBank.addUser("KaplanArda","Uçar","1793");
		
		Account newAccount = new Account("Current Account", defaultUser, theBank); 
		defaultUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		Account newCreditAccount = new CreditAccount("Credit Account", defaultUser, theBank, 1000);
		defaultUser.addAccount(newCreditAccount);
		theBank.addAccount(newCreditAccount);
		
		
		Customer overdraftCustomer = theBank.addUser("Naomi", "Hurley", "1234");
		
		Account newCreditAccount2 = new CreditAccount("Credit Account", overdraftCustomer, theBank, 1000); 
		defaultUser.addAccount(newCreditAccount2);
		theBank.addAccount(newCreditAccount2);
		
		Customer user;
		
		
		while (true) { 
			
			
			user = ATM.mainMenuPrompt(theBank, sc);
			
			
			ATM.customerMenu(user, sc);
			
		}

	}
	
	public static Customer mainMenuPrompt(Bank theBank, Scanner sc) { 
		
		String userID;
		String pin;
		Customer authUser;
		
	
		do {
			
			
			System.out.println(" _____________________Acme ATM Company___________________");			
			System.out.print("|\t\t\t\t\t\t\t| \n");			
			System.out.printf("|\tWelcome to %s\t\t|\n", theBank.getName());
			System.out.print("|\t\t\t\t\t\t\t| \n");
			System.out.println("|_______________________________________________________|");
			System.out.println("|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|");
			System.out.println("|_________________________Log In________________________|");
			System.out.print("|\tPlease log into your account to continue \t|\n");
			System.out.print("| Enter user ID: ");
			userID = sc.nextLine();
			System.out.print("|Enter pin: ");
			pin = sc.nextLine();
			
			
			authUser = theBank.userLogin(userID, pin); 
			if (authUser == null) {
				System.out.println("Incorrect user ID/pin combination. " + 
						"Please try again");
			}
			System.out.println("|_______________________________________________________|");
			System.out.println("|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|");
			System.out.println("|__________________Log In Success_______________________|");
			System.out.println("|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|");
			
		} while(authUser == null); 	
		
		
		return authUser;
		
	}
	
	public static void customerMenu(Customer theUser, Scanner sc) throws FileNotFoundException { 
		
		
		System.out.println("|_______________________________________________________|");
		theUser.printAccountsSummary(); 
		
		int choice;
		
		do {
			
			System.out.println("|What would you like to do?");
			System.out.println("|  1) Deposit ");
			System.out.println("|  2) Withdraw");
			System.out.println("|  3) Transfer");
			System.out.println("|  4) Show account transaction history");
			System.out.println("|  5) Print account transaction history");
			System.out.println("|  6) Overdraft options");
			System.out.println("|  7) Logout of/Quit the Application");
			System.out.println();
			System.out.print("|Enter choice: ");
			choice = sc.nextInt();
			
			if (choice < 1 || choice > 7) {
				System.out.println("Invalid choice. Please choose 1-7.");
			}
			
		} while (choice < 1 || choice > 7);
		
		
		switch (choice) { 
		
		case 1:
			ATM.depositFunds(theUser, sc);
			break;
		case 2:
			ATM.withdrawFunds(theUser, sc); 
			break;
		case 3:
			ATM.transferFunds(theUser, sc); 
			break;
		case 4:
			ATM.showTransHistory(theUser, sc); 
			break;
		case 5:
			ATM.PrintTransHistory(theUser, sc); 
			break;
		case 6:
			System.out.println("Please contact your branch to discuss available overdraft options \n");
			ATM.overdraftMenu(theUser,sc);
			break;
		
		case 7:
			sc.nextLine(); 
			break;
		}
		
		
		if (choice != 7) {
			ATM.customerMenu(theUser, sc); 
		}
		
	}
	
	public static void overdraftMenu(Customer theUser, Scanner sc) throws FileNotFoundException{
		
		theUser.printAccountsSummary();
		
		
		int choice;
		int odAcct;
		
		
		do {
			
			System.out.println("What would you like to do?");
			System.out.println("  1) Check Overdraft Balance");
			System.out.println("  2) <Error payment features in progress>");
			System.out.println("  3) Go back");
			System.out.println();
			System.out.print("Enter choice: ");
			choice = sc.nextInt();
			
			if (choice < 1 || choice > 3) {
				System.out.println("Invalid choice. Please choose 1-5.");
			}
			
		} while (choice < 1 || choice > 3);
		
		
		switch (choice) {
		
		case 1:
			System.out.println("Overdraft account balance ");
			
			do {
				System.out.printf("Enter the number (1-%d) of the account to " + 
						"do overdraft functions with: ", theUser.numAccounts());
				odAcct = sc.nextInt()-1;
				if (odAcct < 0 || odAcct >= theUser.numAccounts()) {
					System.out.println("Invalid account. Please try again.");
				}
				else if (Account.getName().equals("Credit Account") == false){ 
					System.out.println("Account is not eligble for overdraft, please contact your bank to remedy this.");
					ATM.customerMenu(theUser, sc);
				}
				else{
					System.out.println("Overdraft balance is: ");
					theUser.getODRate();
					
				}
			} while (odAcct < 0 || odAcct >= theUser.numAccounts());
			
			break;
		case 2:
			System.out.println("Invalid choice. ");
			break;
			
		case 3:
			ATM.customerMenu(theUser, sc);
			break;
		}
		
		
		if (choice != 3) {
			ATM.customerMenu(theUser, sc);
		}
		
		
	}
	

	public Object Start() { 
		payOverDraft();
		return null;
	}
	public static void transferFunds(Customer theUser, Scanner sc) {
		
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		
		if (theUser.numAccounts()!=1){		
		do {
			System.out.printf("Enter the number (1-%d) of the account to " + 
					"transfer from: ", theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
		}
		else{
			fromAcct=1;
		}
		
		acctBal = theUser.getAcctBalance(fromAcct);
		
		
		do {
			System.out.printf("Enter the number (1-%d) of the account to " + 
					"transfer to: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (toAcct < 0 || toAcct >= theUser.numAccounts());
		
		
		do {
			System.out.printf("Enter the amount to transfer (max € %.02f): $", 
					acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} else if (amount > acctBal) {
				System.out.printf("Amount must not be greater than balance " +
						"of $.02f.\n", acctBal);
			}
		} while (amount < 0 || amount > acctBal);
		
		
		theUser.addAcctTransaction(fromAcct, -1*amount, String.format( 
				"Transfer to account %s", theUser.getAcctUUID(toAcct)));
		theUser.addAcctTransaction(toAcct, amount, String.format(
				"Transfer from account %s", theUser.getAcctUUID(fromAcct))); 
		
	}
	
	public static void withdrawFunds(Customer theUser, Scanner sc) { 
		
		int withdrawAcct;
		double amount;
		double acctBal;
		String txReference;
		
		if (theUser.numAccounts()!=1){
		do {
			System.out.printf("Enter the number (1-%d) of the account to " + 
					"withdraw from: ", theUser.numAccounts());
			withdrawAcct = sc.nextInt()-1;
			if (withdrawAcct < 0 || withdrawAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (withdrawAcct < 0 || withdrawAcct >= theUser.numAccounts());
		}
		else{
			withdrawAcct=1;
		}
		acctBal = theUser.getAcctBalance(withdrawAcct);
		
		
		do {
			System.out.printf("Enter the amount to withdraw (max € %.02f): € ", 
					acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} else if (amount > acctBal) {
				System.out.printf("Amount must not be greater than balance " +
						"of $%.02f.\n", acctBal);
			}
		} while (amount < 0 || amount > acctBal);
		
		
		sc.nextLine();
		
		
		System.out.print("Enter a memo: ");
		txReference = sc.nextLine();
		
		
		theUser.addAcctTransaction(withdrawAcct, -1*amount, txReference);
		
	}
	
	public static void depositFunds(Customer theUser, Scanner sc) { 
		
		int toAcct;
		double amount;
		String txReference;
		
		if (theUser.numAccounts()!=1){
		do {
			System.out.printf("Enter the number (1-%d) of the account to " + 
					"deposit to: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (toAcct < 0 || toAcct >= theUser.numAccounts());
		}
		else{
			toAcct=1;
		}
		
		
		do {
			System.out.printf("Enter the amount to deposit: € ");
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} 
		} while (amount < 0);
		
		
		sc.nextLine();
		
		
		System.out.print("Enter a Reference: ");
		txReference = sc.nextLine();
		
		
		theUser.addAcctTransaction(toAcct, amount, txReference);
		
	}
	
	public static void showTransHistory(Customer theUser, Scanner sc) {
		
		int theAcct=0;
		
		
		if (theUser.numAccounts()!=1){
		
		do {
			System.out.printf("Enter the number (1-%d) of the account\nwhose " + 
					"transactions you want to see: ", theUser.numAccounts());
			theAcct = sc.nextInt()-1;
			if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (theAcct < 0 || theAcct >= theUser.numAccounts()); //
		}
		else{
			theAcct=1;
		}
		
		theUser.displayAcctHistory(theAcct); 
		
	}
	
	public static void PrintTransHistory(Customer theUser, Scanner sc)  throws FileNotFoundException{ 
		
		int theAcct=0;
		
		if (theUser.numAccounts()!=1){ 
		do {
			System.out.printf("Enter the number (1-%d) of the account\nwhose " + 
					"transactions you want to print to file: ", theUser.numAccounts());
			theAcct = sc.nextInt()-1;
			if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (theAcct < 0 || theAcct >= theUser.numAccounts());
		}
		else{
			theAcct=1;
		}
		
		File transactionHistoryFile = new File ("Desktop/transactionHistory.txt"); 
            try (PrintWriter transPrinter = new PrintWriter("transactionHistory.txt")
            ) {
                theUser.printAcctHistory(theAcct, transPrinter);
            }
		
		
	}


        @Override
	public  void payOverDraft(){
		
		
	
	}
        @Override
	public void checkOverDraftRate(){
		
	}


}
