package fowler.energybilling;

import java.text.DecimalFormatSymbols;


/*Martin Fowler: The dollars class is a use of the Quantity pattern. It combines the
notion of an amount and a currency. I’m not going to go into too many
details here. Essentially you create dollars objects with a constructor
that has a number for the amount. The class supports some basic arithmetic
operations.
An important part of the dollars class is the fact that it rounds all numbers
to the nearest cent, a behavior which is often very important in
financial systems. As my friend Ron Jeffries told me: “Be kind to pennies,
and they will be kind to you”.
JK: just added a round method that is invoked at the end of each charge method
*/

public class Dollars {
	double amount;
	String currency;
	
	public Dollars(){
		this.amount = 0.0;
		this.currency = "USD";
		}

	
	public Dollars(double amount){
		this.amount = amount;
		this.currency = "USD";
		}

	public Dollars(Dollars dollar) {
		this.amount = dollar.amount;
		this.currency = dollar.currency;		
	}

	public Dollars plus(Dollars dollars) {
		this.amount += dollars.amount;
		return this;
	}

	public Double getAmount() {
		return amount;
	}


	public void setAmount(Double amount) {
		this.amount = amount;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public Dollars times(double taxRate) {
		this.amount = this.amount * taxRate;
		return this;
	}

	public Dollars minus(Dollars dollars) {
		this.amount -= dollars.amount;
		return this;
	}
	
	
	
	public Dollars max(Dollars dollars) {
		Dollars result;
		if (this.amount <= dollars.amount) {
			result = dollars;
		} else {
			result = this;
		}
			return result;
	}
		
		

	public Dollars min(Dollars dollars) {
		Dollars result;
		if (this.amount <= dollars.amount) {
			result = this;
		} else {
			result = dollars;
		}
			return result;
	}

	public boolean isGreaterThan(Dollars dollars) {
		boolean result;
		if(this.amount >= dollars.amount){
			result= true;}
		else{
			result = false;
		}
		return result;
	}
	

	//c number of decimals to which we want to round
	public Dollars round(int c) {
		int temp=(int)((this.amount*Math.pow(10,c)));
		 this.amount = (double)temp/Math.pow(10,c);
		 return this;
		}
}
