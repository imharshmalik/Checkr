package com.zemoso.checkr.dto;

import java.time.LocalDateTime;

public class ReportDTO {
    private int id;
    private LocalDateTime creationDate;
    private LocalDateTime completionDate;
    private StatusDTO status;
    private CandidatePackageDTO candidatePackage;
    private AdjudicationDTO adjudication;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    public CandidatePackageDTO getCandidatePackage() {
        return candidatePackage;
    }

    public void setCandidatePackage(CandidatePackageDTO candidatePackage) {
        this.candidatePackage = candidatePackage;
    }

    public AdjudicationDTO getAdjudication() {
        return adjudication;
    }

    public void setAdjudication(AdjudicationDTO adjudication) {
        this.adjudication = adjudication;
    }

    public ReportDTO() {

    }

    public ReportDTO(int id, LocalDateTime creationDate, LocalDateTime completionDate, StatusDTO status, CandidatePackageDTO candidatePackage, AdjudicationDTO adjudication) {
        this.id = id;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.status = status;
        this.candidatePackage = candidatePackage;
        this.adjudication = adjudication;
    }

    public ReportDTO(LocalDateTime creationDate, LocalDateTime completionDate, StatusDTO status, CandidatePackageDTO candidatePackage, AdjudicationDTO adjudication) {
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.status = status;
        this.candidatePackage = candidatePackage;
        this.adjudication = adjudication;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", completionDate=" + completionDate +
                ", status=" + status +
                ", candidatePackage=" + candidatePackage +
                ", adjudication=" + adjudication +
                '}';
    }
}
