package com.example.aplikasiku.model;

import com.google.gson.annotations.SerializedName;

public class RateAir {
    @SerializedName("id")
    private int id;
    @SerializedName("waktu")
    private int waktu;
    @SerializedName("rateA")
    private int rateA;
    @SerializedName("rateB")
    private int rateB;
    @SerializedName("rateC")
    private int rateC;
    @SerializedName("rateD")
    private int rateD;
    @SerializedName("rateP")
    private int rateP;

    public RateAir(int id, int waktu, int rateA, int rateB, int rateC, int rateD, int rateP) {
        this.id = id;
        this.waktu = waktu;
        this.rateA = rateA;
        this.rateB = rateB;
        this.rateC = rateC;
        this.rateD = rateD;
        this.rateP = rateP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWaktu() {
        return waktu;
    }

    public void setWaktu(int waktu) {
        this.waktu = waktu;
    }

    public int getRateA() {
        return rateA;
    }

    public void setRateA(int rateA) {
        this.rateA = rateA;
    }

    public int getRateB() {
        return rateB;
    }

    public void setRateB(int rateB) {
        this.rateB = rateB;
    }

    public int getRateC() {
        return rateC;
    }

    public void setRateC(int rateC) {
        this.rateC = rateC;
    }

    public int getRateD() {
        return rateD;
    }

    public void setRateD(int rateD) {
        this.rateD = rateD;
    }

    public int getRateP() {
        return rateP;
    }

    public void setRateP(int rateP) {
        this.rateP = rateP;
    }
}
