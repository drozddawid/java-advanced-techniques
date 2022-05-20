package com.drozd.financemanager.app.soap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.xml.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@XmlRootElement(name = "event")//, namespace = "http://localhost:8100/events")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SOAPEvent {
    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "title", required = true)
    private String title;
    @XmlElement(name = "place", required = true)
    private String place;
    @XmlElement(name = "date", required = true)
    private String date;

    public SOAPEvent(Integer id, String title, String place, LocalDateTime date) {
        this.id = id;
        this.title = title;
        this.place = place;
        this.date = date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public void setDate(LocalDateTime date) {
        this.date = date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
