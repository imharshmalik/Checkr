package com.zemoso.checkr.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime creationDate;
    private LocalDateTime completionDate;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "candidatePackage_id", referencedColumnName = "id")
    private CandidatePackage candidatePackage;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adjudication_id", referencedColumnName = "id")
    private Adjudication adjudication;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public CandidatePackage getCandidatePackage() {
        return candidatePackage;
    }

    public void setCandidatePackage(CandidatePackage candidatePackage) {
        this.candidatePackage = candidatePackage;
    }

    public Adjudication getAdjudication() {
        return adjudication;
    }

    public void setAdjudication(Adjudication adjudication) {
        this.adjudication = adjudication;
    }

    public Report() {

    }

    public Report(long id, LocalDateTime creationDate, LocalDateTime completionDate, Status status, CandidatePackage candidatePackage, Adjudication adjudication) {
        this.id = id;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.status = status;
        this.candidatePackage = candidatePackage;
        this.adjudication = adjudication;
    }

    public Report(LocalDateTime creationDate, LocalDateTime completionDate, Status status, CandidatePackage candidatePackage, Adjudication adjudication) {
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.status = status;
        this.candidatePackage = candidatePackage;
        this.adjudication = adjudication;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", completionDate=" + completionDate +
                ", status=" + status +
                ", candidatePackage=" + candidatePackage +
                ", adjudication=" + adjudication +
                '}';
    }
}
