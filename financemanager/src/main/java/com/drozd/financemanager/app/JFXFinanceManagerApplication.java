package com.drozd.financemanager.app;

import com.drozd.financemanager.app.model.Event;
import com.drozd.financemanager.app.model.Instalment;
import com.drozd.financemanager.app.model.Person;
import com.drozd.financemanager.app.repository.EventRepository;
import com.drozd.financemanager.app.repository.InstalmentRepository;
import com.drozd.financemanager.app.repository.PersonRepository;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

public class JFXFinanceManagerApplication extends Application {
    private static ConfigurableApplicationContext applicationContext;

    private EntityManagerFactory entityManagerFactory;
    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(FinanceManagerApplication.class).run();
//        PersonRepository personRepository = applicationContext.getBean(PersonRepository.class);
//        Person p = new Person("Mike", "White", new ArrayList<>());
//        personRepository.save(p);
//        EventRepository eventRepository = applicationContext.getBean(EventRepository.class);
//        Event e = new Event("Spotkanie", "Jezioro", LocalDateTime.now(), new ArrayList<>());
//        eventRepository.save(e);
//        InstalmentRepository instalmentRepository = applicationContext.getBean(InstalmentRepository.class);
//        Instalment i = new Instalment(e, p, 1, LocalDateTime.now(), 1.0, false, LocalDateTime.now());
//        Instalment j = new Instalment(e, p, 2, LocalDateTime.of(2021, Month.APRIL, 10, 15, 30), 1.0, false, LocalDateTime.now());
//        Instalment k = new Instalment(e, p, 3, LocalDateTime.of(2023, Month.APRIL, 10, 15, 30), 1.0, false, LocalDateTime.now());
//        instalmentRepository.save(i);
//        instalmentRepository.save(j);
//        instalmentRepository.save(k);
    }

    @Override
    public void start(Stage primaryStage) {
        applicationContext.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    public static ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }


    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public Stage getStage() {
            return (Stage) getSource();
        }
    }
}
