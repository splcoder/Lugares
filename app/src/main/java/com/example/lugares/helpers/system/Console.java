package com.example.lugares.helpers.system;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Console {
	private static Scanner lector = null;
	public static void initLector() {
		lector = new Scanner( System.in );
	}
	public static Scanner getLector() {
		return lector;
	}
	public static void closeLector() {
		lector.close();
	}
	/**
	 * For removing/cleaning the last error taken by the Scanner (in the catch sentence)
	 * because if it is not cleaned the error will be "propagated/repeated"
	 */
	public static void flushLastError() {
		lector.next();
	}
	/**
	 * Checks if the string is != null and != ""
	 * @param str	String to check
	 * @return boolean
	 */
	public static boolean isNotEmpty( String str ) {
		return( str != null && ! str.isEmpty() );
	}
	/**
	 * Scan a real
	 * @param errorMsg	Message when the parsing breaks: for retry
	 * @return double
	 */
	public static double readReal( String errorMsg ) {
		double rRead = 0;
		for(;;) {
			try {
				rRead = lector.nextDouble();
				break;
			}catch( Exception e ) {
				if( isNotEmpty( errorMsg ) ) {
					System.out.print( errorMsg );
					Console.flushLastError();
				}
			}
		}
		return rRead;
	}
	/**
	 * Scan a real
	 * @param msgForScan	Message as a petition for scanning the value
	 * @param errorMsg		Message when the parsing breaks: for retry
	 * @return double
	 */
	public static double readReal( String msgForScan, String errorMsg ) {
		System.out.print( msgForScan );
		return readReal( errorMsg );
	}
	/**
	 * Scan an integer number
	 * @param errorMsg	Message when the parsing breaks: for retry
	 * @return long
	 */
	public static long readInt( String errorMsg ) {
		long option = 0;
		for(;;) {
			try {
				option = lector.nextLong();
				break;
			}catch( Exception e ) {
				if( isNotEmpty( errorMsg ) ) {
					System.out.print( errorMsg );
					Console.flushLastError();
				}
			}
		}
		return option;
	}
	/**
	 * Scan an integer number
	 * @param msgForScan	Message as a petition for scanning the value
	 * @param errorMsg		Message when the parsing breaks: for retry
	 * @return long
	 */
	public static long readInt( String msgForScan, String errorMsg ) {
		System.out.print( msgForScan );
		return readInt( errorMsg );
	}
	/**
	 * Scan a string
	 * @return String
	 */
	public static String readString() {
		String s = "";
		do {
			s = lector.nextLine();
		}while( ! isNotEmpty( s ) );
		return s;
	}
	/**
	 * Scan a string
	 * @param msgForScan	Message as a petition for scanning the value
	 * @return String
	 */
	public static String readString( String msgForScan ) {
		System.out.print( msgForScan );
		return readString();
	}
	/**
	 * Scan a date
	 * @param errorMsg	Message when the parsing breaks: for retry
	 * @return Date
	 */
	public static Date readDate( String errorMsg ) {
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sDate = "";
		Date date;
		for(;;) {
			try {
				sDate = lector.nextLine();
				date = sdf.parse( sDate );
				break;
			}catch( Exception e ) {
				if( isNotEmpty( errorMsg ) ){
					System.out.print( errorMsg );
					Console.flushLastError();
				}
			}
		}
		return date;
	}
	/**
	 * Scan a date
	 * @param msgForScan	Message as a petition for scanning the value
	 * @param errorMsg		Message when the parsing breaks: for retry
	 * @return Date
	 */
	public static Date readDate( String msgForScan, String errorMsg ) {
		System.out.print( msgForScan );
		return readDate( errorMsg );
	}
	/**
	 * For scanning a y/n
	 * @param question		The question
	 * @param yes			Char for checking in lower case
	 * @return boolean
	 */
	public static boolean questionYN( String question, char yes ) {
		System.out.print( question );
		if( Console.readString().toLowerCase().charAt( 0 ) == yes )	return true;
		return false;
	}
	/**
	 * Print into the console
	 * @param str	The string to print
	 * @param ln	If true, then with line break
	 */
	public static void show( String str, boolean ln ) {
		if( ln )	System.out.println( str );
		else		System.out.print( str );
	}
}
