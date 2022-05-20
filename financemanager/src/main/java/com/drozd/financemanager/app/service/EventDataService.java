package com.drozd.financemanager.app.service;

import com.drozd.financemanager.app.model.Event;
import com.drozd.financemanager.app.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventDataService {

    EventRepository eventRepository;

    @Autowired
    public EventDataService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void addEvent(Event event){
        eventRepository.save(event);
    }

    public void modifyEvent(Event event){
        eventRepository.save(event);
    }

    public void removeEvent(Event event){
        eventRepository.delete(event);
    }

    public Optional<Event> findEventByID(Integer id){
        return eventRepository.findById(id);
    }

    public List<Event> findEventsByIDs(Iterable<Integer> ids){
        List<Event> result = new ArrayList<>();
        eventRepository.findAllById(ids).forEach(result::add);
        return result;
    }

    public List<Event> getAllEvents(){
        List<Event> result = new ArrayList<>();
        eventRepository.findAll().forEach(result::add);
        return result;
    }

}
