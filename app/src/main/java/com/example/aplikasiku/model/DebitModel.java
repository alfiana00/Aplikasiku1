package com.example.aplikasiku.model;

import com.google.gson.annotations.SerializedName;

public class DebitModel {
    @SerializedName("id")
    private int id;
    @SerializedName("waktu")
    private int waktu;
    @SerializedName("debitA")
    private int debitA;
    @SerializedName("debitB")
    private int debitB;
    @SerializedName("debitC")
    private int debitC;
    @SerializedName("debitD")
    private int debitD;
    @SerializedName("debitP")
    private int debitP;

    public DebitModel(int id, int waktu, int debitA, int debitB, int debitC, int debitD, int debitP) {
        this.id = id;
        this.waktu = waktu;
        this.debitA = debitA;
        this.debitB = debitB;
        this.debitC = debitC;
        this.debitD = debitD;
        this.debitP = debitP;
    }

    public int getId() {
        return id;
    }

    public int getWaktu() {
        return waktu;
    }

    public int getDebitA() {
        return debitA;
    }

    public int getDebitB() {
        return debitB;
    }

    public int getDebitC() {
        return debitC;
    }

    public int getDebitD() {
        return debitD;
    }

    public int getDebitP() {
        return debitP;
    }
}
