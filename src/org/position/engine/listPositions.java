package org.position.engine;

import java.util.Iterator;
import java.util.List;

import org.position.objects.position;
import org.position.engine.searchTrades;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class listPositions {
	
	
	private static CacheManager manager;
	private static Cache cache;

	private static int counter;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		counter = 0;
		int previousCount = 0; 

		manager = CacheManager.newInstance("config/ehcache.xml");
		cache = manager.getCache("bookOfPositions");
		
		while (true) {
			List keys = cache.getKeys();
			Iterator it = keys.iterator();
			
			int totalCount = searchTrades.getCacheSize("trades");
			if (previousCount == 0) {
				previousCount = totalCount;
			} 
			int Tps;
			
			System.out.println(" *** NEW POSITION READING *** Total Trades: " + totalCount + " ***" );
			while (it.hasNext()) {
				
				Element e = null;

					 e = cache.get(it.next());
					 //System.out.println(e);searchTrades.getTotalTrades();
				
					 position pos = (position) e.getValue();

					 System.out.println("STOCK: " + pos.getSTOCK() +
							 " - Exposure: " + convertExposure(pos.getEXPOSURE()) +
							 " " + pos.getCURRENCY() +
							 " - Quantity: " + pos.getQUANTITY() +
							 " (B:" + searchTrades.getTotalTrades(pos.getSTOCK(), "BUY") +
							 "/S:" + searchTrades.getTotalTrades(pos.getSTOCK(), "SELL") +
							 ")"
							 );


			}
			//System.out.println("SYSTEM STATISTICS:");
			// System.out.println(searchTrades.getStatistics());
			if (totalCount == previousCount) {
				Tps = 0;
			} else {
				Tps = totalCount - previousCount;
				previousCount = totalCount;
			}

			System.out.println(" *** Trades per second: " + Tps);
			System.out.println(" *** ************************************************* *** ");
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public static String convertExposure (double exposure) {
		
		String exposureString = String.valueOf(exposure);
		int end = exposureString.length() - 10;
		exposureString.substring(1, end);
		
		return exposureString;
		
	}

}
