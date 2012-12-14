package org.position;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.writer.CacheWriterManager;

import org.position.objects.trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;
import org.terracotta.ehcachedx.org.mortbay.log.Log;


/**
 * @author sgrotz
 * Sample class to use multiple threads to create trades
 * In this sample, we fire multiple threads each with x trades against the Terracotta Server, which takes care
 * of the position calculation...
 */

public class threadedTradeProducer {

	// Define amount of threads to be used
	private static int threadCount = 33;
	
	// Define how many Trades per Thread should be created
	private static int tradesPerThread = 5000;
	
	// Define a delay between sending the trades
	private static long delay = 0;
	
	// Should the trades also get persisted to a database? - Default: false
	private static boolean useWriter = false;
	
	private static CacheManager manager;
	private static Cache cache;
	
	public static void main(String[] args) {
		
		// Get Start Time
		long startTime = System.currentTimeMillis();
		
		// Initialize the cachemanager
		manager = CacheManager.newInstance("config/ehcache.xml");
		cache = manager.getCache("trades");
		
		// Call the main method
		createTradesInThreads();
		
		// Get the end time and calculate the processing time. 
		long endTime = System.currentTimeMillis();
		long timeToProcess = endTime - startTime;
		int total = threadCount * tradesPerThread;
		
		// Print the processing time ...
		System.out.println("Executed " + threadCount + 
				" threads, each thread processing " + tradesPerThread + 
				" trades (total: "+ total + " trades), in " + timeToProcess + 
				"ms");

	}

	
	/**
	 * This is the main method, creating the threads, which in turn create the trades
	 */

	public static void createTradesInThreads() {
		
		Thread[] threads = new Thread[threadCount];
		for (int i = 0; i < threads.length; i++) {

			final int current = i;
			threads[i] = new Thread() {

				public void run() {
					executeLoad();
				}
			};
			threads[i].start();
		}
		
		for (int i = 0; i < threads.length; i++) {
			
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	/**
	 * The executeLoad Method, is executed from each thread
	 */
	
	public static void executeLoad() {
		
		for (int i = 0; i < tradesPerThread; i++) {
			
			trade trade = tradeFactory.createRandomTrade();
			
			// write the trade to the cache
			writeTradeToCache(trade);
			
			try {
				// Wait for x milliseconds and run again :)
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	public static void writeTradeToCache(trade trade)  {
		
		// In case you want to see what trades you process ...
		//System.out.println("New " + trade.getBUYSELL() + 
		//		" trade received ... " + trade.getID() + 
		//		" - Stock: " + trade.getSTOCK() +
		//		" - Quantity: " + trade.getQUANTITY() +
		//		"@" + trade.getPRICE());
		
		
		// Create a new element 
		Element element = new Element(trade.getID(), trade);
		
		// if a writer is configured, use cache.putWithWriter - otherwise cache.put
		if (useWriter) {
			cache.putWithWriter(element);			
		} else {	
			cache.put(element);
		}
	}
	

}
