package ru.roseurobank.hqlfetch;

import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;

/**
 * Created by zadorozhniy276 on 17.03.2015.
 */
@Entity
public class Role {
    @Id
    @GeneratedValue
    @Column(name = "ROLE_ID")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User users;

    public Role() {
    }

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

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "ID => " + id + "; Name => " + name;
    }
}
