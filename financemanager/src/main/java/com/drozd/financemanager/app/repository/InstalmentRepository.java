package com.drozd.financemanager.app.repository;

import com.drozd.financemanager.app.model.Instalment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InstalmentRepository extends CrudRepository<Instalment, Integer> {
    @Query(value = "select * from instalments i where i.was_paid=false and i.deadline < :presentTime",
            nativeQuery = true)
    Iterable<Instalment> getDelayedInstalments(@Param("presentTime") LocalDateTime presentTime);
}
