package com.example.lugares.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.lugares.R;
import com.example.lugares.data.Place;
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

	Place place = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_place_data);

		listPlacesActivity = (ListPlacesActivity) Cache.get( "ListPlacesActivity" );
		latLngSelected = (LatLng)Cache.get( "place_selected" );
		place = (Place)Cache.get( "modPlace" );

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
				float valoration = rbValoration.getRating();
				if( place != null ){
					place.setName( name );
					place.setDescription( description );
					place.setValoration( valoration );
					listPlacesActivity.updateListPlacesAdapter();	// <<< required: Cache.set( "ListPlacesActivity", that );	<<< in ListPlacesActivity.onCreate
				}
				else	listPlacesActivity.addNewPlace( latLngSelected, name, description, valoration );
				finish();
			}
		});

		if( place != null ){
			txtName.setText( place.getName() );
			txtDescription.setText( place.getDescription() );
			rbValoration.setRating( place.getValoration() );

			btnCreate.setText( "Modify" );
		}
	}
}
