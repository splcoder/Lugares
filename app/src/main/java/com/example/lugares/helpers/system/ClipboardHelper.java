package com.example.lugares.helpers.system;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class ClipboardHelper {
	public static void setText( String txt, Context context ){
		ClipboardManager clipboard = (ClipboardManager)context.getSystemService( Context.CLIPBOARD_SERVICE );
		//ClipData clip = ClipData.newPlainText( txt );
		ClipData clip = ClipData.newPlainText("label", txt );
		clipboard.setPrimaryClip( clip );
	}

	public static String getText( Context context ){
		ClipboardManager clipboard = (ClipboardManager)context.getSystemService( Context.CLIPBOARD_SERVICE );
		String pasteData = "";

		// If it does contain data, decide if you can handle the data.
		if( ! clipboard.hasPrimaryClip() ){
			// ""
		}
		else if( ! clipboard.getPrimaryClipDescription().hasMimeType( MIMETYPE_TEXT_PLAIN ) ){
			// Since the clipboard has data but it is not plain text
		}
		else{
			// Since the clipboard contains plain text.
			ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0 );
			// Gets the clipboard as text.
			pasteData = item.getText().toString();
		}
		return pasteData;
	}
}
