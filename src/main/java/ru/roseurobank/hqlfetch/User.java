package ru.roseurobank.hqlfetch;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zadorozhniy276 on 17.03.2015.
 */
@Entity
//@org.hibernate.annotations.Proxy(lazy = false)
public class User {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @org.hibernate.annotations.LazyCollection(
//            LazyCollectionOption.EXTRA
//    )
//    @JoinTable(name = "USER_ROLE",
//            joinColumns = @JoinColumn(name = "USER_ID"),
//            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    @JoinColumn(name = "ROLE_ID")
//    @org.hibernate.annotations.BatchSize(size = 2)
    @org.hibernate.annotations.Fetch(
            FetchMode.JOIN
    )
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public Long getId() {
        return id;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRols() {
        return roles;
    }

    public void setRols(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        role.setUsers(this);
        roles.add(role);
    }

    @Override
    public String toString() {
        return "Id => " + id + "; Name => " + name;
    }
}
