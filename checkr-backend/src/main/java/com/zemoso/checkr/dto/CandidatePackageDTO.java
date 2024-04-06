package com.zemoso.checkr.dto;

public class CandidatePackageDTO {
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

    public CandidatePackageDTO() {
    }

    public CandidatePackageDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CandidatePackageDTO(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CandidatePackageDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
