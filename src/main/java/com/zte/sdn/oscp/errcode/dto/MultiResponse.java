package com.zte.sdn.oscp.errcode.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class MultiResponse<T> extends Response {

    private int total;

    private Collection<T> data;

    public static <T> MultiResponse<T> of(Collection<T> data, int total) {
        MultiResponse<T> multiResponse = new MultiResponse<>();
        multiResponse.setSuccess(true);
        multiResponse.setData(data);
        multiResponse.setTotal(total);
        return multiResponse;
    }

    public static <T> MultiResponse<T> ofWithoutTotal(Collection<T> data) {
        return of(data,0);
    }

    
    public int getTotal() {
        return total;
    }

    
    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getData() {
        return null == data ? new ArrayList<>() : new ArrayList<>(data);
    }

    
    public void setData(Collection<T> data) {
        this.data = data;
    }

    public static MultiResponse buildFailure(String errCode, String errMessage) {
        MultiResponse response = new MultiResponse();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public static MultiResponse buildSuccess(){
        MultiResponse response = new MultiResponse();
        response.setSuccess(true);
        return response;
    }

}