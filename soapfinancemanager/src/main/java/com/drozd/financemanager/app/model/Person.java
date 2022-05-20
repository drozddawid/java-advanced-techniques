package com.drozd.financemanager.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "people")
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Integer id;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Instalment> instalments;

    public Person(String firstName, String lastName, List<Instalment> instalments) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.instalments = instalments;
    }

    public void addInsalment(Instalment instalment){
        instalments.add(instalment);
        instalment.setPerson(this);
    }

    public void removeInsalment(Instalment instalment){
        instalments.remove(instalment);
        instalment.setPerson(null);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
