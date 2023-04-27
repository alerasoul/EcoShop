package com.satpay.ecoshop.model;

import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("rate")
    float rate;

    @SerializedName("count")
    long count;

    public Rating(float rate, long count) {
        this.rate = rate;
        this.count = count;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
