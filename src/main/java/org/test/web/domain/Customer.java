package org.test.web.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String age;

    protected Customer() {
    }

    public Customer(String firstName, String age) {
        this.firstName = firstName;
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', age='%s']",
                id, firstName, age);
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getAge() {
        return age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAge(String lastName) {
        this.age = lastName;
    }
}