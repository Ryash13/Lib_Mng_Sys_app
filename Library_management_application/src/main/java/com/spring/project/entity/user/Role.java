package com.spring.project.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.project.entity.CommonAttributes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "lib_mng_app_auth_master")
public class Role extends CommonAttributes {

    @Column(unique = true, nullable = false, updatable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
