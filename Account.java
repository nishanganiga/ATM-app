package in.nishan;

import java.util.ArrayList;

public class Account {

	private String name;
	private String uuid;
	private User holder;
	private ArrayList<Transaction> transaction;

	public Account(String name, User holder, Bank theBank) {

		this.name = name;
		this.holder = holder;
		this.uuid = theBank.getNewAccountUUID();
		this.transaction = new ArrayList<Transaction>();
	}

	public String getUUID() {
		return this.uuid;
	}

	public String getSummaryLine() {
		double balance = this.getBalance();
		if (balance >= 0) {
			return String.format("%s : Rs %.02f : %s", this.uuid, balance, this.name);
		} else {
			return String.format("%s : Rs (%.02f) : %s", this.uuid, balance, this.name);
		}
	}

	public double getBalance() {
		double balance = 0;
		for (Transaction t : this.transaction)
			balance += t.getMoney();

		return balance;
	}

	  public void printTransHistory(){
		  System.out.printf("\nTransaction history for account %s\n",this.uuid);
		  for(int t=this.transaction.size()-1;t>=0;t--)
		  {
			  System.out.println(transaction.get(t).getSummaryLine());
		  }
		  System.out.println();
	    }

	public void addTransaction(double ammount, String memo) {
		Transaction newTrans = new Transaction(ammount, memo, this);
		this.transaction.add(newTrans);
	}
}
