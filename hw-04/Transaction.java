// Transaction.java
/*
 (provided code)
 Transaction is just a dumb struct to hold
 one transaction. Supports toString.
*/
public class Transaction {
	public int from;
	public int to;
	public int amount;
	
   	public Transaction(int from, int to, int amount) {
		this.from = from;
		this.to = to;
		this.amount = amount;
	}

	public String toString() {
		return("from:" + from + " to:" + to + " amt:" + amount);
	}

	public boolean equals(Transaction t){
		   return this.from == t.from && this.to == t.to && this.amount == t.amount;
	}
}
