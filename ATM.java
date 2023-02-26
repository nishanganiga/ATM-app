package in.nishan;

import java.util.Scanner;

public class ATM {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		Bank theBank = new Bank("Bank of India");

		User aUser = theBank.addUser("Nishan", "ganiga", "2300");
		
		Account newAccount = new Account("Salary", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);

		User curUser;
		while (true) {
			curUser = ATM.mainMenuPrompt(theBank, sc);

			ATM.printUserMenu(curUser, sc);
		}
	}

	public static User mainMenuPrompt(Bank theBank, Scanner sc) {
		String userID;
		String pin;
		User authUser;

		do {
			System.out.println("\n\n Welcome To " + theBank.getName());
			System.out.print("Enter user Id :");
			userID = sc.nextLine();
			System.out.print("Enter pin :");
			pin = sc.nextLine();
			System.out.println();

			authUser = theBank.userLogin(userID, pin);
			if (authUser == null)
				System.out.println("Incorrect User ID/pin combination, Please try again.. ");

		} while (authUser == null);
		{
			return authUser;
		}
	}

	public static void printUserMenu(User theUser, Scanner sc) {
		theUser.printAccountsSummary();

		int choice;

		do {
			System.out.println(" \n Welcome " + theUser.getFirstName()+" "+theUser.getLastName());
			System.out.println();
			System.out.println("  Select the options --->");
			System.out.println("--------------------------------------------");
			System.out.println("   1) Show account transaction history.");
			System.out.println("   2) Withdrawl ");
			System.out.println("   3) Deposit");
			System.out.println("   4) Transfer ");
			System.out.println("   5) Quit");
			System.out.println();
			System.out.print("Enter choice:");
			choice = sc.nextInt();

			if (choice < 1 || choice > 5) {
				System.out.println("Invalid choice. Please choose between 1-5");
			}
		} while (choice < 1 || choice > 5);

		switch (choice) {
		case 1:
			ATM.showTransactionHistory(theUser, sc);
			break;

		case 2:
			ATM.withdrawlFunds(theUser, sc);
			break;

		case 3:
			ATM.depositFunds(theUser, sc);
			break;

		case 4:
			ATM.transferFunds(theUser, sc);
			break;
			
		case 5:
			sc.nextLine();
			break;
		}

		if (choice != 5)
			printUserMenu(theUser, sc);
	}

	public static void showTransactionHistory(User theUser, Scanner sc) {
		int theAcct;

		do {
			System.out.printf("Enter the number (1-%d) of the account \n " + 
					"whose transaction you want to see: ",theUser.numAccounts());

			theAcct = sc.nextInt() - 1;

			if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account, Please try again..");
			}
		} while (theAcct < 0 || theAcct >= theUser.numAccounts());

		theUser.printAcctTransHistory(theAcct);

	}

	public static void transferFunds(User theUser, Scanner sc) {
		int fromAcct;
		int toAcct;
		double ammount;
		double acctBal;

		do {
			System.out.printf("Enter the number (1-%d) of the account\n" +
					" to transfer from: ",theUser.numAccounts());
			fromAcct = sc.nextInt()-1;

			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account, Please try again..");
			}
		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());

		acctBal = theUser.getAcctBalance(fromAcct);

		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + 
					" to transfer to: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;

			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account, Please try again..");
			}
		} while (toAcct < 0 || toAcct >= theUser.numAccounts());

		do {
			System.out.printf("Enter the ammount to transfer (max Rs.%.02f): Rs.", acctBal);
			ammount = sc.nextDouble();

			if (ammount < 0)
				System.out.println("Amount must be greater than zero.");
			else if (ammount > acctBal)
				System.out.printf("Ammount should not be greater than \n" +
						"balance of Rs %.02f.\n", acctBal);
		} while (ammount < 0 || ammount > acctBal);

		theUser.addAcctTransaction(fromAcct, -1 * ammount,
				String.format("Transfer to account %s", theUser.getAcctUUID(toAcct)));
		theUser.addAcctTransaction(toAcct, ammount,
				String.format("Transfer from account %s", theUser.getAcctUUID(fromAcct)));
	}

	public static void withdrawlFunds(User theUser, Scanner sc) {
		int fromAcct;
		double ammount;
		double acctBal;
		String memo;

		do {
			System.out.printf("Enter the number (1-%d) of the account\n" +
					" to withdraw from: ",theUser.numAccounts());
			fromAcct = sc.nextInt()-1;

			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account, Please try again..");
			}
		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());

		acctBal = theUser.getAcctBalance(fromAcct);
		do {
			System.out.printf("Enter the ammount to withdraw (max Rs %.02f): Rs.", acctBal);
			ammount = sc.nextDouble();

			if (ammount < 0)
				System.out.println("Amount must be greater than zero.");
			else if (ammount > acctBal)
				System.out.printf("Ammount should not be greater than \n" + 
						"balance of Rs %.02f.\n", acctBal);
		} while (ammount < 0 || ammount > acctBal);

		sc.nextLine();

		System.out.print("Enter a memo: ");
		memo = sc.nextLine();

		theUser.addAcctTransaction(fromAcct, -1 * ammount, memo);
	}

	public static void depositFunds(User theUser, Scanner sc) {
		int toAcct;
		double ammount;
		double acctBal;
		String memo;

		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + 
					" to deposit in: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;

			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account, Please try again..");
			}
		} while (toAcct < 0 || toAcct >= theUser.numAccounts());

		acctBal = theUser.getAcctBalance(toAcct);
		do {
			System.out.printf("Enter the ammount to deposit (max Rs %.02f): Rs.", acctBal);
			ammount = sc.nextDouble();

			if (ammount < 0)
				System.out.println("Amount must be greater than zero.");

		} while (ammount < 0);

		sc.nextLine();

		System.out.print("Enter a memo: ");
		memo = sc.nextLine();

		theUser.addAcctTransaction(toAcct, ammount, memo);
	}
}
