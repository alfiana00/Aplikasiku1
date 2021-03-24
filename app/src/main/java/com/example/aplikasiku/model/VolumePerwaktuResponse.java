package com.example.aplikasiku.model;

public class VolumePerwaktuResponse{
	private DataVolumeRatePerwaktu dataVolumeRatePerwaktu;
	private boolean success;
	private String message;

	public DataVolumeRatePerwaktu getDataVolumeRatePerwaktu(){
		return dataVolumeRatePerwaktu;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}
