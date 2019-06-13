package com.example.lugares.data;

import java.util.Comparator;

public class SortById implements Comparator<Place> {
	// Used for sorting in ascending order of id
	public int compare( Place a, Place b ){
		return (int)(a.getId() - b.getId());	// Upsss
	}
}
