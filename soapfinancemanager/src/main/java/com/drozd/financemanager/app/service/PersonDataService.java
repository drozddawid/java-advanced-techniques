package com.drozd.financemanager.app.service;

import com.drozd.financemanager.app.model.Person;
import com.drozd.financemanager.app.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonDataService {
    PersonRepository personRepository;

    @Autowired
    public PersonDataService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void addPerson(Person person){
        personRepository.save(person);
    }

    public void modifyPerson(Person person){
        personRepository.save(person);
    }

    public void removePerson(Person person){
        personRepository.delete(person);
    }

    public Optional<Person> findPersonByID(Integer id){
        return personRepository.findById(id);
    }

    public List<Person> findPersonsByIDs(Iterable<Integer> ids){
        List<Person> result = new ArrayList<>();
        personRepository.findAllById(ids).forEach(result::add);
        return result;
    }

    public List<Person> getAllPersons(){
        List<Person> result = new ArrayList<>();
        personRepository.findAll().forEach(result::add);
        return result;
    }
}
