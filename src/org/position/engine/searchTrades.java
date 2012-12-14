package org.position.engine;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Results;

/**
 * @author sgrotz
 * Added a new search class to allow users to search across the cache. 
 * 
 */

public class searchTrades {


	
	/**
	 * @return
	 * Return the average search time ... (time it takes to search for something)
	 */
	public static long getAverageSearchTime() {
		CacheManager manager = CacheManager.newInstance("config/ehcache.xml");
		Cache cache = manager.getCache("trades");	
		
		return cache.getAverageSearchTime();
	}
	
	/**
	 * @return
	 * return the average get time (time it takes to get an object from the cache)
	 */
	public static float getAverageGetTime() {
		CacheManager manager = CacheManager.newInstance("config/ehcache.xml");
		Cache cache = manager.getCache("trades");	
		
		System.out.println(cache.getStatistics());
		return cache.getAverageGetTime();
		
	}
	
	/**
	 * @return
	 * Return all cache statistics
	 */
	public static String getStatistics() {
		CacheManager manager = CacheManager.newInstance("config/ehcache.xml");
		Cache cache = manager.getCache("trades");	
		
		return cache.getStatistics().toString();		
	}
	
	
	
	
	/**
	 * @param CacheName
	 * @return
	 * Returns the size of the cache (element count)
	 */
	public static int getCacheSize(String CacheName) {
		CacheManager manager = CacheManager.newInstance("config/ehcache.xml");
		Cache cache = manager.getCache(CacheName);
		
		return cache.getSize();
	}
	
	/**
	 * @param args
	 * Returns the number of total trades in the trades cacehe for a particular stock and direction
	 */
	
	public static int getTotalTrades(String Stock, String BUYSELL) {
		// TODO Auto-generated method stub

		CacheManager manager = CacheManager.newInstance("config/ehcache.xml");
		Cache cache = manager.getCache("trades");

		Attribute<String> STOCK = cache.getSearchAttribute("STOCK");
		Attribute<String> BS = cache.getSearchAttribute("BUYSELL");
		
		while (true) {
			Results results = cache
					.createQuery()
					.includeKeys()
					//.includeValues()
					.addCriteria(STOCK.eq(Stock)
							.and(BS.eq(BUYSELL))
							)
							.execute();

			// If you want to inspect the results, uncomment the following lines
			//List<Result> keys = results.all();

			//for (int i = 0; i < results.size(); i++) {
			//	System.out.println("Customer found: " + keys.get(i));
			//}
			
			// System.out.println("Records found: " + results.toString());
			
			return results.size();
		}

	}

}
