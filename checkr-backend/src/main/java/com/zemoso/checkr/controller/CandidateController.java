package com.zemoso.checkr.controller;

import com.zemoso.checkr.dto.CandidateDTO;
import com.zemoso.checkr.dto.ExportRequestDTO;
import com.zemoso.checkr.dto.PreAdverseNoticeDTO;
import com.zemoso.checkr.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
    private final CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService){
        this.candidateService = candidateService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CandidateDTO> getCandidateById(@PathVariable Long id) {
        CandidateDTO candidateDTO = candidateService.getCandidateById(id);
        return ResponseEntity.ok(candidateDTO);
    }

    @GetMapping
    public ResponseEntity<List<CandidateDTO>> getAllCandidates() {
        List<CandidateDTO> candidates = candidateService.getAllCandidates();
        return ResponseEntity.ok(candidates);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<CandidateDTO>> getCandidatesByName(@PathVariable("name") String name) {
        List<CandidateDTO> candidates = candidateService.getCandidatesByName(name);
        if (!candidates.isEmpty()) {
            return ResponseEntity.ok(candidates);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CandidateDTO>> filterCandidatesByStatusAndAdjudication(@RequestParam(required = false) String status, @RequestParam(required = false) String adjudication) {
        List<CandidateDTO> candidates;
        if (status != null && adjudication != null) {
            candidates = candidateService.getCandidatesByStatusAndAdjudication(status, adjudication);
        } else if (status != null) {
            candidates = candidateService.getCandidatesByStatus(status);
        } else if (adjudication != null) {
            candidates = candidateService.getCandidatesByAdjudication(adjudication);
        } else {
            candidates = candidateService.getAllCandidates();
        }
        return ResponseEntity.ok(candidates);
    }

    @PostMapping("/export")
    public ResponseEntity<String> exportCandidates(@RequestBody ExportRequestDTO requestDTO) {
        List<CandidateDTO> candidates = candidateService.getCandidatesBetweenDates(requestDTO.getStartDate(), requestDTO.getEndDate());

        String csvContent = generateCsvContent(candidates);

        String downloadLink = sendEmailWithAttachment(csvContent);

        return ResponseEntity.ok("Download link was sent to your email: " + requestDTO.getEmail() + "\n\n CSV Content: \n" + downloadLink);
    }

    public String generateCsvContent(List<CandidateDTO> candidates) {
        return candidates.stream()
                .map(candidate -> candidate.getName() + ", " + candidate.getLocation() + ", " + candidate.getReport().getAdjudication().getName() + ", " + candidate.getReport().getStatus().getName() + ", " + candidate.getCreationDate())
                .collect(Collectors.joining("\n"));
    }

    public String sendEmailWithAttachment(String attachmentContent) {
        return attachmentContent;
    }

    @PostMapping("/{candidateId}/send-pre-adverse-action")
    public ResponseEntity<String> sendPreAdverseActionNotification(@PathVariable Long candidateId, @RequestBody PreAdverseNoticeDTO preAdverseNoticeDTO) {
        CandidateDTO candidateDTO = candidateService.getCandidateById(candidateId);
        if(candidateDTO != null) {
            return ResponseEntity.ok("Pre-Adverse Action notice successfully sent.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidate not found with id: " + candidateId);
        }
    }

    @PostMapping
    public ResponseEntity<CandidateDTO> createCandidate(@RequestBody CandidateDTO candidateDTO) {
        CandidateDTO createdCandidateDTO = candidateService.createCandidate(candidateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCandidateDTO);
    }
}
