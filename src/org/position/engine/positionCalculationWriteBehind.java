package org.position.engine;

import java.util.Properties;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.writer.CacheWriter;
import net.sf.ehcache.writer.CacheWriterFactory;

public class positionCalculationWriteBehind extends CacheWriterFactory{


	@Override
	public CacheWriter createCacheWriter(Ehcache arg0, Properties arg1) {
		// TODO Auto-generated method stub
		return new calculatePositionAsWriteBehind();
	}

}
