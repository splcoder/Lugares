package com.example.lugares.helpers.db;

import android.text.format.DateFormat;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * For managing the "type" in switch statements
 *
 * Note that the SQLite database only has the following types:
 * 		NULL, INTEGER (1-8 bytes), REAL (8 bytes), TEXT, BLOB
 */
public class SqlType {
	private Object value;
	private int type;
	static final char SPECIAL_CHAR_INIT = '#';
	static final char SPECIAL_CHAR_END = '*';	// "The dot product"
	// To not use SqlType.rawStringToSql
	static final int FIXED_STRING = -1024;		// Special for internal purpose (so known), example: set an int[] as String (separated by ',')

	public SqlType( Object value, int type ){
		this.value = value;
		this.type = type;
	}
	//----------------------------------------------------------------------------------------------
	// Do not use the getter and setter
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	//----------------------------------------------------------------------------------------------
	// Create all the types
	public static SqlType getNull(){
		return new SqlType( null, Types.NULL );
	}
	public static SqlType getBoolean( boolean value ){
		return new SqlType( value, Types.BOOLEAN );
	}
	public static SqlType getByte( byte value ){
		return new SqlType( value, Types.TINYINT );
	}
	public static SqlType getShort( short value ){
		return new SqlType( value, Types.SMALLINT );
	}
	public static SqlType getInt( int value ){
		return new SqlType( value, Types.INTEGER );
	}
	public static SqlType getLong( long value ){
		return new SqlType( value, Types.BIGINT );
	}
	public static SqlType getFloat( float value ){
		return new SqlType( value, Types.FLOAT );
	}
	public static SqlType getDouble( double value ){
		return new SqlType( value, Types.DOUBLE );
	}
	public static SqlType getChar( char value ){
		return new SqlType( value, Types.CHAR );
	}
	public static SqlType getFixedString( String value ){
		return new SqlType( value, FIXED_STRING );
	}
	public static SqlType getString( String value ){
		return new SqlType( value, Types.VARCHAR );
	}
	public static SqlType getText( String value ){
		return new SqlType( value, Types.LONGVARCHAR );
	}
	public static SqlType getByteArray( byte[] value ){
		return new SqlType( value, Types.BLOB );
	}
	//----------------------------------------------------------------------------------------------
	public static String intDateToString( long date ){
		return DateFormat.format( "yyyy-MM-dd HH:mm:ss", new Date( date ) ).toString();
	}
	//----------------------------------------------------------------------------------------------
	/**
	 * Use only normal characters: 0-9, @, A-Z, a-z
	 * @param value
	 * @param returnInTildes
	 * @return
	 */
	public static String rawStringToSql( String value, boolean returnInTildes ){
		StringBuilder sb = new StringBuilder();
		int size = value.length();
		int ascii;
		for( int i = 0; i < size; i++ ){
			ascii = (int)value.codePointAt( i );
			if( (48 <= ascii && ascii <= 57) || (64 <= ascii && ascii <= 90) || (97 <= ascii && ascii <= 122) )
				sb.appendCodePoint( ascii );
			else	sb.append( SPECIAL_CHAR_INIT ).append( ascii ).append( SPECIAL_CHAR_END );
		}
		if( returnInTildes ){
			sb.insert( 0, "'" );
			sb.append( "'" );
		}
		return sb.toString();
	}
	public static String rawStringToSql( String value ){
		return rawStringToSql( value, false );
	}
	public static String sqlStringToRaw( String value ){
		StringBuilder sb = new StringBuilder();
		int size = value.length();
		int ascii;
		char ch;
		for( int i = 0; i < size; i++ ){
			if( (ch = value.charAt( i )) == SPECIAL_CHAR_INIT ){
				ascii = 0;
				while( (ch = value.charAt( ++i )) != SPECIAL_CHAR_END )
					ascii = ascii * 10 + ((int)ch - 48);
				sb.appendCodePoint( ascii );
			}
			else	sb.append( ch );
		}
		return sb.toString();
	}
	//----------------------------------------------------------------------------------------------
	public static String implode( String[] aValues, String separator, int start ){
		if( start < 0 || start >= aValues.length )	return "";
		StringBuilder sb = new StringBuilder();
		sb.append( aValues[ start ] );
		while( ++start < aValues.length ){
			sb.append( separator );
			sb.append( aValues[ start ] );
		}
		return sb.toString();
	}
	//----------------------------------------------------------------------------------------------
	public static String intArrayToString( int[] values ){
		StringBuilder sb = new StringBuilder();
		if( values.length > 0 ){
			sb.append( values[ 0 ] );
			for( int i = 1; i < values.length; i++ ){
				sb.append( "," ).append( values[ i ] );
			}
		}
		return sb.toString();
	}
	public static int[] stringIntegersToIntArray( String strIntegers ){
		List<Integer> aValues = new ArrayList<Integer>();
		StringBuilder sb = new StringBuilder();
		int size = strIntegers.length();
		int ascii;
		for( int i = 0; i < size; i++ ){
			ascii = (int)strIntegers.codePointAt( i );
			if( 48 <= ascii && ascii <= 57 ){
				sb.appendCodePoint( ascii );
			}
			else{
				aValues.add( Integer.parseInt( sb.toString() ) );
				//sb.delete( 0, sb.length() - 1 );	<<< gives error !!!
				sb.setLength( 0 );
			}
		}
		if( size > 0 ){
			aValues.add( Integer.parseInt( sb.toString() ) );	// Last
			int[] aTrack = new int[ aValues.size() ];
			for( int i = 0; i < aValues.size(); i++ ){
				aTrack[ i ] = aValues.get( i );
			}
			return aTrack;
		}
		return null;
	}
}
