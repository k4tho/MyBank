/**
 * Class represents a single personal checking account.
 * Contains the account's name, unique number, overall balance, and a history of transactions
 * @author Katie Ho
 * @version 1.0
 */

import java.util.ArrayList;
import java.lang.Math;

public class PersonalAccount {
	/**
	 * The name of the account
	 */
	private String accName;
	
	/**
	 * The identifier number of the account
	 */
	private long accNumber;
	
	/**
	 * The overall balance (sum of past transactions' amounts) of the account
	 */
	private double balance;
	
	/**
	 * The list of past transactions made on the account
	 */
	private ArrayList<Transaction> transactions = new ArrayList<>();
	
	/**
	 * Default constructor initializing all fields to 0 or N/A
	 */
	public PersonalAccount() {
		this.accName = "N/A";
		this.accNumber = 000000000000;
		this.balance = 0.00;
	}
	
	/**
	 * Overload constructor initializing all fields to its values in the parameters
	 * @param accName name of the account
	 * @param accNumber identifier number of the account
	 * @param balance overall balance of the account
	 */
	public PersonalAccount(String accName, long accNumber, double balance) {
		this.accName = accName;
		this.accNumber = accNumber;
		this.balance = balance;
	}
	
	/**
	 * Creates a single transaction and add it to the personal acc's history of transactions.
	 * Takes the transaction and adds it to the overall balance
	 * @param postingDate 
	 * @param description
	 * @param type
	 * @param amount
	 * @return a newly made transaction
	 * @see PersonalAccount#UpdateAccBalance()
	 * @see PersonalAccount#AddTransaction(Transaction)
	 */
	public Transaction CreateTransaction(String postingDate, String description, String type, double amount) {
		double roundedAmount = Math.round(amount*100.00)/100.00;
		UpdateAccBalance(roundedAmount);
		Transaction transaction = new Transaction(postingDate, description, type, roundedAmount, this.balance);
		AddTransaction(transaction);
		
		return transaction;
	}
	
	/**
	 * Puts amount into money format with a dollar sign
	 * @param moneyAmount
	 * @return a String that represents the amount
	 */
	public String DisplayMoney(double moneyAmount) {
		String moneyString;
		if (moneyAmount < 0) {
			moneyString = "-$" + String.format("%.2f", Math.abs(moneyAmount)); 
		}
		else {
			moneyString = "$" + String.format("%.2f", Math.abs(moneyAmount));
		}
		
		return moneyString;
	}
	
	/**
	 * Shortens the account name
	 * @return a String that represents the account name but shortened
	 */
	public String GetShortenedAccName() {
		String shortenedAccName = "";
		for (int i = 0; i < 3; ++i) {
			shortenedAccName += this.accName.charAt(i);
		}
		
		return shortenedAccName;
	}
	
	/**
	 * Shortens the account number from a long to an int
	 * @return an int that represents the account number but shortened
	 */
	public int GetShortenedAccNum() {
		Long accNumber = this.accNumber;
		String accNumberString = accNumber.toString();
		
		String lastFourDigitsString = "";
		for (int i = 4; i > 0; --i) {
			lastFourDigitsString += accNumberString.charAt(accNumberString.length() - i);
		}
		
		int lastFourDigits = Integer.parseInt(lastFourDigitsString);
		
		return lastFourDigits;
	}
	
	/**
	 * Sets the name of the account
	 * @param accName name portion of the account
	 */
	public void SetAccName(String accName) {
		this.accName = accName;
	}
	
	/**
	 * Sets the identifier number of the account
	 * @param accNumber number portion of the account
	 */
	public void SetAccNumber(long accNumber) {
		this.accNumber = accNumber;
	}
	
	/**
	 * Sets the overall balance of the account while rounding it to decimal places
	 * @param balance balance portion of the account
	 */
	public void SetBalance(double balance) {
		this.balance = Math.round(balance*100.00)/100.00;
	}
	
	/**
	 * Gets the name of the account
	 * @return a String that represents the name of the account
	 */
	public String GetAccName() {
		return this.accName;
	}
	
	/**
	 * Gets the identifying number of the account
	 * @return a long that represents the number of the account
	 */
	public long GetAccNumber() {
		return this.accNumber;
	}
	
	/**
	 * Gets the overall balance of the account
	 * @return a double that represents the balance of the account
	 */
	public double GetBalance() {
		return this.balance;
	}
	
	/**
	 * Prints out overall account information for display in the driver class
	 * Displays the account name, overall balance, and all past transactions made.
	 * Past transactions will display its posting date, description, amount of the transaction, and its contribution to the overall balance of the acc
	 * @see PersonalAccount#GetShortenedAccNum()
	 * @see PersonalAccount#DisplayMoney(double)
	 * @see Transaction#GetPostingDate()
	 * @see Transaction#GetDescription()
	 * @see Transaction#GetType()
	 * @see Transaction#GetAmount()
	 * @see Transaction#GetBalance()
	 */
	public void PrintAccInfo() {
		System.out.println(this.accName + " - " + GetShortenedAccNum());
		System.out.print("Balance: " + DisplayMoney(this.balance));
		System.out.println();
		System.out.println();
		
		System.out.println("Transactions:");
		for (Transaction transaction: transactions) {
			System.out.println("\t" + transaction.GetPostingDate() + " : " + 
					transaction.GetDescription() + " : " + 
					transaction.GetType() + " : " + 
					DisplayMoney(transaction.GetAmount()) + " : " + 
					DisplayMoney(transaction.GetBalance()));
		}
	}
	
	/**
	 * Adds a transaction to the account's list of previously made transactions.
	 * @param newTransaction
	 */
	private void AddTransaction(Transaction newTransaction) {
		transactions.add(newTransaction);
	}
	
	/**
	 * Takes in an amount and adds it to the account's overall balance accordingly
	 * @param amount
	 */
	private void UpdateAccBalance(double amount) {
		double newBalance = this.balance + amount;
		SetBalance(newBalance);
	}
}
