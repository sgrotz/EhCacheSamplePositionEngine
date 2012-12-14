package org.position.engine;

import org.position.objects.trade;
import org.position.objects.position;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

/**
 * @author sgrotz
 * The following class is used by the event listener to calculate the position on the fly. Make sure this
 * class is cloneable. Take a look at the explicit locking used at the bottom. 
 * 
 */

public class positionCalculationByTrade implements CacheEventListener, Cloneable {

	
	private static CacheManager manager;
	private static Cache cache;
	
	/**
	 * @param args
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyElementEvicted(Ehcache arg0, Element arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyElementExpired(Ehcache arg0, Element arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyElementPut(Ehcache arg0, Element element)
			throws CacheException {
		
		trade trade = (trade) element.getObjectValue();
		// System.out.println("Trade " + element.getKey() + " wurde eingef?gt ... " + trade.getBUYSELL() + " " + trade.getQUANTITY() + " " + trade.getSTOCK());
		updatePosition(trade);
		
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyElementRemoved(Ehcache arg0, Element arg1)
			throws CacheException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyElementUpdated(Ehcache arg0, Element arg1)
			throws CacheException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyRemoveAll(Ehcache arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	
	/**
	 * @param newTrade
	 * This is where the party happens ... 
	 * In this sample, we also use explicit locking - This will avoid race conditions and make sure one value can only get locked from 
	 * a single trade...
	 */
	public static void updatePosition(trade newTrade)  {
		 
		 manager = CacheManager.newInstance("config/ehcache.xml");
		 cache = manager.getCache("bookOfPositions");
			
			String Stock = newTrade.getSTOCK();

			// Acquire a write lock on this particular stock
			cache.acquireWriteLockOnKey(Stock);
			
			// Get the current stock reading
			Element pos = cache.get(Stock);

			
			if (pos != null)  {
				position existingPosition = (position) pos.getObjectValue();
			
				//System.out.println("Old Position for Stock: " + Stock + " is: " +existingPosition.getQUANTITY() + " (Total Exposure: " + existingPosition.getEXPOSURE() +")");

				int newQuantity = 0;
				Double newExposure = 0.0;
				
				if (newTrade.getBUYSELL() == "BUY") {
					// if it is a buy - increase Quantity
					newQuantity = existingPosition.getQUANTITY() + newTrade.getQUANTITY();
					newExposure = existingPosition.getEXPOSURE() + (newTrade.getQUANTITY() * newTrade.getPRICE());
					//System.out.println("Add");
					
				} else {
					newQuantity = existingPosition.getQUANTITY() - newTrade.getQUANTITY();
					newExposure = existingPosition.getEXPOSURE() - (newTrade.getQUANTITY() * newTrade.getPRICE());
					//System.out.println("Buy");
				}
				
				//System.out.println("Position for Stock: " + Stock + " is: " +newQuantity + " (Total Exposure: " + newExposure +")");
				
				existingPosition.setQUANTITY(newQuantity);
				existingPosition.setEXPOSURE(newExposure);
				
				Element newpos = new Element(Stock, existingPosition);
				//System.out.println(newpos + " " + existingPosition.getQUANTITY());
				
				// Either Put or Replace will work - but take a look at the documentation how they differ!
				//cache.put(newpos);
				cache.replace(newpos);
			
			} else {
				
				//System.out.println("Stock not found in position list: " + Stock + " " + newTrade.getQUANTITY() + " " + newTrade.getPRICE());
				
				position newPosition = new position();
				
				newPosition.setSTOCK(Stock);
				newPosition.setQUANTITY(newTrade.getQUANTITY());
				newPosition.setEXPOSURE(newTrade.getPRICE() * newTrade.getQUANTITY());
				newPosition.setCURRENCY(newTrade.getCCY());
				
				Element newElement = new Element(Stock, newPosition);
				//System.out.println("Position will be updated: " + Stock + " " + newPosition.getQUANTITY() + " " + newPosition.getEXPOSURE());
				//System.out.println(newpos);
				cache.put(newElement);
				
			} 
			
			// Very important - always make sure to release the LOCK. This should actually happen in the finally clause, but hey ... this is a sample ;)
			cache.releaseWriteLockOnKey(Stock);
		
		}
}
