package com.spring.project.entity.user;

import com.spring.project.entity.CommonAttributes;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "lib_mng_app_account_activation_token")
public class Token extends CommonAttributes {

    @Column(unique = true, updatable = false, nullable = false)
    private String token;
    private LocalDateTime expiresAt;

    @Column(columnDefinition = "boolean default false")
    private boolean activated;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
