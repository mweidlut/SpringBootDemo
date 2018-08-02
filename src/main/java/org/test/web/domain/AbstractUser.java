package org.test.web.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractUser {

    @Column
    private String sex; // M/F

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "AbstractUser{" +
                "sex='" + sex + '\'' +
                '}';
    }
}
