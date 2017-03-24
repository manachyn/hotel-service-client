package com.im;

import com.im.services.hotel.HotelService;
import com.im.services.hotel.HotelServiceService;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.namespace.QName;
import java.net.URL;

@Configuration
public class HotelServiceClientConfiguration {

    private static final QName SERVICE_NAME = new QName("http://im.com/services/hotel", "HotelService_Service");

    @Value("${hotel.service.address}")
    private String hotelServiceAddress;

    @Bean
    public HotelService hotelServiceClient() {
        URL wsdlURL = HotelServiceService.WSDL_LOCATION;
        HotelServiceService ss = new HotelServiceService(wsdlURL, SERVICE_NAME);

        return ss.getHotelService();
    }

//    @Bean
//    public JaxWsProxyFactoryBean hotelServiceClientFactory() {
//        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//        factory.getInInterceptors().add(new LoggingInInterceptor());
//        factory.getOutInterceptors().add(new LoggingOutInterceptor());
//        factory.setServiceClass(HotelService.class);
//        factory.setAddress(hotelServiceAddress;);
//
//        return factory;
//    }
//
//    @Bean
//    public HotelService hotelServiceClient() {
//        return (HotelService) hotelServiceClientFactory().create();
//    }
//
//    @Bean(name = "hotelServiceClientProxyBean")
//    public HotelService hotelServicePortType() {
//        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
//        jaxWsProxyFactoryBean.setServiceClass(HotelService.class);
//        jaxWsProxyFactoryBean.setAddress(hotelServiceAddress);
//
//        return (HotelService) jaxWsProxyFactoryBean.create();
//    }
}
