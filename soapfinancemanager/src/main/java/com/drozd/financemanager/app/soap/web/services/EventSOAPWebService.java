package com.drozd.financemanager.app.soap.web.services;

import com.drozd.financemanager.app.JFXFinanceManagerApplication;
import com.drozd.financemanager.app.model.Event;
import com.drozd.financemanager.app.service.EventDataService;
import com.drozd.financemanager.app.soap.model.SOAPEvent;
import com.drozd.financemanager.app.soap.web.interfaces.EventSOAPService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebService(endpointInterface = "com.drozd.financemanager.app.soap.web.interfaces.EventSOAPService")
public class EventSOAPWebService implements EventSOAPService {

    private final EventDataService dataService;

    public EventSOAPWebService() {
        this.dataService = JFXFinanceManagerApplication.getApplicationContext().getBean(EventDataService.class);
    }

    @Override
    @WebMethod
    public void addEvent(SOAPEvent event) {
        dataService.addEvent(new Event(event.getTitle(), event.getPlace(), LocalDateTime.parse(event.getDate(),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME), new ArrayList<>()));
    }

    @Override
    @WebMethod
    public void updateEvent(SOAPEvent event) {
        dataService.modifyEvent(new Event(event.getId(), event.getTitle(), event.getPlace(), LocalDateTime.parse(event.getDate(),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME), new ArrayList<>()));
    }

    @Override
    @WebMethod
    public void removeEvent(Integer id) throws Exception {
        if (id == null || id < 0) throw new IllegalArgumentException("Given id is not valid.");
        Optional<Event> eventToRemove = dataService.findEventByID(id);
        if (eventToRemove.isEmpty()) throw new Exception("Event not found.");
        dataService.removeEvent(eventToRemove.get());
    }

    @Override
    @WebMethod
    public SOAPEvent getEvent(Integer id) throws Exception {
        if (id == null || id < 0) throw new IllegalArgumentException("Given id is not valid.");
        Optional<Event> optionalEvent = dataService.findEventByID(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            return new SOAPEvent(event.getId(), event.getTitle(), event.getPlace(), event.getDate());
        } else {
            throw new Exception("Event not found.");
        }
    }

    @Override
    @WebMethod
    public List<SOAPEvent> getAllEvents() {
        List<Event> events = dataService.getAllEvents();
        List<SOAPEvent> soapEvents = new ArrayList<>(events.size());
        events.forEach(e -> {
            soapEvents.add(new SOAPEvent(e.getId(), e.getTitle(), e.getPlace(), e.getDate()));
        });
        return soapEvents;
    }
}
