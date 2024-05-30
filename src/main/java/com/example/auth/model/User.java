package com.example.auth.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_m_user")
public class User {
    @Id
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id", nullable = false, referencedColumnName = "id")
    private Amartek amartek;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;
    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "token")
    private String tokenJwt;

    public String getTokenJwt() {
        return tokenJwt;
    }

    public void setTokenJwt(String tokenJwt) {
        this.tokenJwt = tokenJwt;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<TrAmartek> trAmarteks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Amartek getAmartek() {
        return amartek;
    }

    public void setAmartek(Amartek amartek) {
        this.amartek = amartek;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Set<TrAmartek> getTrAmarteks() {
        return trAmarteks;
    }

    public void setTrAmarteks(Set<TrAmartek> trAmarteks) {
        this.trAmarteks = trAmarteks;
    }

    public User() {
    }

}
