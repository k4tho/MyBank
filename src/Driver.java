/**
 * Class is a driver to simulate users using a mobile banking app.
 * Users will be able to log into their premade account with their username, password, and pin code.
 * Users can then explore different activities they can do with their account.
 * Activities include transferring money between personal checking accounts, withdrawing/depositing money, 
 * and viewing past transactions made on the personal account.
 * @author Katie Ho
 * @version 1.0
 */

import java.util.Scanner;
import java.util.ArrayList;



public class Driver{
	/**
	 * Record of all the bank accounts made at this bank
	 */
	private static ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>();
	
	/**
	 * Creates a new bank account and adds it the the record of bank account
	 * @param username username portion of the bank account login
	 * @param password password portion of the bank account login
	 * @param pinCode pin code portion of the bank account login
	 * @param firstName first name of the account's user
	 * @param lastName last name of the account's user
	 * @return a newly made bank account
	 */
	private static BankAccount CreateBankAccount(String username, String password, int pinCode, String firstName, String lastName) {
		BankAccount bankAccount = new BankAccount(username, password, pinCode, firstName, lastName);
		bankAccounts.add(bankAccount);
		
		return bankAccount;
	}
	
	/**
	 * Searches for a bank account in the list of a bank accounts and identifies a match based on same username and password
	 * @param usernameInput user's input for the username
	 * @param passwordInput user's input for the password
	 * @return a bank account that matches the login information
	 */
	private static BankAccount FindMatchingBankAcc(String usernameInput, String passwordInput) {
		for (int i = 0; i < bankAccounts.size(); ++i) {
			if (bankAccounts.get(i).VerifyLogin(usernameInput, passwordInput) == true) {
				return bankAccounts.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Simulates user experience on mobile banking app
	 * @param args command line-arguments
	 * @see Driver#CreateBankAccount(String, String, int, String, String)
	 * @see BankAccount#CreatePersonalAccount(String, long, double)
	 * @see PersonalAccount#CreateTransaction(String, String, String, double)
	 */
	public static void main(String[] args) {
		/*
		 * mock accounts
		 */
		BankAccount mockAcc1 = CreateBankAccount("rick", "san", 1234, "Rick", "Sanchez");
		BankAccount mockAcc2 = CreateBankAccount("morty", "smi", 4321, "Morty", "Smith");
		
		PersonalAccount mockDebit1 = mockAcc1.CreatePersonalAccount("Adv Plus Banking", 392852342332L, 100.00);
		PersonalAccount mockSaving1 = mockAcc1.CreatePersonalAccount("Advantage Savings", 385683729957L, 0.00);
		PersonalAccount mockDebit2 = mockAcc2.CreatePersonalAccount("Adv Plus Banking", 194859284823L, 50.00);
		PersonalAccount mockSaving2 = mockAcc2.CreatePersonalAccount("Advantage Savings", 475729550483L, 200.00);
		
		Transaction mockTransaction1 = mockDebit1.CreateTransaction("Processing", "Etsy", "Credit", -10.00);
		Transaction mockTransaction2 = mockDebit1.CreateTransaction("1/08/2023", "Amazon", "Credit", -26.83);
		Transaction mockTransaction3 = mockDebit1.CreateTransaction("1/05/2023", "Seaside Bakery", "Cash", -7.00);
		Transaction mockTransaction4 = mockDebit1.CreateTransaction("1/04/2023", "Kitakata Ramen", "Credit", -21.47);
		Transaction mockTransaction5 = mockDebit1.CreateTransaction("1/01/2023", "Target Refund", "Credit", 13.20);
		Transaction mockTransaction6 = mockSaving1.CreateTransaction("1/03/2023", "Online Banking Transfer from CHK 2332 Confirmation #2849539105", "Transfer", 100.00);
		Transaction mockTransaction7 = mockSaving1.CreateTransaction("12/30/2022", "Online Banking Transfer from CHK 2332 Confirmation #9353066105", "Transfer", 150.00);
		Transaction mockTransaction8 = mockSaving1.CreateTransaction("12/21/2022", "Online Banking Transfer from CHK 2332 Confirmation #29348285442", "Transfer", 200.00);
		Transaction mockTransaction9 = mockDebit2.CreateTransaction("Processing", "Walmart", "Cash", -34.80);
		Transaction mockTransaction10 = mockDebit2.CreateTransaction("1/12/2023", "Barnes & Noble", "Credit", -17.65);
		Transaction mockTransaction11 = mockDebit2.CreateTransaction("1/10/2023", "Starbucks", "Cash", -6.22);
		Transaction mockTransaction12 = mockDebit2.CreateTransaction("1/10/2023", "IHop", "Credit", -22.50);
		Transaction mockTransaction13 = mockDebit2.CreateTransaction("1/06/2023", "7Leaves", "Cash", -5.60);
		Transaction mockTransaction14 = mockSaving2.CreateTransaction("12/31/2022", "Online Banking Transfer from CHK 9232 Confirmation #2849539105", "Transfer", 34.00);
		Transaction mockTransaction15 = mockSaving2.CreateTransaction("12/22/2022", "Online Banking Transfer from CHK 9232 Confirmation #9353066105", "Transfer", 360.00);
		Transaction mockTransaction16 = mockSaving2.CreateTransaction("12/18/2022", "Online Banking Transfer to CHK 2332 Confirmation #29348285442", "Transfer", -50.00);
		
		
		
		
		
		/**
		 * Login display where users have to input their username, password, and pin
		 * Home page displays options for users to transfer money between bank accounts,
		 * withdraw money, deposit money, and view transaction history
		 */
		Scanner scnr = new Scanner(System.in);
		
		boolean needLogin = true;
		BankAccount userBankAccount = null;
		
		System.out.println("Welcome to Bank of PollyWolly!\n");
		while (needLogin) {
			try {
				System.out.println("Enter your username: ");
				String usernameInput = scnr.next();
				System.out.println("Enter your password");
				String passwordInput = scnr.next();
				
				userBankAccount = FindMatchingBankAcc(usernameInput, passwordInput);
				
				if (userBankAccount == null) {
					throw new Exception("Invalid username/password. Try again.");
				}
				else {
					needLogin = false;
				}
			}
			catch (Exception excpt){
				System.out.println(excpt.getMessage() + "\n\n");
			}
		}
		
		needLogin = true;
		
		while (needLogin) {
			try {
				System.out.println("\n\nFor two factor authentication, please enter your pin code: ");
				int pinCodeInput = scnr.nextInt();
				if (userBankAccount.VerifyPin(pinCodeInput) == false) {
					throw new Exception("Incorrect pin.");
				}
				else {
					System.out.println("Pin code success. Now logging in.\n\n");
					needLogin = false;
				}
			}
			catch (Exception excpt) {
				System.out.println("ERROR: " + excpt.getMessage() + " Try again.");
			}
		}
		
		needLogin = true;
		
		System.out.println("\n\n\n\n" + userBankAccount.PrintAccOwner());
		System.out.println();
		System.out.println(userBankAccount.PrintPersonalAccs());
		
		
		
		
		
		/*
		 * Banking App Simulation - Explore Home Page
		 */
		
		boolean needChoice = true;
		int userChoice = 0;
		
		while (needChoice) {
			try {
				System.out.println("\n\n\nWhat would you like to do?");
				System.out.println("\t1) Transfer money between personal accounts.");
				System.out.println("\t2) Withdraw money from an account.");
				System.out.println("\t3) Deposit money into an account.");
				System.out.println("\t4) View transaction history.");
				System.out.println("\t5) Quit.\n");
				System.out.println("Choose 1-5: ");
				userChoice = scnr.nextInt();
				System.out.println("\n\n");
				
				if (userChoice < 1 || userChoice > 6) {
					throw new Exception("Invalid option.");
				}
				else {
					/*
					 * Banking App Simulation - Option 1 Selected (transfer money between accs)
					 */
					if (userChoice == 1) {
						int accWithdrawnFromNum = 0;
						int accTransferToNum = 0;
						PersonalAccount accWithdrawnFrom = null;
						PersonalAccount accTransferTo = null;
						needChoice = true;
						
						//Choosing accounts to transfer money between
						while (needChoice) {
							try {
								System.out.println(userBankAccount.PrintPersonalAccs());
								System.out.println("Which account would you like to transfer money from?");
								accWithdrawnFromNum = scnr.nextInt();
								System.out.println("Which account would you like to transfer money to?");
								accTransferToNum = scnr.nextInt();
								
								if (accWithdrawnFromNum == accTransferToNum) {
									throw new Exception("Can not choose the same account");
								}
								else if (userBankAccount.GetPersonalAccounts().size() < accWithdrawnFromNum || accWithdrawnFromNum < 1) {
									throw new Exception("Option selected for withdrawing account is invalid.");
								}
								else if (userBankAccount.GetPersonalAccounts().size() < accTransferToNum || accTransferToNum < 1) {
									throw new Exception("Option selected for transferring account is invalid.");
								}
								else {
									accWithdrawnFrom = userBankAccount.GetPersonalAccounts().get(accWithdrawnFromNum - 1);
									accTransferTo = userBankAccount.GetPersonalAccounts().get(accTransferToNum - 1);
									needChoice = false;
								}
							}
							catch (Exception excpt) {
								System.out.println("ERROR: " + excpt.getMessage() + " Try again.\n\n\n");
							}
						}
						
						needChoice = true;
						double amountToTransfer = 0.00;
						
						//choosing amount of money to transfer
						while (needChoice) {
							try {
								System.out.println("How much would you like to transfer over?");
								amountToTransfer = scnr.nextDouble();
								if (userBankAccount.HasSufficientFunds(accWithdrawnFrom, amountToTransfer) == false) {
									throw new Exception("Insufficient funds.");
								}
								else if (userBankAccount.IsAmountPositive(amountToTransfer) == false) {
									throw new Exception("Amount must be positive.");
								}
								else {
									System.out.println(userBankAccount.TransferMoney(accWithdrawnFrom, accTransferTo, amountToTransfer));
									needChoice = false;
								}
							}
							catch (Exception excpt){
								System.out.println("ERROR: " + excpt.getMessage() + " Try again.\n\n\n");
							}
						}
						needChoice = true;
					}
					
					/*
					 * Banking App Simulation - Option 2 Selected (withdraw money)
					 */
					else if (userChoice == 2) {
						needChoice = true;
						int accWithdrawingNum = 0;
						PersonalAccount accWithdrawing = null;
						
						//choosing account to withdraw money from
						while (needChoice) {
							try {
								System.out.println(userBankAccount.PrintPersonalAccs());
								System.out.println("Which personal account would you like to withdraw money from?");
								accWithdrawingNum = scnr.nextInt();
								
								if (accWithdrawingNum < 1 || accWithdrawingNum > userBankAccount.GetPersonalAccounts().size()) {
									throw new Exception("Option selected for withdrawing account is invalid.");
								}
								else {
									accWithdrawing = userBankAccount.GetPersonalAccounts().get(accWithdrawingNum-1);
									needChoice = false;
								}
							}
							catch (Exception excpt) {
								System.out.println("ERROR: " + excpt.getMessage() + " Try again.\n\n\n");
							}
						}
						needChoice = true;
						
						//choosing amount of money to withdraw
						while (needChoice) {
							try {
								System.out.println("How much money would you like to withdraw?");
								double amountToWithdraw = scnr.nextDouble();
								
								if (userBankAccount.IsAmountPositive(amountToWithdraw) == false) {
									throw new Exception("Amount must be positive.");
								}
								else if (userBankAccount.HasSufficientFunds(accWithdrawing, amountToWithdraw) == false) {
									throw new Exception("Insufficient funds.");
								}
								else {
									System.out.println(userBankAccount.WithdrawMoneyFrom(accWithdrawing, amountToWithdraw));
									needChoice = false;
								}
							}
							catch (Exception excpt){
								System.out.println("ERROR: " + excpt.getMessage() + " Try again.\n\n\n");
							}
						}
						needChoice = true;
					}
					
					/*
					 * Banking App Simulation - Option 3 Selected (deposit money)
					 */
					else if (userChoice == 3) {
						needChoice = true;
						int accDepositingNum = 0;
						PersonalAccount accDepositing = null;
						
						//choosing account to deposit money to
						while (needChoice) {
							try {
								System.out.println(userBankAccount.PrintPersonalAccs());
								System.out.println("Which personal account would you like to deposit money to?");
								accDepositingNum = scnr.nextInt();
								
								if (accDepositingNum < 1 || accDepositingNum > userBankAccount.GetPersonalAccounts().size()) {
									throw new Exception("Option selected for depositing account is invalid.");
								}
								else {
									accDepositing = userBankAccount.GetPersonalAccounts().get(accDepositingNum-1);
									needChoice = false;
								}
							}
							catch (Exception excpt) {
								System.out.println("ERROR: " + excpt.getMessage() + " Try again.\n\n\n");
							}
						}
						needChoice = true;
						
						//choosing amount of money to deposit
						while (needChoice) {
							try {
								System.out.println("How much money would you like to deposit?");
								double amountToDeposit = scnr.nextDouble();
								
								if (userBankAccount.IsAmountPositive(amountToDeposit) == false) {
									throw new Exception("Amount must be positive.");
								}
								else {
									System.out.println(userBankAccount.DepositMoneyTo(accDepositing, amountToDeposit));
									needChoice = false;
								}
							}
							catch (Exception excpt){
								System.out.println("ERROR: " + excpt.getMessage() + " Try again.\n\n\n");
							}
						}
						needChoice = true;
					}
					
					/*
					 * Banking App Simulation - Option 4 Selected (view past transactions)
					 */
					else if (userChoice == 4) {
						needChoice = true;
						int accViewingNum = 0;
						
						//choosing acc to view
						while (needChoice) {
							try {
								System.out.println(userBankAccount.PrintPersonalAccs());
								System.out.println("Which personal account would you like to view?");
								accViewingNum = scnr.nextInt();
								if (accViewingNum < 1 || accViewingNum > userBankAccount.GetPersonalAccounts().size()) {
									throw new Exception("Option selected for viewing account is invalid.");
								}
								else {
									PersonalAccount viewingAcc = userBankAccount.GetPersonalAccounts().get(accViewingNum - 1);
									viewingAcc.PrintAccInfo();
									needChoice = false;
								}
							}
							catch (Exception excpt) {
								System.out.println("ERROR: " + excpt.getMessage() + " Try again.\n\n\n");
							}
						}
						needChoice = true;
					}
					
					/*
					 * Banking App Simulation - Option 5 Selected (quit)
					 */
					else if (userChoice == 5) {
						System.out.println("Thank you for using Bank Of PollyWolly!");
						needChoice = false;
					}
				}
			}
			catch (Exception excpt) {
				System.out.println("ERROR: " + excpt.getMessage() + " Try again.\n\n\n");
			}
		}
	}
}


