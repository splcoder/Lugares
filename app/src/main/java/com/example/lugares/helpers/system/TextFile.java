package com.example.lugares.helpers.system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextFile {
	public static void writeFile( String fileName, String data, String br, boolean append ) throws IOException {
		BufferedWriter bw = new BufferedWriter( new FileWriter( fileName, append ) );
		bw.write( data + br );
		bw.close();
	}

	public static String readFile( String fileName, String nl ) throws IOException {
		String sRead = "", sLine = "";
		BufferedReader br = new BufferedReader( new FileReader( fileName ) );
		while( (sLine = br.readLine()) != null )	sRead += sLine + nl;
		br.close();
		return sRead;
	}

	public static int countFileLines( String fileName ) throws IOException {
		int iCount = 0;
		String sLine = "";
		BufferedReader br = new BufferedReader( new FileReader( fileName ) );
		while( (sLine = br.readLine()) != null )	iCount++;
		br.close();
		return iCount;
	}
}
