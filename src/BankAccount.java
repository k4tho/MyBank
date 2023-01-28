/**
 * Class represents a user's bank account, which contains all of the personal checking accounts,
 * their log in information with username, and personal information
 * @author Katie Ho
 * @version 1.0
 */

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class BankAccount {
	/**
	 * Username of the bank account for login
	 */
	private String username;
	
	/**
	 * Password of the bank account for login
	 */
	private String password;
	
	/**
	 * Pin code of the bank account for login
	 */
	private int pinCode;
	
	/**
	 * First name of the user
	 */
	private String firstName;
	
	/**
	 * Last name of the user
	 */
	private String lastName;
	
	/**
	 * A record of all the personal checking accounts belonging to the bank account
	 */
	private ArrayList<PersonalAccount> personalAccounts = new ArrayList<>();
	
	/**
	 * Default constructor initializing all fields to 0 or N/A
	 */
	public BankAccount() {
		this.username = "N/A";
		this.password = "N/A";
		this.pinCode = 0000;
		this.firstName = "N/A";
		this.lastName = "N/A";
	}
	
	/**
	 * Overload constructor initializing all fields to its values in the parameters
	 * @param username username portion of the bank account login
	 * @param password password portion of the bank account login
	 * @param pinCode pin portion of the bank account login
	 * @param firstName personal first name of the user
	 * @param lastName last name of the user
	 */
	public BankAccount(String username, String password, int pinCode, String firstName, String lastName) {
		this.username = username;
		this.password = password;
		this.pinCode = pinCode;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	/**
	 * Creates a single personal checking account and adds it to the list of accounts that the user owns
	 * @param accName personal account's name
	 * @param accNumber personal account's identifying number
	 * @param balance personal account's overall balance
	 * @return a newly made personal account
	 */
	public PersonalAccount CreatePersonalAccount(String accName, long accNumber, double balance) {
		PersonalAccount personalAccount = new PersonalAccount(accName, accNumber, balance);
		personalAccounts.add(personalAccount);
		
		return personalAccount;
	}
	
	/**
	 * Transfers money from one personal account to another.
	 * Withdrawing account decreases and transferring account increases in balance
	 * @param accWithdrawnFrom personal account that the money is being withdrawn from
	 * @param accSentTo personal account that the money is being sent to
	 * @param amountToTransfer amount that user wants to transfer from one account to another
	 * @return a String that represents the above information regarding the transfer
	 * @see BankAccount#IsAmountPositive(double)
	 * @see BankAccount#HasSufficientFunds(PersonalAccount, double)
	 * @see BankAccount#GetTransferConfirmationNumber()
	 * @see BankAccount#GetTransactionDate()
	 * @see BankAccount#PrintTransferMoneyFromStatement(PersonalAccount, String)
	 * @See BankAccount#PrintTransferMoneyToStatement(PersonalAccount, String)
	 */
	public String TransferMoney(PersonalAccount accWithdrawnFrom, PersonalAccount accSentTo, double amountToTransfer) {
		if (IsAmountPositive(amountToTransfer) == false) {
			return "Amount entered must be positive.";
		}
		else if (HasSufficientFunds(accWithdrawnFrom, amountToTransfer) == false) {
			return "Bank account has insufficient funds.";
		}
		else {
			String transferConfirmationNumber = GetTransferConfirmationNumber();
			
			accWithdrawnFrom.CreateTransaction(GetTransactionDate(), PrintTransferMoneyFromStatement(accSentTo, transferConfirmationNumber), "Credit", -amountToTransfer);
			accSentTo.CreateTransaction(GetTransactionDate(), PrintTransferMoneyToStatement(accWithdrawnFrom, transferConfirmationNumber), "Credit", amountToTransfer);
			
			return "$" + (Math.round(amountToTransfer*100.00)/100.00) + " was sent from " + GetAccNameWithNum(accWithdrawnFrom) + " to " + GetAccNameWithNum(accSentTo);
		}
	}
	
	/**
	 * Deposits money into a personal checking account -> increase in balance
	 * @param acc personal account to deposit money into
	 * @param amountToDeposit the amount of money that the user wants to put into their account
	 * @return a String that represents the above information regarding the amount deposited to the acc and the new balance
	 * @see BankAccount#GetTransactionDate()
	 * @see BankAccount#PrintMoneyDespositStatement(PersonalAccount)
	 * @see Transaction#GetAmount()
	 * @see BankAccount#GetAccNameWithNum(PersonalAccount)
	 * @see Transaction#GetBalance()
	 */
	public String DepositMoneyTo(PersonalAccount acc, double amountToDeposit) {
		Transaction newTransaction;
		newTransaction = acc.CreateTransaction(GetTransactionDate(), PrintMoneyDepositStatement(acc), "Credit", amountToDeposit);
		
		return "You have deposited $" + newTransaction.GetAmount() + " to " + GetAccNameWithNum(acc) + ". Your new balance is $" + newTransaction.GetBalance();
	}
	
	/**
	 * Withdraws money from a personal checking account -> decrease in balance
	 * @param acc personal account to withdraw money from
	 * @param amountToWithdraw the amount of money that the user wants to take out of an account
	 * @return a String that represents the above information regarding the amount withdrew from the acc and the new balance
	 * @see BankAccount#GetTransactionDate()
	 * @see BankAccount#PrintMoneyWithdrawalStatement(PersonalAccount)
	 * @see Transaction#GetAmount()
	 * @see BankAccount#GetAccNameWithNum(PersonalAccount)
	 * @see Transaction#GetBalance()
	 */
	public String WithdrawMoneyFrom(PersonalAccount acc, double amountToWithdraw) {
		Transaction newTransaction;
		newTransaction = acc.CreateTransaction(GetTransactionDate(), PrintMoneyWithdrawalStatement(acc), "Credit", -amountToWithdraw);
		
		return "You have withdrew $" + newTransaction.GetAmount() + " to " + GetAccNameWithNum(acc) + ". Your new balance is $" + newTransaction.GetBalance();
	}
	
	/**
	 * Checks if the amount of money is positive for withdrawals, deposits, or transfers
	 * @param amount the amount of money 
	 * @return a boolean that represents whether the amount of money is positive or not
	 */
	public boolean IsAmountPositive(double amount) {
		if (amount > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Checks if the balance of the account is greater than the amount of money to withdraw
	 * @param accWithdrawnFrom personal account that the money is being withdrawn from
	 * @param amountToTransfer the amount of money that needs to be withdrawn
	 * @return a boolean that represents if there is enough money in the account to withdraw
	 * @see PersonalAccount#GetBalance()
	 */
	public boolean HasSufficientFunds(PersonalAccount accWithdrawnFrom, double amountToTransfer) {
		if (accWithdrawnFrom.GetBalance() > amountToTransfer) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Checks if the username and password of the bank account matches the input
	 * @param username the username portion of the bank account
	 * @param password the password portion of the bank account
	 * @return a boolean that represents whether the login username and password information matches the input
	 */
	public boolean VerifyLogin(String username, String password) {
		if (this.username.equals(username) == true) {
			if (this.password.equals(password) == true) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	/**
	 * Checks if the pin code of the bank account matches the input
	 * @param pinCode the pin code portion of the bank account
	 * @return a boolean that represents whether the login pin matches the input
	 */
	public boolean VerifyPin(int pinCode) {
		if (pinCode == this.pinCode) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Sets the username of the bank account
	 * @param username username of the bank account login
	 */
	public void SetUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Sets the password of the bank account
	 * @param password password of the bank account login
	 */
	public void SetPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Sets the pin code of the bank account
	 * @param pinCode pin code of the bank account login
	 */
	public void SetPinCode(int pinCode) {
		this.pinCode = pinCode;
	}
	
	/**
	 * Sets the user's first name for the bank account
	 * @param firstName first name of the user
	 */
	public void SetFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Sets the user's last name for the bank account
	 * @param lastName last name of the user
	 */
	public void SetLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Gets the username of the bank account login
	 * @return a String that represents the account's username
	 */
	public String GetUsername() {
		return this.username;
	}
	
	/**
	 * Gets the password of the bank account login
	 * @return a String that represents the account's password
	 */
	public String GetPassword() {
		return this.password;
	}
	
	/**
	 * Gets the pin code of the bank account login
	 * @return an int that represents the account's pin code
	 */
	public int GetPinCode() {
		return this.pinCode;
	}
	
	/**
	 * Gets the first name of the user
	 * @return a String that represents the user's first name
	 */
	public String GetFirstName() {
		return this.firstName;
	}
	
	/**
	 * Gets the last name of the user
	 * @return a String that represents the user's last name
	 */
	public String GetLastName() {
		return this.lastName;
	}
	
	/**
	 * Gets the list of all the personal checking accounts that the bank account owns
	 * @return an ArrayList of all the personal accounts on the bank account
	 */
	public ArrayList<PersonalAccount> GetPersonalAccounts(){
		return this.personalAccounts;
	}
	
	/**
	 * Prints out the account's full name for the welcome screen on driver
	 * @return a String that contains the account's full name
	 */
	public String PrintAccOwner() {
		return this.firstName + " " + this.lastName + "'s Account";
	}
	
	/**
	 * Prints out all of the personal checking accounts on the bank account
	 * @return a String that contains information of all the personal checking accounts
	 * @see BankAccount#GetAccNameWithNum(PersonalAccount)
	 * @see PersonalAccount#DisplayMoney(double)
	 * @see PersonalAccount#GetBalance()
	 */
	public String PrintPersonalAccs() {
		String displayAccs = "Personal Accounts: \n";
		int numPersonalAccs = 1;
		
		for (PersonalAccount acc: personalAccounts) {
			displayAccs += "\t" + numPersonalAccs + ") " + GetAccNameWithNum(acc) + "\n\t\t" + acc.DisplayMoney(acc.GetBalance()) + "\n";
			numPersonalAccs ++;
		}
		return displayAccs;
	}
	
	/**
	 * Prints a transaction description statement for the account that had money withdrawn through a transfer
	 * @param accSentTo personal account that the money got sent to
	 * @param transferConfirmationNumber id number confirming the transaction
	 * @return a String that contains a transaction description statement including what account the money was sent to and the confirmation number
	 * @see BankAccount#GetAccShortNameWithNum(PersonalAccount)
	 */
	private String PrintTransferMoneyFromStatement(PersonalAccount accSentTo, String transferConfirmationNumber) {
		return "Banking transfer to " + GetAccShortNameWithNum(accSentTo) + " confirmation #" + transferConfirmationNumber;
	}
	
	/**
	 * Prints a transaction description statement for the account that received money through a transfer
	 * @param accWithdrawnFrom personal account that the money was sent from
	 * @param transferConfirmationNumber id number confirming the transaction
	 * @return a String that contains a transaction description statement including what account the money was sent from and the confirmation number
	 * @see BankAccount#GetAccShortNameWithNum(PersonalAccount)
	 */
	private String PrintTransferMoneyToStatement(PersonalAccount accWithdrawnFrom, String transferConfirmationNumber) {
		return "Banking banking transfer from "+ GetAccShortNameWithNum(accWithdrawnFrom) + " confirmation #" + transferConfirmationNumber;
	}
	
	/**
	 * Prints a transaction description statement for an account that received a money deposit
	 * @param accDeposit personal account that the money was deposited into
	 * @return a String that contains a transaction description statement including the personal account deposit and the confirmation number
	 * @see BankAccount#GetAccShortNameWithNum(PersonalAccount)
	 */
	private String PrintMoneyDepositStatement(PersonalAccount accDeposit) {
		return "Banking deposit to " + GetAccShortNameWithNum(accDeposit) + " confirmation #" + GetTransferConfirmationNumber();
	}

	/**
	 * Prints a transaction description statement for an account that received a money withdrawal
	 * @param accWithdrawal personal account that the money was withdrawn from
	 * @return a String that contains a transaction description statement including the personal account withdrawal and confirmation number
	 * @see BankAccount#GetAccShortNameWithNum(PersonalAccount)
	 */
	private String PrintMoneyWithdrawalStatement(PersonalAccount accWithdrawal) {
		return "Banking withdrawal from " + GetAccShortNameWithNum(accWithdrawal) + " confirmation #" + GetTransferConfirmationNumber();
	}
	
	/**
	 * Prints the shortened version of the account name with identifier number
	 * @param acc personal account 
	 * @return a String that contains a shortened version of the account name and identifier number
	 * @see PersonalAccount#GetShortenedAccName()
	 * @see PersonalAccount#GetShortenedAccNum()
	 */
	private String GetAccShortNameWithNum(PersonalAccount acc) {
		return acc.GetShortenedAccName() + " - " + acc.GetShortenedAccNum();
	}
	
	/**
	 * Prints the account name with identifier number
	 * @param acc personal account
	 * @return a String that contains the account name and identifier number
	 * @see PersonalAccount#GetAccName()
	 * @see PersonalAccount#GetShortenedAccNum()
	 */
	private String GetAccNameWithNum(PersonalAccount acc) {
		return acc.GetAccName() + " - " + acc.GetShortenedAccNum();
	}
	
	/**
	 * Prints a random identification number to confirm a transfer transaction
	 * @return a String that represents a transfer confirmation number
	 */
	private String GetTransferConfirmationNumber() {
		Random rand = new Random();
		long randomConfirmationNumber = rand.nextLong(9999999999L);
		
		String randomConfirmationNumberString = Long.toString(randomConfirmationNumber);
		
		if (randomConfirmationNumberString.length() < 10) {
			int zerosNeeded = 10 - randomConfirmationNumberString.length();
			for (int i = 0; i < zerosNeeded; ++i) {
				randomConfirmationNumberString = 0 + randomConfirmationNumberString;
			}
		}
		
		return randomConfirmationNumberString;
	}
	
	/**
	 * Prints the processing date that the transaction was made
	 * @return a String that represents the date of the transaction
	 */
	private String GetTransactionDate() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		
		String dateString = formatter.format(date);
		return dateString;
	}
}
