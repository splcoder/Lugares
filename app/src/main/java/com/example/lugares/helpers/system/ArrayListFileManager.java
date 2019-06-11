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
import java.util.ArrayList;

/**
 * For reading/saving an array of objects (of a serializable class) from/into a file
 * @param <T>
 */
public class ArrayListFileManager<T> {
	private String fileName = "";
	private ArrayList<T> aValues = new ArrayList<>();

	// Reader
	private FileInputStream fis;
	private ObjectInputStream ois;
	// Writer
	private FileOutputStream fos;
	private ObjectOutputStream oos;

	// Android
	private Context context = null;

	public ArrayListFileManager( Context context, String fileName ){
		this.context = context;
		this.fileName = fileName;
	}

	public ArrayList<T> get(){ return aValues; }

	private T readNext() throws IOException, ClassNotFoundException {
		try {
			if( ois != null )	return (T)ois.readObject();
		} catch( EOFException e ) {
			// The EOFException must be catch here and don't thrown
		}
		return null;
	}

	public ArrayList<T> readAll(){
		//aValues = new ArrayList<>();
		aValues.clear();
		try {
			//fis = new FileInputStream( fileName );
			fis = context.openFileInput( fileName );
			ois = new ObjectInputStream( fis );
			//
			T mv = null;
			while( (mv = readNext()) != null ) {
				aValues.add( mv );
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
		return aValues;
	}

	public void writeAll(){
		try{
			//fos = new FileOutputStream( fileName, false );
			// Android
			fos = context.openFileOutput( fileName, Context.MODE_PRIVATE );
			oos = new ObjectOutputStream( fos );
			if( oos != null ) {
				for( int i = 0; i < aValues.size(); i++ ){
					oos.writeObject( aValues.get( i ) );
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
}
