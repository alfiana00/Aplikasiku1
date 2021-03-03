package com.example.aplikasiku.apiinterface;

import android.os.Debug;

import com.example.aplikasiku.model.DebitModel;
import com.example.aplikasiku.model.KebocoranModel;
import com.example.aplikasiku.model.LoginResponse;
import com.example.aplikasiku.model.RateAir;
import com.example.aplikasiku.model.RealtimeResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
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
    Call<RealtimeResponse>getRealtime(@Query("rateA")String rateA,
                                      @Query("rateB")String rateB,
                                      @Query("rateC")String rateC,
                                      @Query("rateD")String rateD,
                                      @Query("rateP")String rateP);
    @GET("rate.php")
    Call<List<RateAir>>getRateAir();
    @GET("debit.php")
    Call<List<DebitModel>>getDebitAir();
    @GET("kebocoran.php")
    Call<List<KebocoranModel>>getKebocoranAir();
}
