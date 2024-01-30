// Bank.java

/*
 Creates a bunch of accounts and uses threads
 to post transactions to the accounts concurrently.
*/

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Bank {
	public static final int ACCOUNTS = 20;	 // number of accounts
	private static final int BALANCE = 1000;	//initial balance of accounts
	private static final int CAPACITY = 50;		//capacity of ArrayBlockingQueue
	private Transaction nullTrans = new Transaction(-1, 0, 0);	//null transaction which terminates the work of a single thread
	private static Account[] accounts;
	private ArrayBlockingQueue<Transaction> transQ;
	private CountDownLatch latch;

	/*
	 Reads transaction data (from/to/amt) from a file for processing.
	 (provided code)
	 */
	public void readFile(String file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			// Use stream tokenizer to get successive words from file
			StreamTokenizer tokenizer = new StreamTokenizer(reader);
			
			while (true) {
				int read = tokenizer.nextToken();
				if (read == StreamTokenizer.TT_EOF) break;  // detect EOF
				int from = (int)tokenizer.nval;
				
				tokenizer.nextToken();
				int to = (int)tokenizer.nval;
				
				tokenizer.nextToken();
				int amount = (int)tokenizer.nval;

				transQ.put(new Transaction(from, to, amount));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/*
	 Processes one file of transaction data
	 -fork off workers
	 -read file into the buffer
	 -wait for the workers to finish
	*/
	public void processFile(String file, int numWorkers) throws InterruptedException {
		readFile(file);

		putNullTransInQueue(numWorkers);

		latch.await();
	}

	private void putNullTransInQueue(int numWorkers) throws InterruptedException {
		for(int i = 0; i < numWorkers; i++){
			transQ.put(nullTrans);
		}
	}

	private static void printOutput(){
		for(int i = 0; i < ACCOUNTS; i++){
			System.out.println(accounts[i].toString());
		}
	}

	public Bank(int numWorkers){
		variablesInit(numWorkers);
		fillAccountsArr();
		startWorkers(numWorkers);
	}

	private void variablesInit(int numWorkers){
		transQ = new ArrayBlockingQueue<>(CAPACITY);
		latch = new CountDownLatch(numWorkers);
		accounts = new Account[ACCOUNTS];
	}

	private void fillAccountsArr(){
		for(int i = 0; i < ACCOUNTS; i++){
			accounts[i] = new Account(this, i, BALANCE);
		}
	}

	private void startWorkers(int numWorkers){
		for(int i = 0; i < numWorkers; i++){
			Worker worker = new Worker();
			worker.start();
		}
	}
	
	/*
	 Looks at commandline args and calls Bank processing.
	*/
	public static void main(String[] args) throws InterruptedException {
		// deal with command-lines args
		if (args.length == 0) {
			System.out.println("Args: transaction-file [num-workers [limit]]");
			System.exit(1);
		}
		
		String file = args[0];
		
		int numWorkers = 1;
		if (args.length >= 2) {
			numWorkers = Integer.parseInt(args[1]);
		}

		Bank b = new Bank(numWorkers);

		b.processFile(file, numWorkers);

		printOutput();
	}

	private class Worker extends Thread{
		@Override
		public void run(){
			while(true){
				try {
					Transaction t = transQ.take();
					if(t == nullTrans){
						latch.countDown();
						break;
					}
					accounts[t.from].withdraw(t.amount);
					accounts[t.to].deposit(t.amount);
				} catch (InterruptedException ignored) {

				}
			}
		}
	}
}

