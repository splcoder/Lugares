package com.example.lugares.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.lugares.R;
import com.example.lugares.data.CacheKeys;
import com.example.lugares.helpers.system.Cache;
import com.google.android.gms.maps.model.LatLng;

import android.content.Intent;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;

public class StreetActivity extends FragmentActivity implements OnStreetViewPanoramaReadyCallback {

	//private GoogleMap mMap;
	private StreetViewPanorama streetViewPanorama;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_street);
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		/*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);*/

		StreetViewPanoramaFragment streetViewPanoramaFragment = (StreetViewPanoramaFragment) getFragmentManager().findFragmentById( R.id.fragmentStreet );
		streetViewPanoramaFragment.getStreetViewPanoramaAsync( this );
	}

	// Deleted onMapReady()

	@Override
	public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
		this.streetViewPanorama = streetViewPanorama;

		LatLng coord = (LatLng) Cache.get( CacheKeys.STREET_LAT_LONG );
		streetViewPanorama.setPosition( coord );
	}
}
