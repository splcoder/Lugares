package com.example.lugares.helpers.system;

import android.content.Context;
import android.util.Log;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * For reading/saving an array (a hash map) of objects from/into a file
 *
 * The class T (that must implements Serializable) must extends Id
 * So in the constructor of the class T (for a new object) must be:
 * 		setId( HashMapFileManager<T>.getNewId() );
 *		setId( HashMapFileManager.<Place>getNewId() );
 *
 * @param <T extends Id>
 */
public class HashMapFileManager<T extends Id> {
	private String fileName = "";
	private HashMap<Long, T> mapValues = new HashMap<>();

	// Incremental
	private static String _fileNameForId = "";
	private static AtomicLong _lastId = new AtomicLong(0 );

	// Reader
	private FileInputStream fis;
	private ObjectInputStream ois;
	// Writer
	private FileOutputStream fos;
	private ObjectOutputStream oos;

	// Android
	private Context context = null;

	public HashMapFileManager( Context context, String fileName, String fileNameForId ){
		this.context = context;
		this.fileName = fileName;
		_fileNameForId = fileNameForId;
	}

	public HashMap<Long, T> get(){ return mapValues; }

	public static long getNewId(){ return _lastId.getAndIncrement(); }

	//----------------------------------------------------------------------------------------------
	private static void readLastId(){
		long lastId = 0;
		byte[] bytes = new byte[ Long.BYTES ];
		try {
			FileInputStream fisId = new FileInputStream( _fileNameForId );
			fisId.read( bytes );
			fisId.close();
			lastId = BytesManager.toLong( bytes );
		} catch( FileNotFoundException e ) {
			Log.i( "File read", "The file '" + _fileNameForId + "' must be created" );
			// Using default = 0 = starting value
		} catch( Exception e ){
			e.printStackTrace();
		}
		_lastId.set( lastId );
	}
	private static void writeLastId(){
		try {
			FileOutputStream fosId = new FileOutputStream( _fileNameForId, false );
			fosId.write( BytesManager.toByteArray( _lastId.get() ) );
			fosId.close();
		} catch( Exception e ){
			Log.i( "File write", "The file '" + _fileNameForId + "' can not be created" );
			e.printStackTrace();
		}
	}
	//----------------------------------------------------------------------------------------------

	private T readNext() throws IOException, ClassNotFoundException {
		try {
			if( ois != null )	return (T)ois.readObject();
		} catch( EOFException e ) {
			// The EOFException must be catch here and don't thrown
		}
		return null;
	}

	public HashMap<Long, T> readAll( boolean read_last_id ){
		if( read_last_id )	readLastId();
		//-----------------------------------
		//mapValues = new HashMap<Long, T>();
		mapValues.clear();
		try {
			//fis = new FileInputStream( fileName );
			fis = context.openFileInput( fileName );
			ois = new ObjectInputStream( fis );
			//
			T mv = null;
			while( (mv = readNext()) != null ) {
				// Remind that T extends Id class, so getId() function
				mapValues.put( mv.getId(), mv );
			}
		} catch( FileNotFoundException e ) {
			//Console.show( "The file '" + fileName + "' must be created", true );
			Log.i( "File read", "The file '" + fileName + "' must be created" );
		} catch( Exception e ) {
			e.printStackTrace();
		}
		finally {
			// Close
			try {
				if( fis != null )	fis.close();
				if( ois != null )	ois.close();
			} catch( IOException e ){
				e.printStackTrace();
			}
		}
		return mapValues;
	}
	public HashMap<Long, T> readAll(){ return readAll( true ); }

	public void writeAll( boolean read_last_id ){
		if( read_last_id )		writeLastId();
		//----------------------------------------
		try{
			//fos = new FileOutputStream( fileName, false );
			// Android
			fos = context.openFileOutput( fileName, Context.MODE_PRIVATE );
			oos = new ObjectOutputStream( fos );
			if( oos != null ) {
				for( long id : mapValues.keySet() ){
					oos.writeObject( mapValues.get( id ) );
				}
			}
		} catch( IOException e ){
			Log.e( "File write", "The file '" + fileName + "' can not be created" );
			e.printStackTrace();
		} catch( Exception e ){
			e.printStackTrace();
		}
		finally {
			// Close
			try {
				if( fos != null )	fos.close();
				if( oos != null )	oos.close();
			} catch( Exception e ){
				e.printStackTrace();
			}
		}
	}
	public void writeAll(){ writeAll( true ); }
}
