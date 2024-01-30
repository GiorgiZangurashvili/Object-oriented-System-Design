// Cracker.java
/*
 Generates SHA hashes of short strings in parallel.
*/

import sun.plugin2.message.Message;

import java.security.*;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();
	private static CountDownLatch latch;
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}

	public Cracker(int numWorkers, int maxLength, byte[] hashByte){
		int space = CHARS.length / numWorkers;

		for(int i = 0; i < numWorkers; i++){
			Worker worker;
			if(i != numWorkers - 1){
				worker = new Worker(i * space, (i + 1) * space, maxLength, hashByte);
			}else{
				worker = new Worker(i * space, CHARS.length, maxLength, hashByte);
			}
			worker.start();
		}
	}

	private static byte[] digestText(String text){
		try{
			MessageDigest messageDigest = MessageDigest.getInstance("SHA");
			messageDigest.update(text.getBytes());
			return messageDigest.digest();
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws InterruptedException {
		if (args.length < 1) {
			System.out.println("Args: target length [workers]");
			System.exit(1);
		}

		// args: targ len [num]
		String targ = args[0];
		if(args.length == 1){
			System.out.println(hexToString(digestText(targ)));
			return;
		}
		int len = Integer.parseInt(args[1]);
		int numWorkers = 1;
		if (args.length > 2) {
			numWorkers = Integer.parseInt(args[2]);
		}
		// a! 34800e15707fae815d7c90d49de44aca97e2d759
		// xyz 66b27417d37e024c46526c2f6d358a754fc552f3

		latch = new CountDownLatch(numWorkers);

		Cracker c = new Cracker(numWorkers, len, hexToArray(targ));

		latch.await();

		System.out.println("all done");
	}

	private class Worker extends Thread{
		private int startIndex;
		private int endIndex;
		private int maxLength;
		private byte[] hash;

		public Worker(int startIndex, int endIndex, int maxLength, byte[] hash){
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.maxLength = maxLength;
			this.hash = hash;
		}

		@Override
		public void run(){
			for(int i = startIndex; i < endIndex; i++){
				String possiblePass = "" + CHARS[i];
				searchPass(possiblePass);
			}
			latch.countDown();
		}

		private void searchPass(String possiblePass){
			if(possiblePass.length() > this.maxLength) return;

			byte[] passHash = digestText(possiblePass);
			if(Arrays.equals(passHash, hash)){
				System.out.println(possiblePass);
			}else{
				if(possiblePass.length() == this.maxLength) return;

				for (int i = 0; i < CHARS.length; i++) {
					searchPass(possiblePass + CHARS[i]);
				}
			}
		}
	}
}