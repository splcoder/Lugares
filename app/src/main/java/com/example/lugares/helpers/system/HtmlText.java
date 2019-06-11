package com.example.lugares.helpers.system;

import android.text.Html;
import android.text.Spanned;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlText {
	public static Spanned get( String htmlText ){
		Spanned sp = Html.fromHtml( htmlText );
		return sp;
	}
	public static void set( String htmlText, TextView textView ){
		Spanned sp = Html.fromHtml( htmlText );
		textView.setText( sp );
	}
	public static void set( String htmlText, WebView webView ){
		webView.loadDataWithBaseURL("file:///android_res/drawable/", htmlText,"text/html","utf-8",null );
	}
	public static void set( String htmlText, WebView webView, String baseUrl ){
		webView.loadDataWithBaseURL( baseUrl, htmlText,"text/html","utf-8",null );
	}
	/**
	 * Use in the html the next format for a var:
	 * 		<% MyVar %>		i.e. using the character: '%'
	 *
	 * Then use the argument:
	 * 		Map<String, String> mapReplaceText = new HashMap<String, String>();
	 * 		mapReplaceText.put( "MyVar", "This is the text" );
	 */
	public static void set( String htmlText, WebView webView, Map<String, String> tokens, String baseUrl ){
		// Create pattern of the format "%(cat|beverage)%"
		String patternString = "<%\\s(" + StringUtils.join( tokens.keySet(),"|" ) + ")\\s%>";
		Pattern pattern = Pattern.compile( patternString );
		Matcher matcher = pattern.matcher( htmlText );

		StringBuffer sb = new StringBuffer();
		while( matcher.find() ){
			matcher.appendReplacement( sb, tokens.get( matcher.group( 1 ) ) );
		}
		matcher.appendTail( sb );
		webView.loadDataWithBaseURL( baseUrl, sb.toString(),"text/html","utf-8",null );
	}
	public static void set( String htmlText, WebView webView, Map<String, String> tokens ){
		HtmlText.set( htmlText, webView, tokens,"file:///android_res/drawable/" );
	}
}