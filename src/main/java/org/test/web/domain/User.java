package org.test.web.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Table(name = "USER")
@Entity
public class User extends AbstractUser implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column
    private String id;

    @Column(length = 40)
    private String name;

    @Column
    private LocalDate birthday;

    @Column
    private LocalDateTime createTime = LocalDateTime.now();

    private List<User> child = new ArrayList<>();

    public User(){}

    public User(String name, String sex, LocalDate birthday){
        this.name = name;
        super.setSex(sex);
        this.birthday = birthday;
    }

    public User children(User... children){
        this.child.addAll(Arrays.asList(children));
        return this;
    }

    public List<User> getChild() {
        return child;
    }

    public void setChild(List<User> child) {
        this.child = child;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", createTime=" + createTime +
                "} " + super.toString();
    }
}
