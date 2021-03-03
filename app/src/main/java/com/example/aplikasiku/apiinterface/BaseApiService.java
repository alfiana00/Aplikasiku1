package com.example.aplikasiku.apiinterface;

import com.example.aplikasiku.model.KebocoranResponse;
import com.example.aplikasiku.model.LoginResponse;
import com.example.aplikasiku.model.RateResponse;
import com.example.aplikasiku.model.RealtimeResponse;
import com.example.aplikasiku.model.StatusButtonResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BaseApiService {
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse>loginRequest(@Field("username")String username,
                                    @Field("password")String password);
    @GET("realtime.php")
    Call<RealtimeResponse>getRealtime(@Query("gedung")String gedung);
    @GET("rate-air.php")
    Call<List<RateResponse>>getRateAir(@Query("gedung")String gedung,
                                       @Query("waktu1")String waktu1,
                                       @Query("waktu2")String waktu2);
    @GET("kebocoran.php")
    Call<List<KebocoranResponse>>getKebocoranAir(@Query("gedung")String gedung,
                                                 @Query("waktu1")String waktu1,
                                                 @Query("waktu2")String waktu2);
    @GET("status-button.php")
    Call<StatusButtonResponse>getStatusButton(@Query("gedung")String gedung);

}
