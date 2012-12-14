package org.position.engine;

import java.util.Properties;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.writer.CacheWriter;
import net.sf.ehcache.writer.CacheWriterFactory;

/**
 * @author sgrotz
 * calculatePositionWriteBehindFactory is used to extend the cache to use write behind or write through. 
 * Make sure to point the configuration in your ehcache.xml to the proper class :)
 */


public class calculatePositionWriteBehindFactory extends CacheWriterFactory{
	@Override
	public CacheWriter createCacheWriter(Ehcache arg0, Properties arg1) {
		// TODO Auto-generated method stub
		return new calculatePositionAsWriteBehind();
	}

}
