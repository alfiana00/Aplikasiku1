package com.example.aplikasiku.model;

public class VolumePerwaktuResponse{
	private Data data;
	private boolean success;
	private String message;

	public Data getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}
