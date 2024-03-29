package com.example.lugares.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.lugares.R;
import com.example.lugares.data.CacheKeys;
import com.example.lugares.data.Place;
import com.example.lugares.helpers.system.Cache;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

//public class SelectPlaceActivity extends FragmentActivity implements OnMapReadyCallback {
public class SelectPlaceActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnInfoWindowClickListener {

	private GoogleMap mMap;
	FloatingActionButton btnAddSelected;

	ListPlacesActivity listPlacesActivity;

	Marker markerSelected = null;
	LatLng latLngSelected = null;

	boolean addPlace = false;
	boolean onlyShowMarkers = false;
	boolean allPlacesEqual = false;
	boolean zoomPlace = false;
	Place fixedPlace = null;
	boolean modifyPlaceLocation = false;

	@SuppressLint("RestrictedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_place);

		btnAddSelected = findViewById( R.id.btnAddSelected );
		btnAddSelected.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if( latLngSelected == null ){
					Toasty.info( getApplicationContext(), "You must set a marker", Toast.LENGTH_SHORT, true ).show();
					return;
				}
				// Set the data of this place
				Cache.set( CacheKeys.PLACE_SELECTED, latLngSelected );
				if( addPlace ){
					Intent intent = new Intent( SelectPlaceActivity.this, SetPlaceDataActivity.class );
					startActivity( intent );
				}
				finish();
			}
		});

		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this );

		listPlacesActivity = (ListPlacesActivity)Cache.get( CacheKeys.LIST_PLACES_ACTIVITY );
		addPlace = Cache.delBoolean( CacheKeys.ADD_PLACE, false );		// get and del
		modifyPlaceLocation = Cache.delBoolean( CacheKeys.MODIFY_PLACE_LOCATION, false );		// get and del
		zoomPlace = Cache.delBoolean( CacheKeys.ZOOM_PLACE, false );	// get and del
		onlyShowMarkers = Cache.delBoolean( CacheKeys.ONLY_SHOW_MARKERS, false );
		allPlacesEqual = Cache.delBoolean( CacheKeys.ALL_PLACES_EQUAL, false );	// <<< get and del
		fixedPlace = (Place)Cache.get( CacheKeys.FIXED_LOCATION );

		if( addPlace || modifyPlaceLocation ){
			btnAddSelected.setVisibility( View.VISIBLE );
		}
		//if( modifyPlaceLocation )	btnAddSelected.setTooltipText( "Mod" );
	}


	/**
	 * Manipulates the map once available.
	 * This callback is triggered when the map is ready to be used.
	 * This is where we can add markers or lines, add listeners or move the camera. In this case,
	 * we just add a marker near Sydney, Australia.
	 * If Google Play services is not installed on the device, the user will be prompted to install
	 * it inside the SupportMapFragment. This method will only be triggered once the user has
	 * installed Google Play services and returned to the app.
	 */
	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;

		mMap.getUiSettings().setZoomControlsEnabled( true );
		mMap.getUiSettings().setCompassEnabled( true );
		//map:cameraZoom="13"
		//	map:mapType="satellite"

		mMap.setOnMapClickListener( this );
		mMap.setOnMarkerDragListener( this );
		mMap.setOnInfoWindowClickListener( this );

		// Add a marker in Sydney and move the camera
		/*LatLng sydney = new LatLng(-34, 151);
		mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
		mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

		// Set the markers known
		ArrayList<Place> aPlaces = listPlacesActivity.getPlaces();
		Marker marker;
		for( Place place : aPlaces ){
			marker = mMap.addMarker( new MarkerOptions()
					.position( new LatLng( place.getLatitude(), place.getLongitude() ) )
					.draggable( false )
					.title( place.getName() )
					.snippet( place.getDescription() )
					//.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_BLUE ) )
			);
			if( ! addPlace && ! allPlacesEqual && fixedPlace != null && fixedPlace.getId() == place.getId() ){
				marker.setIcon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_ORANGE ) );
				marker.showInfoWindow();
				if( modifyPlaceLocation ){
					marker.setDraggable( true );
					marker.setIcon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_YELLOW ) );
					markerSelected = marker;
				}
			}
			else	marker.setIcon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_BLUE ) );
		}

		if( fixedPlace != null ){
			if( (onlyShowMarkers || addPlace) && ! zoomPlace )
					mMap.animateCamera( CameraUpdateFactory.newLatLng( fixedPlace.getLatLong() ) );
			else	mMap.animateCamera( CameraUpdateFactory.newLatLngZoom( fixedPlace.getLatLong(), 13.0f ) );
			// Clean
			//fixedPlace = null;
		}
		else{
			LatLng donosti = new LatLng(43.327929, -1.959019 );
			//mMap.addMarker( new MarkerOptions().position( donosti ).title( "My Marker" ) );
			mMap.moveCamera( CameraUpdateFactory.newLatLng( donosti ) );
		}
	}

	@Override
	public void onMapClick(LatLng latLng) {
		//Toasty.info( getApplicationContext(), "Clicked on map", Toast.LENGTH_SHORT, true ).show();
		if( addPlace || modifyPlaceLocation ){
			if( markerSelected != null )	markerSelected.remove();
			markerSelected = mMap.addMarker( new MarkerOptions()
					.position( latLng )
					.draggable( true )
					.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_RED ) )
			);
			if( modifyPlaceLocation )	markerSelected.setIcon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_YELLOW ) );
			//mMap.moveCamera(CameraUpdateFactory.newLatLng( latLng ) );
			mMap.animateCamera( CameraUpdateFactory.newLatLng( latLng ) );
			//
			latLngSelected = latLng;	// <<< remind onMarkerDragEnd
		}
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		Toasty.info( getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT, true ).show();
		Intent intent = new Intent( SelectPlaceActivity.this, StreetActivity.class );
		Cache.set( CacheKeys.STREET_LAT_LONG, marker.getPosition() );
		startActivity( intent );
	}

	@Override
	public void onMarkerDragStart(Marker marker) {
		marker.hideInfoWindow();
	}

	@Override
	public void onMarkerDrag(Marker marker) {

	}

	@Override
	public void onMarkerDragEnd(Marker marker ) {
		latLngSelected = marker.getPosition();

		//LatLng latLng = marker.getPosition();
		//marker.setSnippet( "Pos: " + latLng.latitude + ", " + latLng.longitude );
		marker.showInfoWindow();
	}
}
