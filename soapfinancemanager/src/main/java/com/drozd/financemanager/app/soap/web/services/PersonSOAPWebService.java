package com.drozd.financemanager.app.soap.web.services;

import com.drozd.financemanager.app.JFXFinanceManagerApplication;
import com.drozd.financemanager.app.model.Person;
import com.drozd.financemanager.app.service.PersonDataService;
import com.drozd.financemanager.app.soap.model.SOAPPerson;
import com.drozd.financemanager.app.soap.web.interfaces.PersonSOAPService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@WebService(endpointInterface = "com.drozd.financemanager.app.soap.web.interfaces.PersonSOAPService")
public class PersonSOAPWebService implements PersonSOAPService {
    private final PersonDataService dataService;

    public PersonSOAPWebService() {
        dataService = JFXFinanceManagerApplication.getApplicationContext().getBean(PersonDataService.class);
    }

    @Override
    @WebMethod
    public void addPerson(SOAPPerson person) {
        dataService.addPerson(new Person(person.getFirstName(), person.getLastName(), new ArrayList<>()));
    }

    @Override
    @WebMethod
    public void modifyPerson(SOAPPerson person) {
        dataService.modifyPerson(new Person(person.getId(), person.getFirstName(), person.getLastName(), new ArrayList<>()));
    }

    @Override
    @WebMethod
    public void removePerson(Integer id) throws IllegalArgumentException{
        if(id == null || id < 0) throw new IllegalArgumentException("Invalid person id. Given ID: " + id);
        Optional<Person> optionalPerson = dataService.findPersonByID(id);
        if(optionalPerson.isEmpty())
            throw new IllegalArgumentException("Person already doesn't exist. Given ID: " + id);
        dataService.removePerson(optionalPerson.get());
    }

    @Override
    @WebMethod
    public SOAPPerson findPersonByID(Integer id) throws IllegalArgumentException{
        if(id == null || id < 0) throw new IllegalArgumentException("Invalid person id. Given ID: " + id);
        Optional<Person> optionalPerson = dataService.findPersonByID(id);
        if(optionalPerson.isEmpty())
            throw new IllegalArgumentException("Person doesn't exist. Given ID: " + id);
        Person person = optionalPerson.get();
        return new SOAPPerson(person.getId(), person.getFirstName(), person.getLastName());
    }

    @Override
    @WebMethod
    public List<SOAPPerson> getAllPeople() {
        List<Person> people = dataService.getAllPersons();
        List<SOAPPerson> soapPeople = new ArrayList<>(people.size());
        people.forEach(p -> { soapPeople.add(new SOAPPerson(p.getId(), p.getFirstName(), p.getLastName()));});
        return soapPeople;
    }
}
