package com.example.lugares.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.lugares.R;
import com.example.lugares.helpers.system.Cache;
import com.google.android.gms.maps.model.LatLng;

public class SetPlaceDataActivity extends AppCompatActivity {

	ListPlacesActivity listPlacesActivity;
	LatLng latLngSelected;

	EditText txtName;
	EditText txtDescription;
	RatingBar rbValoration;
	Button btnCreate;
	Button btnCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_place_data);

		listPlacesActivity = (ListPlacesActivity) Cache.get( "ListPlacesActivity" );
		latLngSelected = (LatLng)Cache.get( "place_selected" );

		txtName = findViewById( R.id.txtName );
		txtDescription = findViewById( R.id.txtDescription );
		rbValoration = findViewById( R.id.rbValoration );
		btnCreate = findViewById( R.id.btnCreate );
		btnCancel = findViewById( R.id.btnCancel );

		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		btnCreate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = txtName.getText().toString();
				String description = txtDescription.getText().toString();
				int valoration = (int)rbValoration.getRating();
				listPlacesActivity.addNewPlace( latLngSelected, name, description, valoration );
				finish();
			}
		});
	}
}
