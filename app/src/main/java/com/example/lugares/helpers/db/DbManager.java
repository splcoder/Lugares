package com.example.lugares.helpers.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Types;

public class DbManager {
	// For logging
	private static final String TAG = "DbManager";
	// The DB
	private String msDatabaseName = "generic_db";
	private static final int DATABASE_VERSION = 1;
	private String[] masDatabaseTables;			// DB's tables names
	private String[] masDatabaseCreateTables;	// SQLs for creating each table of the DB

	private class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper( Context context ){
			super( context, msDatabaseName, null, DATABASE_VERSION );
		}
		@Override
		public void onCreate( SQLiteDatabase db ){
			Log.i( DbManager.TAG, "Creating the database's tables ..." );
			try{
				transaction( masDatabaseCreateTables, db );
				Log.i( DbManager.TAG, "... creation elapsed: " + getElapsed() + " ns" );
			} catch( SQLException e ){
				e.printStackTrace();
				// Must crash !!! -> the sql queries must have NO error
				throw new SQLException( "Error in the creation of the database's tables -> " + e.getMessage() );
			}
		}
		@Override
		public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ){	// increment the version in +1
			Log.w( DbManager.TAG, "Upgrading database version from " + oldVersion + " to " + newVersion );	// destruye toda la db antigua
			for( String s : masDatabaseTables )
				db.execSQL( "DROP TABLE IF EXISTS " + s );
			onCreate( db );
		}
	}

	// SQLite
	private DatabaseHelper mDBHelper;
	private SQLiteDatabase mDb;
	// Context
	private Context mCtx;

	//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	public DbManager( Context ctx, String[] databaseTables, String[] databaseCreateTables ){
		this.mCtx = ctx;
		this.masDatabaseTables = databaseTables;
		this.masDatabaseCreateTables = databaseCreateTables;
	}
	public DbManager( Context ctx, String[] databaseTables, String[] databaseCreateTables, String databaseName ){
		this.mCtx = ctx;
		this.masDatabaseTables = databaseTables;
		this.masDatabaseCreateTables = databaseCreateTables;
		this.msDatabaseName = databaseName;
	}

	/**
	 * Open db: set onCreate
	 * @return
	 * @throws SQLException
	 */
	public DbManager open() throws SQLException {
		mDBHelper = new DatabaseHelper( mCtx );
		mDb = mDBHelper.getWritableDatabase();
		return this;
	}

	/**
	 * Close db: set onDestroy
	 */
	public void close(){
		if( mDBHelper != null )
			mDBHelper.close();
	}
	//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// CRUD functions
	//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// Insert: returns the id or -1 on error
	public long insert( String table, String[] asColumns, SqlType[] aValues ){
		if( asColumns.length != aValues.length )	throw new SQLException( "insert function: <columns and values> arrays have distinct length" );
		ContentValues values = new ContentValues();
		for( int i = 0; i < asColumns.length; i++ ){
			switch( aValues[ i ].getType() ){
				case Types.NULL:			values.putNull( asColumns[ i ] );															break;
				//case Types.BOOLEAN:		values.put( asColumns[ i ], (boolean)aValues[ i ].getValue() );							break;
				// SQLite doesn't support boolean
				case Types.BOOLEAN:			values.put( asColumns[ i ], (int)((boolean)aValues[ i ].getValue() ? 1 : 0) );				break;
				case Types.TINYINT:			values.put( asColumns[ i ], (byte)aValues[ i ].getValue() );								break;
				case Types.SMALLINT:		values.put( asColumns[ i ], (short)aValues[ i ].getValue() );								break;
				case Types.INTEGER:			values.put( asColumns[ i ], (int)aValues[ i ].getValue() );									break;
				case Types.BIGINT:			values.put( asColumns[ i ], (long)aValues[ i ].getValue() );								break;
				case Types.FLOAT:			values.put( asColumns[ i ], (float)aValues[ i ].getValue() );								break;
				case Types.DOUBLE:			values.put( asColumns[ i ], (double)aValues[ i ].getValue() );								break;
				// Text
				case Types.CHAR:
				case Types.VARCHAR:
				case Types.LONGVARCHAR:	//	values.put( asColumns[ i ], (String)aValues[ i ].getValue() );							break;
					values.put( asColumns[ i ], SqlType.rawStringToSql( (String)aValues[ i ].getValue() ) );	break;
				case SqlType.FIXED_STRING:	values.put( asColumns[ i ], (String)aValues[ i ].getValue() );								break;
				case Types.BLOB:			values.put( asColumns[ i ], (byte[])aValues[ i ].getValue() );								break;
				default: throw new RuntimeException( TAG + " -> insert function: Type NOT supported" );
			}
		}
		return mDb.insert( table, null, values );
	}
	// Update
	public boolean update( String table, String sColId, long id, String[] asColumns, SqlType[] aValues ){
		if( asColumns.length != aValues.length )	throw new SQLException( "update function: <columns and values> arrays have distinct length" );
		ContentValues values = new ContentValues();
		for( int i = 0; i < asColumns.length; i++ ){
			switch( aValues[ i ].getType() ){
				case Types.NULL:			values.putNull( asColumns[ i ] );															break;
				//case Types.BOOLEAN:		values.put( asColumns[ i ], (boolean)aValues[ i ].getValue() );							break;
				// SQLite doesn't support boolean
				case Types.BOOLEAN:			values.put( asColumns[ i ], (int)((boolean)aValues[ i ].getValue() ? 1 : 0) );				break;
				case Types.TINYINT:			values.put( asColumns[ i ], (byte)aValues[ i ].getValue() );								break;
				case Types.SMALLINT:		values.put( asColumns[ i ], (short)aValues[ i ].getValue() );								break;
				case Types.INTEGER:			values.put( asColumns[ i ], (int)aValues[ i ].getValue() );									break;
				case Types.BIGINT:			values.put( asColumns[ i ], (long)aValues[ i ].getValue() );								break;
				case Types.FLOAT:			values.put( asColumns[ i ], (float)aValues[ i ].getValue() );								break;
				case Types.DOUBLE:			values.put( asColumns[ i ], (double)aValues[ i ].getValue() );								break;
				// Text
				case Types.CHAR:
				case Types.VARCHAR:
				case Types.LONGVARCHAR:	//	values.put( asColumns[ i ], (String)aValues[ i ].getValue() );							break;
					values.put( asColumns[ i ], SqlType.rawStringToSql( (String)aValues[ i ].getValue() ) );	break;
				case SqlType.FIXED_STRING:	values.put( asColumns[ i ], (String)aValues[ i ].getValue() );								break;
				case Types.BLOB:			values.put( asColumns[ i ], (byte[])aValues[ i ].getValue() );								break;
				default: throw new RuntimeException( TAG + " -> update function: Type NOT supported" );
			}
		}
		return mDb.update( table, values, sColId + "=?", new String[]{ String.valueOf( id ) } ) > 0;
	}
	// Delete
	public boolean delete( String table, String sColId, long id ){
		return mDb.delete( table, sColId + "=?", new String[]{ String.valueOf( id ) } ) > 0;
	}
	public void delete( String table ){		// delete all
		mDb.delete( table, null, null );
	}
	// Read
	private int miFetchedRegs = 0;
	private long miTotalRegs = 0;
	private long miElapsedTime = 0;
	/**
	 * Query for SELECT, SHOW, DESCRIBE and EXPLAIN
	 * Note that SQLite doesn't have SQL_CALC_FOUND_ROWS
	 * @param sql String SQL to execute (with no LIMIT word)
	 * @param limitOffset start of the rows
	 * @param limitSize rows to show
	 * @return array execution of the query
	 */
	public Cursor select( String sql, int limitOffset, int limitSize ){
		miElapsedTime = System.nanoTime();
		Cursor cursor = mDb.rawQuery( sql + " LIMIT " + limitOffset + ", " + limitSize, null );
		if( cursor != null ){
			miFetchedRegs = cursor.getCount();
			cursor.moveToFirst();
		}
		else{
			miFetchedRegs = 0;
		}
		// Get the total rows
		Cursor cursortotal = mDb.rawQuery( sql, null );
		if( cursortotal != null )
			miTotalRegs = cursortotal.getCount();
		else	miTotalRegs = 0;
		// End
		miElapsedTime = System.nanoTime() - miElapsedTime;
		return cursor;
	}
	public Cursor select( String sql ){
		miElapsedTime = System.nanoTime();
		Cursor cursor = mDb.rawQuery( sql, null );
		miElapsedTime = System.nanoTime() - miElapsedTime;
		if( cursor != null ){
			miFetchedRegs = cursor.getCount();
			cursor.moveToFirst();
		}
		else{
			miFetchedRegs = 0;
		}
		return cursor;
	}
	public int getFetchedRegs(){ return miFetchedRegs; }
	public long getTotalRegs(){ return miTotalRegs; }
	public long getElapsed(){ return miElapsedTime; }
	/**
	 * Execute "any sql"
	 * Example 1: INSERT INTO table_name (field_1, field_2) VALUES (1, 2), (3, 4), (5, 'Ship')
	 * Example 2: UPDATE TABLE table_name SET field_1 = 1, field_2 = 'Ship' WHERE id = 3
	 * Example 3: DELETE FROM table_name WHERE id_1 = 2 AND id_2 = 3
	 * @param sql
	 */
	public void exec( String sql ){
		miElapsedTime = System.nanoTime();
		mDb.execSQL( sql );
		miElapsedTime = System.nanoTime() - miElapsedTime;
	}
	public void transaction( String[] sqls, SQLiteDatabase db ){
		miElapsedTime = System.nanoTime();
		db.beginTransaction();
		try{
			for( String s : sqls )
				db.execSQL( s );
			db.setTransactionSuccessful();
		}
		catch( SQLException e ){
			throw new SQLException( "transaction error: " + e.getMessage() );
		}
		finally {
			db.endTransaction();
		}
		miElapsedTime = System.nanoTime() - miElapsedTime;
	}
	public void transaction( String[] sqls ){
		transaction( sqls, mDb );
	}
}