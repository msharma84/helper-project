package com;

import java.util.Random;

public class ExponentialBackoffRetries {
	
	private int numOfRetries;
	
	private int numOfRetriesLeft;
	
	private long defaultTimeToWait;
	
	private long timeToWait;
	
	private final Random random = new Random();

	// initialize all the values only using number of retries and default wait time  
	public ExponentialBackoffRetries(int numOfRetries, long defaultTimeToWait) {
		this.numOfRetries = numOfRetries;
		this.numOfRetriesLeft = numOfRetries;
		this.defaultTimeToWait = defaultTimeToWait;
		this.timeToWait = defaultTimeToWait;
	}

	// reset the values in case of further retries required, but need to handle with
	// care as might results in never ending loop
	public void reset() {
		this.numOfRetriesLeft = numOfRetries;
		this.timeToWait = defaultTimeToWait;
	}
	
	// Retry only when maximum retries doesn't exceed
	public boolean shouldRetry() {
		return numOfRetriesLeft >= 0;
	}
	
	public void retry() {
		
		System.out.println("*** RETRY STARTED ***");
		numOfRetriesLeft --;
		if(!shouldRetry()) {
			System.out.println("*** RETRY FAILED - LIMIT EXCEEDS ***");
			return ;
		}
		// Wait for next attempt
		waitTillNextRetry();
		// increase wait time exponentially
		timeToWait += random.nextInt(1000);
		System.out.println("WAIT TIME - "+timeToWait);
	}
	
	public void waitTillNextRetry() {
		
		try {
			Thread.sleep(timeToWait);
		}catch(InterruptedException e) {
			System.err.println(e.getMessage());
			Thread.currentThread().interrupt();
		}
	}
	
	public void doNotRetry() {
		numOfRetriesLeft = 0;
	}
	
	public int getNumOfRetries() {
		return numOfRetries;
	}

	public int getNumOfRetriesLeft() {
		return numOfRetriesLeft;
	}

	public long getDefaultTimeToWait() {
		return defaultTimeToWait;
	}

	public long getTimeToWait() {
		return timeToWait;
	}

	public static void main(String[] args) {
		
		ExponentialBackoffRetries expr = new ExponentialBackoffRetries(5, 10);
		
		while(expr.shouldRetry()) {
			/*
			 * if(expr.getNumOfRetriesLeft() ==1) { expr.reset(); }
			 */
			// we are checking the failed condition only
			// this code should be in the failed condition to trigger exponentially
			expr.retry();
		}
	}

}
