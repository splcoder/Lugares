package com.example.lugares.helpers.system;

import java.util.HashMap;
import java.util.Map;

public class Cache {
	private static Cache _cache = null;
	// Internal Cache var
	private Map<String, Object> mapCache = new HashMap<String, Object>();
	// Constructor (private)
	private Cache(){}
	/**
	 * Access for direct Cache's values/functions (not mapCache)
	 * @return Cache
	 */
	public static synchronized Cache getCache(){
		if( _cache == null ){
			_cache = new Cache();
		}
		return _cache;
	}
	/**
	 * Cache map set
	 * @param key
	 * @param value
	 */
	public static void set( String key, Object value ){
		getCache().mapCache.put( key, value );
	}
	/**
	 * Cache map access
	 * @param key
	 * @return Object
	 */
	public static Object get( String key ){
		return getCache().mapCache.get( key );
	}
	/**
	 * Cache map get and delete entry
	 * @param key
	 * @return Object
	 */
	public static Object del( String key ){
		return getCache().mapCache.remove( key );
	}

	//----------------------------------------------------------------------------------------------
	// Another vars/functions
	//----------------------------------------------------------------------------------------------

	/**
	 * Get a boolean (basic type)
	 * @param key
	 * @param default_value
	 * @return
	 */
	public static boolean getBoolean( String key, boolean default_value ){
		try{
			default_value = (boolean)getCache().mapCache.get( key );
		} catch( Exception e ){}
		return default_value;
	}
	public static boolean delBoolean( String key, boolean default_value ){
		try{
			default_value = (boolean)getCache().mapCache.remove( key );
		} catch( Exception e ){}
		return default_value;
	}

	/**
	 * Get a long (basic type)
	 * @param key
	 * @param default_value
	 * @return
	 */
	public static long getLong( String key, long default_value ){
		try{
			default_value = (long)getCache().mapCache.get( key );
		} catch( Exception e ){}
		return default_value;
	}
	public static long delLong( String key, long default_value ){
		try{
			default_value = (long)getCache().mapCache.remove( key );
		} catch( Exception e ){}
		return default_value;
	}

	/**
	 * Get a double (basic type)
	 * @param key
	 * @param default_value
	 * @return
	 */
	public static double getDouble( String key, double default_value ){
		try{
			default_value = (double)getCache().mapCache.get( key );
		} catch( Exception e ){}
		return default_value;
	}
	public static double delDouble( String key, double default_value ){
		try{
			default_value = (double)getCache().mapCache.remove( key );
		} catch( Exception e ){}
		return default_value;
	}
}
