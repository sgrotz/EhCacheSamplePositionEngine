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

public class threadedTradeProducer {

	private static int threadCount = 33;
	private static int tradesPerThread = 5000;
	private static long delay = 0;
	private static boolean useWriter = false;
	
	private static CacheManager manager;
	private static Cache cache;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		long startTime = System.currentTimeMillis();
		
		manager = CacheManager.newInstance("config/ehcache.xml");
		cache = manager.getCache("trades");
		
		createTradesInThreads();
		
		long endTime = System.currentTimeMillis();
		long timeToProcess = endTime - startTime;
		int total = threadCount * tradesPerThread;
		//Double avgPerTrade = Double.valueOf(timeToProcess / total);
		
		System.out.println("Executed " + threadCount + 
				" threads, each thread processing " + tradesPerThread + 
				" trades (total: "+ total + " trades), in " + timeToProcess + 
				"ms");
		
		
			
	}

	
	
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
	
	
	public static void executeLoad() {
		
		for (int i = 0; i < tradesPerThread; i++) {
			
			trade trade = tradeFactory.createRandomTrade();
			
			writeTradeToCache(trade);
			
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	public static void writeTradeToCache(trade trade)  {
		
		//System.out.println("New " + trade.getBUYSELL() + 
		//		" trade received ... " + trade.getID() + 
		//		" - Stock: " + trade.getSTOCK() +
		//		" - Quantity: " + trade.getQUANTITY() +
		//		"@" + trade.getPRICE());
		
		Element element = new Element(trade.getID(), trade);
		

		if (useWriter) {
			cache.putWithWriter(element);			
		} else {	
			cache.put(element);
		}
	}
	

}
