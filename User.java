package in.nishan;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

	private String firstName;
	private String lastName;
	private String uuid;
	private byte pinHash[];
	private ArrayList<Account> accounts;

	public User(String firstName, String lastName, String pin, Bank theBank) {
		this.firstName = firstName;
		this.lastName = lastName;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());

		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}

		this.uuid = theBank.getNewUserUUID();
		this.accounts = new ArrayList<Account>();
		System.out.println();
		System.out.printf("New user %s %s with ID :< %s > is created successfully.\n", firstName, lastName, this.uuid);
		System.out.println("----------------------------------------------------------------");
	}

	public void addAccount(Account anAcct) {
		this.accounts.add(anAcct);
	}

	public String getUUID() {
		return this.uuid;
	}

	public String getFirstName() {
		return this.firstName;
	}
	public String getLastName() {
		return this.lastName;
	}

	public boolean validatePin(String aPin) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);

		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error, caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}

	public void printAccountsSummary() {
		System.out.printf("\n\n%s's accounts summary\n", this.firstName);

		for (int a = 0; a < this.accounts.size(); a++) {
			String line = this.accounts.get(a).getSummaryLine();
			System.out.println(line);
		}
	}

	public int numAccounts() {
		return this.accounts.size();
	}

	public void printAcctTransHistory(int acctIndex) {
		this.accounts.get(acctIndex).printTransHistory();
	}

	public double getAcctBalance(int acctIndex) {
		return this.accounts.get(acctIndex).getBalance();
	}

	public String getAcctUUID(int acctIndex) {
		return this.accounts.get(acctIndex).getUUID();
	}

	public void addAcctTransaction(int acctIndex, double ammount, String memo) {
		this.accounts.get(acctIndex).addTransaction(ammount, memo);
	}
}
