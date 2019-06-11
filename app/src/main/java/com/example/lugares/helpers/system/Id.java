package com.example.lugares.helpers.system;

public class Id {
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
