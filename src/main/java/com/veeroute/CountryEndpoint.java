package com.veeroute;

import io.spring.guides.gs_producing_web_service.SayHiRequest;
import io.spring.guides.gs_producing_web_service.SayHiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import io.spring.guides.gs_producing_web_service.GetCountryRequest;
import io.spring.guides.gs_producing_web_service.GetCountryResponse;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

@Endpoint
public class CountryEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private CountryRepository countryRepository;

    @Autowired
    public CountryEndpoint(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        response.setCountry(countryRepository.findCountry(request.getName()));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "sayHiRequest")
    @ResponsePayload
    public JAXBElement<SayHiResponse> sayHi(@RequestPayload JAXBElement<SayHiRequest> sayHi) {
        System.out.println("sayHi() called");

        SayHiResponse sh =  new SayHiResponse();
        sh.setReturn("hi, " + sayHi.getValue().getText());
        QName qname = new QName(NAMESPACE_URI, "sayHiResponse");
        return new JAXBElement<SayHiResponse>(qname, SayHiResponse.class, sh);
    }
}