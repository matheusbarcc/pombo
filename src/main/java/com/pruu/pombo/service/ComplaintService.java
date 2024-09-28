package com.pruu.pombo.service;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.dto.ComplaintDTO;
import com.pruu.pombo.model.entity.Complaint;
import com.pruu.pombo.model.enums.ComplaintStatus;
import com.pruu.pombo.model.enums.Reason;
import com.pruu.pombo.model.enums.Role;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.repository.ComplaintRepository;
import com.pruu.pombo.model.repository.UserRepository;
import com.pruu.pombo.model.selector.ComplaintSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    public Complaint create(Complaint complaint) throws PomboException {
        if(complaint.getReason() == null || !EnumSet.allOf(Reason.class).contains(complaint.getReason())) {
            throw new PomboException("Motivo inválido", HttpStatus.BAD_REQUEST);
        }

        return complaintRepository.save(complaint);
    }

    public List<Complaint> findAll(String userId) throws PomboException {
        verifyAdmin(userId);
        return complaintRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Complaint findById(String complaintId, String userId) throws PomboException {
        verifyAdmin(userId);
        return complaintRepository.findById(complaintId).orElseThrow(() -> new PomboException("Denúncia não encontrada.", HttpStatus.BAD_REQUEST));
    }

    public List<Complaint> fetchWithFilter(ComplaintSelector selector, String userId) throws PomboException {
        verifyAdmin(userId);
        if(selector.hasPagination()) {
            int pageNumber = selector.getPage();
            int pageSize = selector.getLimit();

            PageRequest page = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
            return complaintRepository.findAll(selector, page).toList();
        }

        return complaintRepository.findAll(selector, Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public List<Complaint> fetchByPublicationId(String userId, String publicationId) throws PomboException {
        verifyAdmin(userId);
        List<Complaint> complaints = this.complaintRepository.findByPublicationId(publicationId);
        return complaints;
    }

    public void updateStatus(String complaintId) throws PomboException {
        verifyAdmin(complaintId);
        Complaint complaint = this.complaintRepository.findById(complaintId).orElseThrow(() -> new PomboException("Denúncia não encontrada.", HttpStatus.BAD_REQUEST));

        if(complaint.getStatus() == ComplaintStatus.PENDING) {
            complaint.setStatus(ComplaintStatus.ANALYSED);
        } else {
            complaint.setStatus(ComplaintStatus.PENDING);
        }

        this.complaintRepository.save(complaint);
    }

    public ComplaintDTO findDTOByPublicationId(String userId, String publicationId) throws PomboException {
        verifyAdmin(userId);
        List<Complaint> complaints = this.complaintRepository.findByPublicationId(publicationId);
        List<Complaint> pendingComplaintAmount = new ArrayList<>();
        List<Complaint> analysedComplaintAmount = new ArrayList<>();


        for(Complaint c : complaints) {
            if(c.getStatus() == ComplaintStatus.PENDING) {
                pendingComplaintAmount.add(c);
            }
            if(c.getStatus() == ComplaintStatus.ANALYSED) {
                analysedComplaintAmount.add(c);
            }
        }

        ComplaintDTO dto = Complaint.toDTO(publicationId, complaints.size(), pendingComplaintAmount.size(), analysedComplaintAmount.size());
        return dto;
    }

    public boolean delete(String id) {
        complaintRepository.deleteById(id);
        return true;
    }

    public void verifyAdmin(String userId) throws PomboException{
        User user = userRepository.findById(userId).orElseThrow(() -> new PomboException("Usuário não encontrado.", HttpStatus.BAD_REQUEST));

        if(user.getRole() == Role.USER) {
            throw new PomboException("Usuário não autorizado.", HttpStatus.UNAUTHORIZED);
        }
    }
}
