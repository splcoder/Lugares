package com.example.lugares;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

	final String SAVE_PREFERENCES_DATA = "save_prefs_data";
	final String SAVE_PREFERENCES_EMAIL = "save_prefs_email";
	final String SAVE_PREFERENCES_PASS = "save_prefs_pass";
	final String SAVE_PREFERENCES_CHECK_BOX = "save_prefs_check_box";

	EditText txtEmail;
	EditText txtPass;
	CheckBox cbRemindMe;
	Button btnEnter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txtEmail = findViewById( R.id.txtEmail );
		txtPass = findViewById( R.id.txtPass );
		cbRemindMe = findViewById( R.id.cbRemindMe );
		btnEnter = findViewById( R.id.btnEnter );

		SharedPreferences prefs = getSharedPreferences( SAVE_PREFERENCES_DATA, MODE_PRIVATE );
		txtEmail.setText( prefs.getString( SAVE_PREFERENCES_EMAIL, "" ) );
		txtPass.setText( prefs.getString( SAVE_PREFERENCES_PASS, "" ) );
		cbRemindMe.setChecked( prefs.getBoolean( SAVE_PREFERENCES_CHECK_BOX, false ) );

		btnEnter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String sPass = "entrar";
				//
				String sSetEmail = txtEmail.getText().toString();
				String sSetPass = txtPass.getText().toString();
				if( sSetEmail.isEmpty() ){
					Toasty.error( getApplicationContext(), "Set an email.", Toast.LENGTH_SHORT, true ).show();
					return;
				}
				if( ! sSetPass.equals( sPass ) ){
					Toasty.error( getApplicationContext(), "Password incorrect !", Toast.LENGTH_SHORT, true ).show();
					return;
				}
				// Save
				if( cbRemindMe.isChecked() ){
					SharedPreferences.Editor editor = getSharedPreferences( SAVE_PREFERENCES_DATA, MODE_PRIVATE ).edit();
					editor.putString( SAVE_PREFERENCES_EMAIL, sSetEmail );
					editor.putString( SAVE_PREFERENCES_PASS, sSetPass );
					editor.putBoolean( SAVE_PREFERENCES_CHECK_BOX, true );
					//editor.apply();
					editor.commit();
				}
				else{
					SharedPreferences.Editor editor = getSharedPreferences( SAVE_PREFERENCES_DATA, MODE_PRIVATE ).edit();
					editor.clear();
					editor.commit();
				}
				// Exe
			}
		});
	}
}
