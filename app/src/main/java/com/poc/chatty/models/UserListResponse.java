package com.poc.chatty.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonregalado on 1/16/18.
 */

public class UserListResponse implements Serializable
{
    
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("result")
    @Expose
    private List<Result> result = new ArrayList<Result>();
    private final static long serialVersionUID = -7956914496040999729L;
    
    public Integer getCount() {
        return count;
    }
    
    public void setCount(Integer count) {
        this.count = count;
    }
    
    public List<Result> getResult() {
        return result;
    }
    
    public void setResult(List<Result> result) {
        this.result = result;
    }
}