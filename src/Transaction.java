/**
 * Class represents a single transaction with contains the date, type, amount, and balance of the overall account
 * @author Katie Ho
 * @version 1.0
 */

public class Transaction {
	public PersonalAccount personalAccount;
	
	/**
	 * Date that the transaction was made
	 */
	private String postingDate;
	
	/**
	 * Description of the transaction
	 */
	private String description;
	
	/**
	 * How the payment/transaction was made
	 */
	private String type;
	
	/**
	 * Cost of the transaction
	 */
	private double amount;
	
	/**
	 * The overall balance of the account after the transaction was made
	 */
	private double balance;
	
	/**
	 * Default constructor initializing all fields to 0, N/A, or Processing
	 */
	public Transaction() {
		this.postingDate = "Processing";
		this.description = "N/A";
		this.type = "N/A";
		this.amount = 0.00;
		this.balance = 0.00;
	}
	
	/**
	 * Overload constructor initializing all fields to its values in the parameters
	 * @param postingDate date portion of the transaction
	 * @param description description portion of the transaction
	 * @param type type portion of the transaction
	 * @param amount amount/cost portion of the transaction
	 * @param balance balance portion of the personal account
	 */
	public Transaction(String postingDate, String description, String type, double amount, double balance) {
		this.postingDate = postingDate;
		this.description = description;
		this.type = type;
		this.amount = amount;
		this.balance = balance;
	}
	
	/**
	 * Gets the date of when the transaction was made
	 * @return a String that represents the posting date
	 */
	public String GetPostingDate() {
		return this.postingDate;
	}
	
	/**
	 * Gets the description of the transaction
	 * @return a String that represents the description of the transaction
	 */
	public String GetDescription() {
		return this.description;
	}
	
	/**
	 * Gets the type of transaction
	 * @return a String that represents the type of transaction that was made
	 */
	public String GetType() {
		return this.type;
	}
	
	/**
	 * Gets the amount that the transactions
	 * @return a double representing the amount
	 */
	public double GetAmount() {
		return this.amount;
	}
	
	/**
	 * Gets the overall balance of the account after the transaction was made
	 * @return a double that represents that overall balance of the account
	 */
	public double GetBalance() {
		return this.balance;
	}

	/**
	 * Sets the date that the transaction was made
	 * @param postingDate date portion of the transaction
	 */
	private void SetPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}
	
	/**
	 * Sets the description of the transaction
	 * @param description description portion of the transaction
	 */
	private void SetDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Sets the type of transaction that was made
	 * @param type type portion of the transaction
	 */
	private void SetType(String type) {
		this.type = type;
	}
	
	/**
	 * Sets the amount/price of the transaction
	 * @param amount amount portion of the transaction
	 */
	private void SetAmount(double amount) {
		this.amount = amount;
	}
	
	/**
	 * Sets the overall balance of the account
	 * @param balance balance portion of the personalAccount
	 */
	private void SetBalance(double balance) {
		this.balance = Math.round(balance*100.00)/100.00;
	}
}
