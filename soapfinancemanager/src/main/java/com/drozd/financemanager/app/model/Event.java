package com.drozd.financemanager.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Table(name = "events")
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "place")
    private String place;
    @Column(name = "date")
    private LocalDateTime date;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Instalment> instalments;


    public Event() {
    }

    public Event(String title, String place, LocalDateTime date, List<Instalment> instalments) {
        this.title = title;
        this.place = place;
        this.date = date;
        this.instalments = instalments;
    }

    public Event(Integer id, String title, String place, LocalDateTime date, List<Instalment> instalments) {
        this.id = id;
        this.title = title;
        this.place = place;
        this.date = date;
        this.instalments = instalments;
    }

    public void addInsalment(Instalment instalment) {
        instalments.add(instalment);
        instalment.setEvent(this);
    }

    public void removeInsalment(Instalment instalment) {
        instalments.remove(instalment);
        instalment.setEvent(null);
    }

    @Override
    public String toString() {
        return "Event: " + title +
                "\nPlace: " + place +
                "\nDate: " + date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm"));
    }
}
