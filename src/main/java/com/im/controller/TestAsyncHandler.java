package com.im.controller;

import com.im.services.hotel.types.SearchHotelsResponse;

import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;

public class TestAsyncHandler implements AsyncHandler<SearchHotelsResponse> {

    private SearchHotelsResponse reply;

    public void handleResponse(Response<SearchHotelsResponse> response) {
        try {
            reply = response.get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public SearchHotelsResponse getResponse() {
        return reply;
    }
}
