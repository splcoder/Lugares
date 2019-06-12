package com.example.lugares.helpers.system;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;

public class PhoneAction {
	/**
	 * Open an Url
	 * @param context
	 * @param url
	 */
	public static void openWeb( Context context, String url ){
		Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
		context.startActivity( intent );
	}

	/**
	 * GoogleMap
	 * @param context
	 * @param latLng
	 */
	public static void openMap( Context context, LatLng latLng ){
		Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "https://www.google.es/maps/@" + latLng.latitude + "," + latLng.longitude + ",15z" ) );
		context.startActivity( intent );
	}

	public static void takePhoto( Context context ){
		Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
		context.startActivity( intent );
	}

	public static void sendEmail( Context context, String email, String subject, String text ){
		Intent intent = new Intent( Intent.ACTION_SEND );
		intent.setType( "text/plain" );
		intent.putExtra( Intent.EXTRA_SUBJECT, subject );
		intent.putExtra( Intent.EXTRA_TEXT, text );
		intent.putExtra( Intent.EXTRA_EMAIL, new String[]{ email } );
		context.startActivity( intent );
	}
	public static void sendEmail( Context context, String[] emails, String subject, String text ){
		Intent intent = new Intent( Intent.ACTION_SEND );
		intent.setType( "text/plain" );
		intent.putExtra( Intent.EXTRA_SUBJECT, subject );
		intent.putExtra( Intent.EXTRA_TEXT, text );
		intent.putExtra( Intent.EXTRA_EMAIL, emails );
		context.startActivity( intent );
	}
	public static void sendWhatsApp( Context context, String email, String subject, String text ){
		Intent intent = new Intent( Intent.ACTION_SEND );
		intent.setType( "text/plain" );
		intent.putExtra( Intent.EXTRA_SUBJECT, subject );
		intent.putExtra( Intent.EXTRA_TEXT, text );
		intent.putExtra( Intent.EXTRA_EMAIL, new String[]{ email } );
		intent.setPackage( "com.whatsapp" );
		context.startActivity( intent );
	}
	public static void sendWhatsApp( Context context, String[] emails, String subject, String text ){
		Intent intent = new Intent( Intent.ACTION_SEND );
		intent.setType( "text/plain" );
		intent.putExtra( Intent.EXTRA_SUBJECT, subject );
		intent.putExtra( Intent.EXTRA_TEXT, text );
		intent.putExtra( Intent.EXTRA_EMAIL, emails );
		intent.setPackage( "com.whatsapp" );
		context.startActivity( intent );
	}

	private static final int MY_CALL_PERMISSION = 23;
	public static void call( Context context, String telephoneNumber ){
		// In the manifest:		<uses-permission android:name="android.permission.CALL_PHONE" />
		Intent intent = null;
		if( ActivityCompat.checkSelfPermission( context, Manifest.permission.CALL_PHONE ) == PackageManager.PERMISSION_GRANTED ){
			intent = new Intent( Intent.ACTION_CALL, Uri.parse( "tel:" + telephoneNumber ) );
		}
		else{
			// TODO
			//ActivityCompat.requestPermissions( context, new String[]{ Manifest.permission.CALL_PHONE }, MY_CALL_PERMISSION );
		}
		if( intent != null ){
			context.startActivity( intent );
		}
	}
}
