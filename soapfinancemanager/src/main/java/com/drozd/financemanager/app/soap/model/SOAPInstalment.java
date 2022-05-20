package com.drozd.financemanager.app.soap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@XmlRootElement(name = "instalment")
@XmlAccessorType(value = XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SOAPInstalment {
    @XmlElement(name = "id", required = true)
    private Integer id;
    @XmlElement(name = "event_id", required = true)
    private Integer eventID;
    @XmlElement(name = "person_id", required = true)
    private Integer personID;
    @XmlElement(name = "instalment_number", required = true)
    private Integer instalmentNumber;
    @XmlElement(name = "deadline", required = true)
    private String deadline;
    @XmlElement(name = "price", required = true)
    private Double price;
    @XmlElement(name = "was_paid", required = true)
    private Boolean wasPaid;
    @XmlElement(name = "when_was_paid", required = false)
    private String whenWasPaid;

    public SOAPInstalment(Integer id, Integer eventID, Integer personID, Integer instalmentNumber, LocalDateTime deadline, Double price, Boolean wasPaid, LocalDateTime whenWasPaid) {
        this.id = id;
        this.eventID = eventID;
        this.personID = personID;
        this.instalmentNumber = instalmentNumber;
        this.deadline = deadline.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.price = price;
        this.wasPaid = wasPaid;
        this.whenWasPaid = whenWasPaid == null ? null : whenWasPaid.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }


    public LocalDateTime getDeadlineLocalDateTime(){
        return LocalDateTime.parse(deadline, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
    public void setDeadline(LocalDateTime deadline){
        this.deadline = deadline.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
    public void setWhenWasPaid(LocalDateTime whenWasPaid){
        this.deadline = whenWasPaid.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
    public LocalDateTime getWhenWasPaidLocalDateTime(){
        if (whenWasPaid == null) return null;
        return LocalDateTime.parse(whenWasPaid, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
