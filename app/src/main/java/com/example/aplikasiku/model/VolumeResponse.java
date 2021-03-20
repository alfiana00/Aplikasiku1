package com.example.aplikasiku.model;

import java.util.List;

public class VolumeResponse{
	private List<DataVolume> data;
	private boolean success;
	private String message;

	public List<DataVolume> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}