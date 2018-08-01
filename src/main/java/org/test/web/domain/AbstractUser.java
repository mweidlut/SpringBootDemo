package org.test.web.domain;


public abstract class AbstractUser {

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
