package org.position.engine;

import java.util.Properties;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

/**
 * @author sgrotz
 * PositionCalculationFactory is used to extend the cache to call this event listener each time a new trade is being
 * written to the cache. Configure the event listener in the ehcache.xml file.
 */

public class positionCalculationFactory extends CacheEventListenerFactory {


	@Override
	public CacheEventListener createCacheEventListener(Properties arg0) {
		// TODO Auto-generated method stub
		
		return new positionCalculationByTrade();
	}

}
