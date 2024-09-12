package com.pruu.pombo.service;

import com.pruu.pombo.exception.PomboException;
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
        return publicationRepository.findById(id).orElseThrow(() -> new PomboException("Publication nao encontrado."));
    }

    public Publication create(Publication publication) throws PomboException {
        this.userRepository.findById(publication.getUser().getId()).orElseThrow(() -> new PomboException("Usuario invalido."));

        return publicationRepository.save(publication);
    }

    // If the publication is already liked, the method will unlike it
    public void like(String userId, String publicationId) throws PomboException {
        Publication publication = publicationRepository.findById(publicationId).orElseThrow(() -> new PomboException("Publication nao encontrado."));
        User user = userRepository.findById(userId).orElseThrow(() -> new PomboException("Usuario nao encontrado."));

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

        this.complaintRepository.findByPublicationId(publicationId).orElseThrow(() -> new PomboException("O publication não foi denunciado."));
        Publication publication = publicationRepository.findById(publicationId).orElseThrow(() -> new PomboException("Publication nao encontrado."));

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
        Publication publication = publicationRepository.findById(publicationId).orElseThrow(() -> new PomboException("Publication nao encontrado."));

        return publication.getLikes();
    }

    public void verifyAdmin(String userId) throws PomboException{
        User user = userRepository.findById(userId).orElseThrow(() -> new PomboException("Usuario nao encontrado."));

        if(user.getRole() == Role.USER) {
            throw new PomboException("Usuário não autorizado.");
        }
    }
}
