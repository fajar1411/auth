package com.example.auth.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_m_role")
public class Role {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
   
     @JsonIgnore
    @OneToMany(mappedBy = "role")
    private Set<TrAmartek> trAmarteks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TrAmartek> getTrAmarteks() {
        return trAmarteks;
    }

    public void setTrAmarteks(Set<TrAmartek> trAmarteks) {
        this.trAmarteks = trAmarteks;
    }

    
}
