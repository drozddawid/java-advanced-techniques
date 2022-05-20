package com.drozd.financemanager.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Table(name = "instalments")
@Entity
public class Instalment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(name = "instalment_number", nullable = false)
    private Integer insalmentNumber;

    @Column(name = "deadline", nullable = false)
    private LocalDateTime deadline;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "was_paid", nullable = false)
    private Boolean wasPaid;

    @Column(name = "when_was_paid", nullable = true)
    private LocalDateTime whenWasPaid;


    public Instalment() {
    }

    public Instalment(Event event, Person person, Integer insalmentNumber, LocalDateTime deadline, Double price, Boolean wasPaid, LocalDateTime whenWasPaid) {
        this.event = event;
        this.person = person;
        this.insalmentNumber = insalmentNumber;
        this.deadline = deadline;
        this.price = price;
        this.wasPaid = wasPaid;
        this.whenWasPaid = whenWasPaid;
    }

    @Override
    public String toString() {
        return
                "Person: " + person.toString() +
                        "\nEvent: " + event.getTitle() + " Place: " + event.getPlace() + " Time: " + event.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")) +
                        "\nInstalment number: " + insalmentNumber +
                        "\nPrice: " + price +
                        "\nDeadline: " + deadline.format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")) +
                        "\nWas paid: " + (wasPaid ? "yes, payment time: " + whenWasPaid.format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")) : "no");
    }
}
