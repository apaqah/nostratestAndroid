package com.andra.nostratest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andra on 7/28/2018.
 */

public class GeneralList<T> implements Serializable {

    @SerializedName("result")
    @Expose
    private List<T> data = null;


    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }


}
