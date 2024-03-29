package com.example.lugares.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lugares.R;
import com.example.lugares.adapters.ListPlacesAdapter;
import com.example.lugares.data.CacheKeys;
import com.example.lugares.data.Place;
import com.example.lugares.helpers.system.Cache;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class ListPlacesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
	ListPlacesActivity that = this;

	HashMap<Long, Place> mapPlaces;
	ArrayList<Place> aPlaces = new ArrayList<>();
	ListPlacesAdapter listPlacesAdapter;

	ListView listPlaces;
	FloatingActionButton btnAdd;
	FloatingActionButton btnMode;
	FloatingActionButton btnShowMarkers;

	boolean bSeeLocationInWorld = false;

	public ArrayList<Place> getPlaces(){ return aPlaces; }
	public void addNewPlace( LatLng latLngSelected, String name, String description, float valoration ){
		Place place = new Place( name, description, latLngSelected.latitude, latLngSelected.longitude, valoration );
		mapPlaces.put( place.getId(), place );
		aPlaces.add( place );
		listPlacesAdapter.notifyDataSetChanged();
	}
	public void updateListPlacesAdapter(){ listPlacesAdapter.notifyDataSetChanged(); }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_places);

		Cache.set( CacheKeys.LIST_PLACES_ACTIVITY, that );	// <<< Required !

		listPlaces = findViewById( R.id.listPlaces );
		btnAdd = findViewById( R.id.btnAdd );
		btnMode = findViewById( R.id.btnMode );
		btnShowMarkers = findViewById( R.id.btnShowMarkers );

		//View header = getLayoutInflater().inflate( R.layout.header, null );
		View footer = getLayoutInflater().inflate( R.layout.list_places_footer, null );
		//listPlaces.addHeaderView( header );
		listPlaces.addFooterView( footer );

		// First set the context <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		Place.dataManager.setContext( getApplicationContext() );
		mapPlaces = Place.dataManager.readAll();
		for( long id : mapPlaces.keySet() ){
			aPlaces.add( mapPlaces.get( id ) );
		}
		listPlacesAdapter = new ListPlacesAdapter( this, aPlaces );
		listPlaces.setAdapter( listPlacesAdapter );
		listPlaces.setOnItemClickListener( this );
		listPlaces.setOnItemLongClickListener( this );

		btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toasty.info( getApplicationContext(), "Clicked", Toast.LENGTH_SHORT, true ).show();
				Intent intent = new Intent( getApplicationContext(), SelectPlaceActivity.class );
				Cache.set( CacheKeys.ADD_PLACE, true );
				Cache.set( CacheKeys.ONLY_SHOW_MARKERS, false );
				startActivity( intent );
			}
		});

		btnMode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if( bSeeLocationInWorld ){
					bSeeLocationInWorld = false;
					btnMode.setImageResource( android.R.drawable.ic_menu_view );
				}
				else{
					bSeeLocationInWorld = true;
					btnMode.setImageResource( android.R.drawable.ic_menu_mapmode );
				}
			}
		});

		btnShowMarkers.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent( getApplicationContext(), SelectPlaceActivity.class );
				Cache.set( CacheKeys.ONLY_SHOW_MARKERS, true );
				Cache.set( CacheKeys.ALL_PLACES_EQUAL, true );
				startActivity( intent );
			}
		});

		if( aPlaces.isEmpty() ){
			Toasty.info( getApplicationContext(), "Add a new place", Toast.LENGTH_SHORT, true ).show();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Place place = aPlaces.get( position );
		//Toasty.success( getApplicationContext(), place.getName(), Toast.LENGTH_SHORT, true ).show();
		Intent intent = null;
		if( bSeeLocationInWorld ){
			Cache.set( CacheKeys.ONLY_SHOW_MARKERS, true );
			Cache.set( CacheKeys.FIXED_LOCATION, place );
			Cache.set( CacheKeys.ZOOM_PLACE, true );
			intent = new Intent( ListPlacesActivity.this, SelectPlaceActivity.class );
		}
		else{
			Cache.set( CacheKeys.STREET_LAT_LONG, place.getLatLong() );
			intent = new Intent( ListPlacesActivity.this, StreetActivity.class );
		}
		startActivity( intent );
	}

	@Override
	protected void onPause() {
		super.onPause();

		Place.dataManager.writeAll();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
		new AlertDialog.Builder(this )
				.setIcon( android.R.drawable.ic_dialog_alert )
				.setTitle( "Delete" )
				.setMessage( "Are you sure ?" )
				.setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Place place = aPlaces.get( position );
						mapPlaces.remove( place.getId() );
						aPlaces.remove( position );
						listPlacesAdapter.notifyDataSetChanged();
					}

				})
				.setNegativeButton( "Cancel", null )
				.show();

		return true; // Only longClick (NOT click)
	}
}
