package com.pruu.pombo.service;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.dto.PublicationDTO;
import com.pruu.pombo.model.entity.Complaint;
import com.pruu.pombo.model.entity.Publication;
import com.pruu.pombo.model.enums.Role;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.repository.ComplaintRepository;
import com.pruu.pombo.model.repository.PublicationRepository;
import com.pruu.pombo.model.repository.UserRepository;
import com.pruu.pombo.model.selector.PublicationSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    public List<Publication> findAll() {
        return publicationRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Publication findById(String id) throws PomboException {
        return publicationRepository.findById(id).orElseThrow(() -> new PomboException("Publicação não encontrada."));
    }

    public Publication create(Publication publication) throws PomboException {
        this.userRepository.findById(publication.getUser().getId()).orElseThrow(() -> new PomboException("Usuário inválido."));

        return publicationRepository.save(publication);
    }

    // If the publication is already liked, the method will unlike it
    public void like(String userId, String publicationId) throws PomboException {
        Publication publication = publicationRepository.findById(publicationId).orElseThrow(() -> new PomboException("Publicação não encontrada."));
        User user = userRepository.findById(userId).orElseThrow(() -> new PomboException("Usuário não encontrado."));

        List<User> likes = publication.getLikes();

        if(likes.contains(user)) {
            likes.remove(user);
        } else {
            likes.add(user);
        }

        publication.setLikes(likes);
        publicationRepository.save(publication);
    }

    // If the publication is already blocked, the method will unblock it
    public void block(String userId, String publicationId) throws PomboException{
        verifyAdmin(userId);

        List<Complaint> complaints = this.complaintRepository.findByPublicationId(publicationId);

        Publication publication = publicationRepository.findById(publicationId).orElseThrow(() -> new PomboException("Publicação não encontrada."));

        if(complaints.isEmpty()) {
            throw new PomboException("A publicação não foi denunciada");
        }

        publication.setBlocked(!publication.isBlocked());

        publicationRepository.save(publication);
    }

    public List<Publication> fetchWithFilter(PublicationSelector selector) {
        if(selector.hasPagination()) {
            int pageNumber = selector.getPage();
            int pageSize = selector.getLimit();

            PageRequest page = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
            return publicationRepository.findAll(selector, page).toList();
        }

        return publicationRepository.findAll(selector, Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public List<User> fetchPublicationLikes(String publicationId) throws PomboException {
        Publication publication = publicationRepository.findById(publicationId).orElseThrow(() -> new PomboException("Publicação não encontrada."));

        return publication.getLikes();
    }

    public List<Complaint> fetchPublicationComplaints(String publicationId) throws PomboException {
        Publication publication = publicationRepository.findById(publicationId).orElseThrow(() -> new PomboException("Publicação não encontrada."));

        return publication.getComplaints();
    }

    public List<PublicationDTO> fetchDTO() throws PomboException {
        List<Publication> publications = this.findAll();
        List<PublicationDTO> dtos = new ArrayList<>();

        for(Publication p : publications) {
            Integer likeAmount = this.fetchPublicationLikes(p.getId()).size();
            Integer complaintAmount = this.fetchPublicationComplaints(p.getId()).size();
            PublicationDTO dto = Publication.toDTO(p, likeAmount, complaintAmount);
            dtos.add(dto);
        }

        return dtos;

//        return publications.stream().map(publication -> {
//            Integer likeAmount = this.fetchPublicationLikes(publication.getId()).size();
//            Integer complaintAmount = this.fetchPublicationComplaints(publication.getId()).size();
//            return Publication.toDTO(publication, likeAmount, complaintAmount);
//        });
    }

    public boolean delete(String id){
        publicationRepository.deleteById(id);
        return true;
    }

    public void verifyAdmin(String userId) throws PomboException{
        User user = userRepository.findById(userId).orElseThrow(() -> new PomboException("Usuário não encontrado."));

        if(user.getRole() == Role.USER) {
            throw new PomboException("Usuário não autorizado.");
        }
    }
}
