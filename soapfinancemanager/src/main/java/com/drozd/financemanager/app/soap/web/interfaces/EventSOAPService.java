package com.drozd.financemanager.app.soap.web.interfaces;

import com.drozd.financemanager.app.model.Event;
import com.drozd.financemanager.app.soap.model.SOAPEvent;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface EventSOAPService {
    @WebMethod
    void addEvent(@WebParam(name = "event") SOAPEvent event);

    @WebMethod
    void updateEvent(@WebParam(name = "event") SOAPEvent event);

    @WebMethod
    void removeEvent(@WebParam(name = "id") Integer id)
            throws Exception;

    @WebMethod
    SOAPEvent getEvent(@WebParam(name = "id") Integer id)
            throws Exception;

    @WebMethod
    List<SOAPEvent> getAllEvents();
}
