package com.zemoso.checkr.dto;

public class StatusDTO {
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

    public StatusDTO() {
    }

    public StatusDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public StatusDTO(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StatusDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
