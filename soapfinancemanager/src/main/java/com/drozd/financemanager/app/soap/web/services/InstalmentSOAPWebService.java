package com.drozd.financemanager.app.soap.web.services;

import com.drozd.financemanager.app.JFXFinanceManagerApplication;
import com.drozd.financemanager.app.model.Event;
import com.drozd.financemanager.app.model.Instalment;
import com.drozd.financemanager.app.model.Person;
import com.drozd.financemanager.app.service.EventDataService;
import com.drozd.financemanager.app.service.InstalmentDataService;
import com.drozd.financemanager.app.service.PersonDataService;
import com.drozd.financemanager.app.soap.model.SOAPInstalment;
import com.drozd.financemanager.app.soap.web.interfaces.InstalmentSOAPService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebService(endpointInterface = "com.drozd.financemanager.app.soap.web.interfaces.InstalmentSOAPService")
public class InstalmentSOAPWebService implements InstalmentSOAPService {

    private final EventDataService eventDataService;
    private final PersonDataService personDataService;
    private final InstalmentDataService instalmentDataService;

    public InstalmentSOAPWebService() {
        instalmentDataService = JFXFinanceManagerApplication.getApplicationContext().getBean(InstalmentDataService.class);
        eventDataService = JFXFinanceManagerApplication.getApplicationContext().getBean(EventDataService.class);
        personDataService = JFXFinanceManagerApplication.getApplicationContext().getBean(PersonDataService.class);
    }

    private Event fetchEventOrThrowExceptionIfDoesntExist(Integer id) throws IllegalArgumentException {
        Optional<Event> optionalEvent = eventDataService.findEventByID(id);
        if (optionalEvent.isEmpty()) throw new IllegalArgumentException("Event doesn't exist. Given event ID: " + id);
        return optionalEvent.get();
    }

    private Person fetchPersonOrThrowExceptionIfDoesntExist(Integer id) throws IllegalArgumentException {
        Optional<Person> optionalPerson = personDataService.findPersonByID(id);
        if (optionalPerson.isEmpty())
            throw new IllegalArgumentException("Person doesn't exist. Given person ID: " + id);
        return optionalPerson.get();
    }

    @Override
    @WebMethod
    public void addInstalment(SOAPInstalment instalment) throws IllegalArgumentException {
        Event event = fetchEventOrThrowExceptionIfDoesntExist(instalment.getEventID());
        Person person = fetchPersonOrThrowExceptionIfDoesntExist(instalment.getPersonID());
        instalmentDataService.addInstalment(new Instalment(event, person, instalment.getInstalmentNumber(),
                instalment.getDeadlineLocalDateTime(), instalment.getPrice(), instalment.getWasPaid(),
                instalment.getWhenWasPaidLocalDateTime()));
    }

    @Override
    @WebMethod
    public void updateInstalment(SOAPInstalment instalment) throws IllegalArgumentException {
        Event event = fetchEventOrThrowExceptionIfDoesntExist(instalment.getEventID());
        Person person = fetchPersonOrThrowExceptionIfDoesntExist(instalment.getPersonID());

        instalmentDataService.modifyInstalment(new Instalment(event, person,
                instalment.getInstalmentNumber(), instalment.getDeadlineLocalDateTime(), instalment.getPrice(),
                instalment.getWasPaid(), instalment.getWhenWasPaidLocalDateTime()));
    }

    @Override
    @WebMethod
    public void removeInstalment(Integer id) throws IllegalArgumentException {
        if(id == null || id < 0) throw new IllegalArgumentException("Given id is not valid.");
        Optional<Instalment> optionalInstalment = instalmentDataService.findInstalmentByID(id);
        if (optionalInstalment.isEmpty())
            throw new IllegalArgumentException("Instalment already doesn't exist. Instalment ID: " + id);
        instalmentDataService.removeInstalment(optionalInstalment.get());
    }

    @Override
    @WebMethod
    public SOAPInstalment findInstalmentByID(Integer id) throws IllegalArgumentException{
        if(id == null || id < 0) throw new IllegalArgumentException("Given id is not valid.");
        Optional<Instalment> optionalInstalment = instalmentDataService.findInstalmentByID(id);
        if (optionalInstalment.isEmpty())
            throw new IllegalArgumentException("Instalment doesn't exist. Given instalment ID: " + id);
        Instalment instalment = optionalInstalment.get();
        return new SOAPInstalment(instalment.getId(), instalment.getEvent().getId(),
                instalment.getPerson().getId(), instalment.getInsalmentNumber(), instalment.getDeadline(),
                instalment.getPrice(), instalment.getWasPaid(), instalment.getWhenWasPaid());
    }

    @Override
    @WebMethod
    public List<SOAPInstalment> getAllInstalments() {
        List<Instalment> instalments = instalmentDataService.getAllInstalments();
        return instalmentsListToSOAPInstalmentsList(instalments);
    }

    @Override
    @WebMethod
    public List<SOAPInstalment> getDelayedInstalments(String presentTime) {
        List<Instalment> instalments = instalmentDataService.getDelayedInstalments(LocalDateTime.parse(presentTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return instalmentsListToSOAPInstalmentsList(instalments);
    }

    private List<SOAPInstalment> instalmentsListToSOAPInstalmentsList(List<Instalment> instalments) {
        List<SOAPInstalment> soapInstalments = new ArrayList<>(instalments.size());
        instalments.forEach(i -> {
            soapInstalments.add(new SOAPInstalment(i.getId(), i.getEvent().getId(), i.getPerson().getId(),
                    i.getInsalmentNumber(), i.getDeadline(), i.getPrice(), i.getWasPaid(), i.getWhenWasPaid()));
        });
        return soapInstalments;
    }
}
