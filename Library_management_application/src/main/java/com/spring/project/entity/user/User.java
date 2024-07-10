package com.spring.project.entity.user;

import com.spring.project.entity.CommonAttributes;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "lib_mng_app_users")
public class User extends CommonAttributes implements Principal, UserDetails {

    @NotNull(message = "Firstname cannot be blank or null")
    private String firstname;

    @NotNull(message = "Lastname cannot be blank or null")
    private String lastname;

    @Column(unique = true, nullable = false)
    @NotNull(message = "Email cannot be blank or null")
    @Email(message = "Enter a valid Email ID")
    private String email;

    @Min(value = 6)
    @Max(value = 16)
    @NotNull(message = "Password cannot be blank or null")
    private String password;

    @Column(unique = true, nullable = false, updatable = false)
    private String uuid;

    private Date dateOfBirth;

    @Column(columnDefinition = "boolean default false")
    private boolean enabled;

    @Column(columnDefinition = "boolean default false")
    private boolean logicallyDeleted;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private Token activationToken;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isLogicallyDeleted() {
        return logicallyDeleted;
    }

    public void setLogicallyDeleted(boolean logicallyDeleted) {
        this.logicallyDeleted = logicallyDeleted;
    }

    public Token getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(Token activationToken) {
        this.activationToken = activationToken;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String getName() {
        return lastname + ", " + firstname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
