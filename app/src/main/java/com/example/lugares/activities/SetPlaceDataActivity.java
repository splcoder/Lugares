package com.example.lugares.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.lugares.R;
import com.example.lugares.data.CacheKeys;
import com.example.lugares.data.Place;
import com.example.lugares.helpers.system.Cache;
import com.google.android.gms.maps.model.LatLng;

import es.dmoral.toasty.Toasty;

public class SetPlaceDataActivity extends AppCompatActivity {

	ListPlacesActivity listPlacesActivity;
	LatLng latLngSelected;

	EditText txtName;
	EditText txtDescription;
	RatingBar rbValoration;
	Button btnChangeLocation;
	Button btnCreate;
	Button btnCancel;

	Place place = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_place_data);

		listPlacesActivity = (ListPlacesActivity) Cache.get( CacheKeys.LIST_PLACES_ACTIVITY );
		latLngSelected = (LatLng)Cache.get( CacheKeys.PLACE_SELECTED );
		place = (Place)Cache.del( CacheKeys.MODIFY_PLACE );		// <<< get and clean

		txtName = findViewById( R.id.txtName );
		txtDescription = findViewById( R.id.txtDescription );
		rbValoration = findViewById( R.id.rbValoration );
		btnChangeLocation = findViewById( R.id.btnChangeLocation );
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
					latLngSelected = (LatLng)Cache.get( CacheKeys.PLACE_SELECTED );
					if( latLngSelected != null )	place.setLatLong( latLngSelected );
					place.setName( name );
					place.setDescription( description );
					place.setValoration( valoration );
					listPlacesActivity.updateListPlacesAdapter();
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
			btnChangeLocation.setVisibility( View.VISIBLE );
			btnChangeLocation.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Cache.set( CacheKeys.MODIFY_PLACE_LOCATION, true );
					Cache.set( CacheKeys.FIXED_LOCATION, place );
					Cache.set( CacheKeys.ZOOM_PLACE, true );
					Intent intent = new Intent( SetPlaceDataActivity.this, SelectPlaceActivity.class );
					startActivity( intent );
				}
			});
		}
	}
}
