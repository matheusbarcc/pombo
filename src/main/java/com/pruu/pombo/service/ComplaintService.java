package com.pruu.pombo.service;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.dto.ReportedPublicationDTO;
import com.pruu.pombo.model.entity.Complaint;
import com.pruu.pombo.model.entity.Publication;
import com.pruu.pombo.model.enums.ComplaintStatus;
import com.pruu.pombo.model.enums.Reason;
import com.pruu.pombo.model.repository.ComplaintRepository;
import com.pruu.pombo.model.repository.PublicationRepository;
import com.pruu.pombo.model.selector.ComplaintSelector;
import com.pruu.pombo.utils.RSAEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private RSAEncoder rsaEncoder;

    public Complaint create(Complaint complaint) throws PomboException {
        publicationRepository.findById(complaint.getPublication().getId()).orElseThrow(() -> new PomboException(
                "Publicação não encontrada.", HttpStatus.BAD_REQUEST));

        if(complaint.getReason() == null || !EnumSet.allOf(Reason.class).contains(complaint.getReason())) {
            throw new PomboException("Motivo inválido", HttpStatus.BAD_REQUEST);
        }

        return complaintRepository.save(complaint);
    }

    public Complaint findById(String complaintId) throws PomboException {
        return complaintRepository.findById(complaintId).orElseThrow(() -> new PomboException("Denúncia não encontrada.", HttpStatus.BAD_REQUEST));
    }

    public List<Complaint> fetchWithFilter(ComplaintSelector selector) {
        List<Complaint> complaints = new ArrayList<>();

        if(selector.hasPagination()) {
            int pageNumber = selector.getPage();
            int pageSize = selector.getLimit();

            PageRequest page = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.ASC, "createdAt"));
            complaints = complaintRepository.findAll(selector, page).toList();
        }

        complaints = removeBlockedAndDeletedPublicationsComplaints(complaints);

        return complaints;
    }

    public List<ReportedPublicationDTO> fetchReportedPublications(ComplaintSelector selector) throws PomboException {
        List<Publication> publications = new ArrayList<>();

        if(selector.hasPagination()) {
            int pageNumber = selector.getPage();
            int pageSize = selector.getLimit();

            PageRequest page = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.ASC, "createdAt"));
            publications = publicationRepository.findAll(page).toList();
        }

        return convertToReportedPublicationDTO(publications);
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

    public List<ReportedPublicationDTO> convertToReportedPublicationDTO(List<Publication> publications) throws PomboException {
        List<ReportedPublicationDTO> dtos = new ArrayList<>();

        for(Publication p : publications) {
            p.setContent(rsaEncoder.decode(p.getContent()));
            String profilePictureUrl = null;
            List<Complaint> complaints = p.getComplaints();

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

            if (p.getUser().getProfilePicture() != null) {
                profilePictureUrl = attachmentService.getAttachmentUrl(p.getUser().getProfilePicture().getId());
            }

            ReportedPublicationDTO dto = Complaint.toReportedPublicationDTO(p, profilePictureUrl, complaints.size(),
                                                                           pendingComplaintAmount, acceptedComplaintAmount,
                                                                           rejectedComplaintAmount);
            dtos.add(dto);
        }

        return dtos;
    }

    public List<Complaint> removeBlockedAndDeletedPublicationsComplaints(List<Complaint> complaints) {
        return complaints.stream()
                .filter(comp -> !comp.getPublication().isBlocked() && !comp.getPublication().isDeleted())
                .collect(Collectors.toList());
    }
}
