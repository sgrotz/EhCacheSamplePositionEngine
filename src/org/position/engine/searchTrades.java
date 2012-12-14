package org.position.engine;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Results;

public class searchTrades {


	
	public static long getAverageSearchTime() {
		CacheManager manager = CacheManager.newInstance("config/ehcache.xml");
		Cache cache = manager.getCache("trades");	
		
		return cache.getAverageSearchTime();
	}
	
	public static float getAverageGetTime() {
		CacheManager manager = CacheManager.newInstance("config/ehcache.xml");
		Cache cache = manager.getCache("trades");	
		
		System.out.println(cache.getStatistics());
		return cache.getAverageGetTime();
		
	}
	
	public static String getStatistics() {
		CacheManager manager = CacheManager.newInstance("config/ehcache.xml");
		Cache cache = manager.getCache("trades");	
		
		return cache.getStatistics().toString();		
	}
	
	
	
	
	public static int getCacheSize(String CacheName) {
		CacheManager manager = CacheManager.newInstance("config/ehcache.xml");
		Cache cache = manager.getCache(CacheName);
		
		return cache.getSize();
	}
	
	/**
	 * @param args
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

			//List<Result> keys = results.all();

			//for (int i = 0; i < results.size(); i++) {
			//	System.out.println("Customer found: " + keys.get(i));
			//}
			
			// System.out.println("Records found: " + results.toString());
			
			return results.size();
		}

	}

}
