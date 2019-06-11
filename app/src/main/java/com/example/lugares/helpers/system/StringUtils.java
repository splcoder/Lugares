package com.example.lugares.helpers.system;

import com.google.android.gms.maps.model.LatLng;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class StringUtils {
	public static String join( Set<String> words, String with ){
		StringBuilder sb = new StringBuilder();
		Iterator it = words.iterator();
		if( it.hasNext() ){
			sb.append( it.next() );
		}
		while( it.hasNext() ){
			sb.append( with ).append( it.next() );
		}
		return sb.toString();
	}
	public static String joinLatLng( List<LatLng> items, String with ){
		StringBuilder sb = new StringBuilder();
		Iterator it = items.iterator();
		LatLng latLng;
		if( it.hasNext() ){
			latLng = (LatLng)it.next();
			sb.append( String.valueOf( latLng.latitude ) ).append( ", " ).append( String.valueOf( latLng.longitude ) );
		}
		while( it.hasNext() ){
			latLng = (LatLng)it.next();
			sb.append( with ).append( String.valueOf( latLng.latitude ) ).append( ", " ).append( String.valueOf( latLng.longitude ) );
		}
		return sb.toString();
	}
	public static String join( List<Object> items, String with ){
		StringBuilder sb = new StringBuilder();
		Iterator it = items.iterator();
		if( it.hasNext() ){
			sb.append( String.valueOf( it.next() ) );
		}
		while( it.hasNext() ){
			sb.append( with ).append( String.valueOf( it.next() ) );
		}
		return sb.toString();
	}
}
