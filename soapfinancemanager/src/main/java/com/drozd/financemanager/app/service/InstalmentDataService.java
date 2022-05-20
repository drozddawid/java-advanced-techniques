package com.drozd.financemanager.app.service;

import com.drozd.financemanager.app.model.Instalment;
import com.drozd.financemanager.app.repository.InstalmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.xml.ws.WebServiceProvider;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InstalmentDataService {
    InstalmentRepository instalmentRepository;

    @Autowired
    public InstalmentDataService(InstalmentRepository instalmentRepository) {
        this.instalmentRepository = instalmentRepository;
    }

    public void addInstalment(Instalment instalment){
        instalmentRepository.save(instalment);
    }

    public void modifyInstalment(Instalment instalment){
        instalmentRepository.save(instalment);
    }

    public void removeInstalment(Instalment instalment){
        instalmentRepository.delete(instalment);
    }

    public Optional<Instalment> findInstalmentByID(Integer id){
        return instalmentRepository.findById(id);
    }

    public List<Instalment> findInstalmentsByIDs(Iterable<Integer> ids){
        List<Instalment> result = new ArrayList<>();
        instalmentRepository.findAllById(ids).forEach(result::add);
        return result;
    }

    public List<Instalment> getAllInstalments(){
        List<Instalment> result = new ArrayList<>();
        instalmentRepository.findAll().forEach(result::add);
        return result;
    }

    public List<Instalment> getDelayedInstalments(LocalDateTime presentTime){
        List<Instalment> result = new ArrayList<>();
        instalmentRepository.getDelayedInstalments(presentTime).forEach(result::add);
        return result;
    }

}
