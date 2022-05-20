package com.drozd.financemanager.app.soap.web.interfaces;

import com.drozd.financemanager.app.soap.model.SOAPInstalment;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface InstalmentSOAPService {
    @WebMethod
    void addInstalment(@WebParam(name = "instalment") SOAPInstalment instalment) throws IllegalArgumentException;

    @WebMethod
    void updateInstalment(@WebParam(name = "instalment") SOAPInstalment instalment) throws IllegalArgumentException;

    @WebMethod
    void removeInstalment(@WebParam(name = "id") Integer id) throws IllegalArgumentException;

    @WebMethod
    SOAPInstalment findInstalmentByID(@WebParam(name = "id") Integer id);

    @WebMethod
    List<SOAPInstalment> getAllInstalments();

    @WebMethod
    List<SOAPInstalment> getDelayedInstalments(@WebParam(name = "present_time") String presentTime);


}
