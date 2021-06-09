package org.test.web.dto;

public class CustomerDto {
    private String firstName;
    private String age;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "firstName='" + firstName + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
