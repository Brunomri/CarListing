package com.bruno.carlisting.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private Integer roleId;

    private String username;

    private String password;

    private String displayName;

    private String contact;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Listing> userListings = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Car> cars = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name = "userToRole_id"),
    inverseJoinColumns = @JoinColumn(name = "roleToUser_id"))
    private List<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(Integer roleId, String username, String password, String displayName, String contact) {
        this.roleId = roleId;
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.contact = contact;
    }

}
