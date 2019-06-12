package com.example.lugares.data;

import com.example.lugares.helpers.system.HashMapFileManager;
import com.example.lugares.helpers.system.Id;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Place extends Id implements Serializable {

	// REMIND THAT THE Context MUST BE SET BEFORE USING ALL !!!
	public static final HashMapFileManager<Place> dataManager = new HashMapFileManager<>( null, "T.data", "last_id_T.data" );

	private String name = "";
	private String description = "";
	private double latitude = 0;
	private double longitude = 0;
	private int valoration = 5;	// 1 - 5

	public Place(){
		id = dataManager.getNewId();
	}
	public Place( String name, String description, double latitude, double longitude, int valoration ){
		this();
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.valoration = valoration;
	}
	public Place( long id, String name, String description, double latitude, double longitude, int valoration ){
		this.id = id;
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.valoration = valoration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLatLong( LatLng latLong ){
		this.latitude = latLong.latitude;
		this.longitude = latLong.longitude;
	}

	public LatLng getLatLong(){
		return new LatLng( this.latitude, this.longitude );
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getValoration() {
		return valoration;
	}

	public void setValoration(int valoration) {
		this.valoration = valoration;
	}
}
