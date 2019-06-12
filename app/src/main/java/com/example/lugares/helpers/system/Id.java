package com.example.lugares.helpers.system;

import java.io.Serializable;

// See: https://www.dev2qa.com/android-get-application-context-from-anywhere-example/
public class Id implements Serializable {
	protected long id = 0;
	public Id(){}
	public Id( long id ){
		this.id = id;
	}
	public long getId(){
		return id;
	}
	public void setId( long id ){
		this.id = id;
	}
}
