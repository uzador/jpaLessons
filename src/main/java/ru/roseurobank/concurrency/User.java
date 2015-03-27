package ru.roseurobank.concurrency;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author zadorozhniy276
 */
@Entity
@Table(name = "TAB_USER")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USER_NAME")
    private String name;

//    @Column(name = "USER_AGE")
//    private int age;

    @Version
    @Column(name = "OBJ_VERSION")
    private int version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }

    public int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "\nId => " + id + " Name = > " + name + /*" Age = > " + age +*/ " Version = > " + version;
    }

}
