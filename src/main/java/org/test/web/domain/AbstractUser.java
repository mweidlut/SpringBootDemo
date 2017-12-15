package org.test.web.domain;

/**
 * User: weimeng
 * Date: 2017/11/7 16:09
 */
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
