package com.example.lugares.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.lugares.R;
import com.example.lugares.activities.ListPlacesActivity;
import com.example.lugares.activities.SetPlaceDataActivity;
import com.example.lugares.data.Place;
import com.example.lugares.data.SortById;
import com.example.lugares.helpers.system.Cache;

import java.util.ArrayList;
import java.util.Collections;

public class ListPlacesAdapter extends ArrayAdapter<Place> {
	private ArrayList<Place> listPlaces;
	private Context context;
	private int lastPosition = -1;

	public ListPlacesAdapter( Context context, ArrayList<Place> listPlaces ){
		super( context, R.layout.activity_list_places, listPlaces );
		this.listPlaces = listPlaces;
		this.context = context;

		Collections.sort( this.listPlaces, new SortById() );
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent ){
		// Get the data item for this position
		final Place place = getItem( position );
		// Check if an existing view is being reused, otherwise inflate the view
		if( convertView == null ){
			convertView = LayoutInflater.from( getContext() ).inflate( R.layout.item_place, parent,false );
		}
		// Lookup view for data population
		TextView txtName = (TextView)convertView.findViewById( R.id.txtName );
		TextView txtDescription = (TextView)convertView.findViewById( R.id.txtDescription );
		RatingBar rbValoration = (RatingBar)convertView.findViewById( R.id.rbValoration );
		ImageView imgMod = (ImageView)convertView.findViewById( R.id.imgMod );

		imgMod.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Cache.set( "modPlace", place );
				Intent intent = new Intent( context, SetPlaceDataActivity.class);
				context.startActivity( intent );
			}
		});

		// TODO background color
		//txtName.setBackgroundTintList( this.context.getResources().getColorStateList( R.color. ) );
		// Populate the data into the template view using the data object
		txtName.setText( place.getName() );
		//txtName.setText( place.getName() + ": " + place.getId() );
		txtDescription.setText( place.getDescription() );

		rbValoration.setNumStars( 5 );
		rbValoration.setRating( place.getValoration() );
		rbValoration.setIsIndicator( true );	// Fix
		switch( (int)place.getValoration() ){
			case 1: {
				//imageView.setImageResource( R.drawable.ic_note_green );
				txtName.setTextColor( ContextCompat.getColor( context, R.color.black ) );
				break;
			}
			case 2: {
				txtName.setTextColor( ContextCompat.getColor( context, R.color.blue ) );
				break;
			}
			case 3: {
				txtName.setTextColor( ContextCompat.getColor( context, R.color.green ) );
				break;
			}
			case 4: {
				txtName.setTextColor( ContextCompat.getColor( context, R.color.orange ) );
				break;
			}
			case 5: {
				txtName.setTextColor( ContextCompat.getColor( context, R.color.red ) );
				break;
			}
		}
		// Return the completed view to render on screen
		return convertView;
	}

}
