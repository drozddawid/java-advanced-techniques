package com.drozd.financemanager.app;

import com.drozd.financemanager.app.soap.web.services.EventSOAPWebService;
import com.drozd.financemanager.app.soap.web.services.InstalmentSOAPWebService;
import com.drozd.financemanager.app.soap.web.services.PersonSOAPWebService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import javax.persistence.EntityManagerFactory;
import javax.xml.ws.Endpoint;
import java.util.ArrayList;
import java.util.List;

public class JFXFinanceManagerApplication extends Application {
    private static ConfigurableApplicationContext applicationContext;
    private List<Endpoint> soapEndpoints = new ArrayList<>();

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
        soapEndpoints.add(Endpoint.publish("http://localhost:8100/events", new EventSOAPWebService()));
        soapEndpoints.add(Endpoint.publish("http://localhost:8100/instalments", new InstalmentSOAPWebService()));
        soapEndpoints.add(Endpoint.publish("http://localhost:8100/people", new PersonSOAPWebService()));
    }

    @Override
    public void stop() {
        applicationContext.close();
        soapEndpoints.forEach(Endpoint::stop);
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
