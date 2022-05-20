package com.drozd.financemanager.app.soap.web.interfaces;

import com.drozd.financemanager.app.soap.model.SOAPPerson;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface PersonSOAPService {
    @WebMethod
    void addPerson(@WebParam(name = "person") SOAPPerson person);

    @WebMethod
    void modifyPerson(@WebParam(name = "person") SOAPPerson person);

    @WebMethod
    void removePerson(@WebParam(name = "id") Integer id) throws IllegalArgumentException;

    @WebMethod
    SOAPPerson findPersonByID(@WebParam(name = "id") Integer id) throws IllegalArgumentException;

    @WebMethod
    List<SOAPPerson> getAllPeople();
}
