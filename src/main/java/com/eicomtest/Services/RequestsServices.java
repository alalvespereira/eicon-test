package com.eicomtest.Services;

import com.eicomtest.models.Requests;

public interface RequestsServices {
    String findAllRequests();
    String findIdRequests(int n);
    String findDateRequests(String d);
    boolean isRequestExist(Requests r);
    void saveRequest(Requests r);
}
