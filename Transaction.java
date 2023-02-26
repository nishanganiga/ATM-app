package in.nishan;

import java.util.Date;

public class Transaction {

	private double ammount;
	private Date timeStamp;
	private String memo;
	private Account inAccount;

	public Transaction(double ammount, Account inAccount) {
		this.ammount = ammount;
		this.inAccount = inAccount;
		this.timeStamp = new Date();
		this.memo = "";
	}

	public Transaction(double ammount, String memo, Account inAccount) {
		this(ammount, inAccount);
		this.memo = memo;

	}

	public double getMoney() {
		return this.ammount;
	}

	public String getSummaryLine() {
		if (this.ammount >= 0) {
			return String.format("%s : Rs %.02f : %s", this.timeStamp.toString(), this.ammount, this.memo);
		} else {
			return String.format("%s : Rs (%.02f) : %s", this.timeStamp.toString(), -this.ammount, this.memo);
		}
	}

	public void transactionInfo() {
		System.out.println(this.ammount + " " + this.inAccount + " " + this.memo + " " + this.timeStamp);
	}
}
