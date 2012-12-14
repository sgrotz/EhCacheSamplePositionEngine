package org.position.engine;

import java.util.Collection;
import java.util.Iterator;

import org.position.objects.position;
import org.position.objects.trade;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheEntry;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.writer.CacheWriter;
import net.sf.ehcache.writer.writebehind.operations.SingleOperationType;

public class calculatePositionAsWriteBehind implements CacheWriter {


	private static CacheManager manager;
	private static Cache cache;
	
	@Override
	public CacheWriter clone(Ehcache arg0) throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(CacheEntry arg0) throws CacheException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Collection<CacheEntry> arg0) throws CacheException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() throws CacheException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

		
	}

	@Override
	public void throwAway(Element arg0, SingleOperationType arg1,
			RuntimeException arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
public void write(Element e) throws CacheException {
		// TODO Auto-generated method stub
		
		//System.out.println("New Write event!!");
		
		trade trade = (trade) e.getValue();
		updatePosition(trade);
		
	}

	@Override
	public void writeAll(Collection<Element> ce) throws CacheException {
		// TODO Auto-generated method stub
		
		System.out.println("New Write ALL event!!");
		
		Iterator<Element> it = ce.iterator();
		System.out.println("Size is: " + ce.size());
		
		while (it.hasNext()) {
			Element e = it.next();
			trade trade = (trade) e.getObjectValue();
			
			updatePosition(trade);
			
		}
		
	}
	
 public static void updatePosition(trade newTrade)  {
	 
	 manager = CacheManager.newInstance("config/ehcache.xml");
	 cache = manager.getCache("bookOfPositions");
		
		String Stock = newTrade.getSTOCK();
		//System.out.println("Running write behind for " + Stock);
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
	}
	

}
