package com.drozd.financemanager.app.soap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "person")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class SOAPPerson {
    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "first_name", required = true)
    private String firstName;
    @XmlElement(name = "last_name", required = true)
    private String lastName;
}
