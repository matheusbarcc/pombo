package com.pruu.pombo.service;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.dto.ComplaintDTO;
import com.pruu.pombo.model.entity.Complaint;
import com.pruu.pombo.model.entity.Publication;
import com.pruu.pombo.model.enums.ComplaintStatus;
import com.pruu.pombo.model.enums.Reason;
import com.pruu.pombo.model.repository.ComplaintRepository;
import com.pruu.pombo.model.repository.PublicationRepository;
import com.pruu.pombo.model.selector.ComplaintSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    public Complaint create(Complaint complaint) throws PomboException {
        publicationRepository.findById(complaint.getPublication().getId()).orElseThrow(() -> new PomboException(
                "Publicação não encontrada.", HttpStatus.BAD_REQUEST));

        if(complaint.getReason() == null || !EnumSet.allOf(Reason.class).contains(complaint.getReason())) {
            throw new PomboException("Motivo inválido", HttpStatus.BAD_REQUEST);
        }

        return complaintRepository.save(complaint);
    }

    public List<Complaint> fetchAll(){
        return complaintRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Complaint findById(String complaintId) throws PomboException {
        return complaintRepository.findById(complaintId).orElseThrow(() -> new PomboException("Denúncia não encontrada.", HttpStatus.BAD_REQUEST));
    }

    public List<Complaint> fetchWithFilter(ComplaintSelector selector) {
        if(selector.hasPagination()) {
            int pageNumber = selector.getPage();
            int pageSize = selector.getLimit();

            PageRequest page = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
            return complaintRepository.findAll(selector, page).toList();
        }

        return complaintRepository.findAll(selector, Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public void updateStatus(String complaintId, ComplaintStatus newStatus) throws PomboException {
        Complaint complaint = this.complaintRepository.findById(complaintId).orElseThrow(() -> new PomboException("Denúncia não encontrada.", HttpStatus.BAD_REQUEST));
        Publication publication = complaint.getPublication();

        if(!EnumSet.allOf(ComplaintStatus.class).contains(newStatus)) {
            throw new PomboException("Status inválido", HttpStatus.BAD_REQUEST);
        }

        if(newStatus == ComplaintStatus.ACCEPTED) {
            publication.setBlocked(true);

            publicationRepository.save(publication);
        }
        if(complaint.getStatus() == ComplaintStatus.ACCEPTED && (newStatus == ComplaintStatus.REJECTED || newStatus == ComplaintStatus.PENDING)) {
            publication.setBlocked(false);

            publicationRepository.save(publication);
        }

        complaint.setStatus(newStatus);
        complaintRepository.save(complaint);
    }

    public ComplaintDTO findDTOByPublicationId(String publicationId) {
        List<Complaint> complaints = this.complaintRepository.findByPublicationId(publicationId);
        int pendingComplaintAmount = 0;
        int acceptedComplaintAmount = 0;
        int rejectedComplaintAmount = 0;


        for(Complaint c : complaints) {
            if(c.getStatus() == ComplaintStatus.PENDING) {
                pendingComplaintAmount++;
            }
            if(c.getStatus() == ComplaintStatus.ACCEPTED) {
                acceptedComplaintAmount++;
            }
            if(c.getStatus() == ComplaintStatus.REJECTED) {
                rejectedComplaintAmount++;
            }
        }

        ComplaintDTO dto = Complaint.toDTO(publicationId, complaints.size(), pendingComplaintAmount, acceptedComplaintAmount, rejectedComplaintAmount);
        return dto;
    }
}
