package com.im.controller;

import com.im.services.hotel.HotelService;
import com.im.services.hotel.SearchHotelsException;
import com.im.services.hotel.types.Room;
import com.im.services.hotel.types.SearchHotelsRequest;
import com.im.services.hotel.types.SearchHotelsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Response;
import java.time.LocalDate;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

@RestController
public class HotelController {

    private final HotelService hotelServiceClient;

    @Autowired
    public HotelController(HotelService hotelServiceClient) {
        this.hotelServiceClient = hotelServiceClient;
    }

    @RequestMapping("/search-hotels")
    public SearchHotelsResponse searchHotels() {
        System.out.println("Invoking searchHotels...");
        SearchHotelsRequest request = null;
        try {
            request = createRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SearchHotelsResponse searchHotelsResponse = null;
	//long time = System.currentTimeMillis();
        try {
            searchHotelsResponse = hotelServiceClient.searchHotels(request);
	    //System.out.println((System.currentTimeMillis() - time) + "!!!!!!!!!!!!!!!!!!!!!!!!");
        } catch (SearchHotelsException e) {
            e.printStackTrace();
        }

        return searchHotelsResponse;
    }

    @RequestMapping("/search-hotels-async-non-blocking-polling")
    public SearchHotelsResponse searchHotelsAsyncNonBlockingPolling() {
        //System.out.println("Invoking searchHotelsAsync with the non-blocking polling approach...");
        SearchHotelsRequest request = null;
        try {
            request = createRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Response<SearchHotelsResponse> response = hotelServiceClient.searchHotelsAsync(request);
        // Before attempting to get the result, check whether the response has arrived by calling the non-blocking
        while (!response.isDone()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        SearchHotelsResponse searchHotelsResponse = null;
        try {
            searchHotelsResponse = response.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return searchHotelsResponse;
    }

    @RequestMapping("/search-hotels-async-blocking-polling")
    public SearchHotelsResponse searchHotelsAsyncBlockingPolling() {
        //System.out.println("Invoking searchHotelsAsync with the blocking polling approach...");
        SearchHotelsRequest request = null;
        try {
            request = createRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Response<SearchHotelsResponse> response = hotelServiceClient.searchHotelsAsync(request);
        SearchHotelsResponse searchHotelsResponse = null;
        try {
            // Block until the response arrives (optionally specifying a timeout)
            long time = System.currentTimeMillis();
            searchHotelsResponse = response.get(60L, java.util.concurrent.TimeUnit.SECONDS);
            System.out.println((System.currentTimeMillis() - time) + "!!!!!!!!!!!!!!!!!!!!!!!!");
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        return searchHotelsResponse;
    }

    @RequestMapping("/search-hotels-async-callback")
    public SearchHotelsResponse searchHotelsAsyncCallback() {
        TestAsyncHandler asyncHandler = new TestAsyncHandler();
        //System.out.println("Invoking searchHotelsAsync using callback object...");
        SearchHotelsRequest request = null;
        try {
            request = createRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Future<?> response = hotelServiceClient.searchHotelsAsync(request, asyncHandler);
        while (!response.isDone()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return asyncHandler.getResponse();
    }

    private SearchHotelsRequest createRequest() throws Exception {
        SearchHotelsRequest request = new SearchHotelsRequest();
        request.setCity(123);

        LocalDate today = LocalDate.now();
        LocalDate checkInDate = today.plusMonths(1);
        LocalDate checkOutDate = checkInDate.plusDays(7);

        XMLGregorianCalendar checkIn = DatatypeFactory.newInstance().newXMLGregorianCalendar(checkInDate.toString());
        XMLGregorianCalendar checkOut = DatatypeFactory.newInstance().newXMLGregorianCalendar(checkOutDate.toString());

        request.setCheckIn(checkIn);
        request.setCheckOut(checkOut);

        Room room = new Room();
        room.getChildAges().add(10);
        request.getRooms().add(room);

        return request;
    }
}
