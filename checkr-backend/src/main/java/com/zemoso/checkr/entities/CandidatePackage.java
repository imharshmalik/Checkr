package com.zemoso.checkr.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CandidatePackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CandidatePackage() {
    }

    public CandidatePackage(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CandidatePackage(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CandidatePackage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
