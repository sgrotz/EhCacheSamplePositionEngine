package org.position.engine;

import java.util.Properties;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

public class positionCalculationFactory extends CacheEventListenerFactory {


	@Override
	public CacheEventListener createCacheEventListener(Properties arg0) {
		// TODO Auto-generated method stub
		
		return new calculatePositionOnTrades();
	}

}
