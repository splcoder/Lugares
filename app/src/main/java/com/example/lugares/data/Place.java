package com.example.lugares.data;

import com.example.lugares.helpers.system.HashMapFileManager;
import com.example.lugares.helpers.system.Id;

import java.io.Serializable;

public class Place extends Id implements Serializable {

	private String name = "";
	private String description = "";
	private double latitude = 0;
	private double longitude = 0;
	private int valoration = 5;	// 1 - 5

	public Place(){
		setId( HashMapFileManager.<Place>getNewId() );
	}
}
