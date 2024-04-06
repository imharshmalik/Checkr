package com.zemoso.checkr.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="candidate")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String location;
    private LocalDate dob;
    private String email;
    private String phone;
    private String zipcode;
    private String socialSecurity;
    @CreationTimestamp
    private LocalDateTime creationDate;
    private String driversLicense;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_id", referencedColumnName = "id")
    private Report report;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(String socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getDriversLicense() {
        return driversLicense;
    }

    public void setDriversLicense(String driversLicense) {
        this.driversLicense = driversLicense;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Candidate() {
    }

    public Candidate(String name) {
        this.name = name;
    }

    public Candidate(CandidateBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.location = builder.location;
        this.dob = builder.dob;
        this.email = builder.email;
        this.phone = builder.phone;
        this.zipcode = builder.zipcode;
        this.socialSecurity = builder.socialSecurity;
        this.creationDate = builder.creationDate;
        this.driversLicense = builder.driversLicense;
        this.report = builder.report;
    }

    public static class CandidateBuilder {
        private int id;
        private String name;
        private String location;
        private LocalDate dob;
        private String email;
        private String phone;
        private String zipcode;
        private String socialSecurity;
        private LocalDateTime creationDate;
        private String driversLicense;
        private Report report;

        public CandidateBuilder() {
            // NO ARGS CONSTRUCTOR
        }

        public CandidateBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public CandidateBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CandidateBuilder setLocation(String location) {
            this.location = location;
            return this;
        }

        public CandidateBuilder setDob(LocalDate dob) {
            this.dob = dob;
            return this;
        }

        public CandidateBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public CandidateBuilder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public CandidateBuilder setZipcode(String zipcode) {
            this.zipcode = zipcode;
            return this;
        }

        public CandidateBuilder setSocialSecurity(String socialSecurity) {
            this.socialSecurity = socialSecurity;
            return this;
        }

        public CandidateBuilder setCreationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public CandidateBuilder setDriversLicense(String driversLicense) {
            this.driversLicense = driversLicense;
            return this;
        }

        public CandidateBuilder setReport(Report report) {
            this.report = report;
            return this;
        }

        public Candidate build() {
            return new Candidate(this);
        }
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", socialSecurity='" + socialSecurity + '\'' +
                ", creationDate=" + creationDate +
                ", driversLicense='" + driversLicense + '\'' +
                ", report=" + report +
                '}';
    }
}
