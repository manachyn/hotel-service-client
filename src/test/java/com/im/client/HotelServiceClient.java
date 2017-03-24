package com.im.client;

import com.im.services.hotel.HotelService;
import com.im.services.hotel.HotelServiceService;
import com.im.services.hotel.SearchHotelsException;
import com.im.services.hotel.types.Hotel;
import com.im.services.hotel.types.Room;
import com.im.services.hotel.types.SearchHotelsRequest;
import com.im.services.hotel.types.SearchHotelsResponse;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class HotelServiceClient {

    private static final QName SERVICE_NAME = new QName("http://im.com/services/hotel", "HotelService_Service");

    private HotelServiceClient() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = HotelServiceService.WSDL_LOCATION;

        HotelServiceService ss = new HotelServiceService(wsdlURL, SERVICE_NAME);
        HotelService port = ss.getHotelService();

        System.out.println("Invoking searchHotels...");
        SearchHotelsRequest request  = new SearchHotelsRequest();
        request.setCity(123);
        GregorianCalendar gc = new GregorianCalendar();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse("2017-03-01");
        gc.setTime(date);
        XMLGregorianCalendar xgc1 = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        request.setCheckIn(xgc1);
        gc.add(Calendar.DATE, 7);
        XMLGregorianCalendar xgc2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        request.setCheckOut(xgc2);

        Room room = new Room();
        room.setAdults(1);
        room.setChildren(0);

        try {
            SearchHotelsResponse response = port.searchHotels(request);
            List<Hotel> hotels = response.getSearchHotelsResult();
            System.out.println(hotels);
            System.out.println(response);
        } catch (SearchHotelsException e) {
            System.out.println(e.toString());
        }
    }
}
