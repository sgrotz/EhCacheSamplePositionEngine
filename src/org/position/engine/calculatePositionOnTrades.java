package org.position.engine;

import org.position.objects.trade;
import org.position.objects.position;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

public class calculatePositionOnTrades implements CacheEventListener, Cloneable {

	
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
		// System.out.println("Trade " + element.getKey() + " wurde eingefügt ... " + trade.getBUYSELL() + " " + trade.getQUANTITY() + " " + trade.getSTOCK());
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
	
	
	public static void updatePosition(trade newTrade)  {
		 
		 manager = CacheManager.newInstance("config/ehcache.xml");
		 cache = manager.getCache("bookOfPositions");
			
			String Stock = newTrade.getSTOCK();
			//System.out.println("Running write behind for " + Stock);
			cache.acquireWriteLockOnKey(Stock);
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
			cache.releaseWriteLockOnKey(Stock);
			
		}


}
