package com.zemoso.checkr.entities;

import jakarta.persistence.*;

@Entity
@Table(name="adjudication")
public class Adjudication {
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

    public Adjudication() {
    }

    public Adjudication(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Adjudication(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Adjudication{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
