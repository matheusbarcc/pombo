package com.pruu.pombo.service;

import com.pruu.pombo.model.entity.Attachment;
import com.pruu.pombo.model.repository.AttachmentRepository;
import com.pruu.pombo.utils.RSAEncoder;
import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.dto.PublicationDTO;
import com.pruu.pombo.model.entity.Complaint;
import com.pruu.pombo.model.entity.Publication;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.repository.PublicationRepository;
import com.pruu.pombo.model.repository.UserRepository;
import com.pruu.pombo.model.selector.PublicationSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
    private AttachmentRepository attachmentRepositoy;

    @Autowired
    private RSAEncoder rsaEncoder;

    public Publication create(Publication publication) throws PomboException {
        if(publication.getContent().length() > 300) {
            throw new PomboException("O conteúdo do Pruu deve conter no máximo 300 caracteres.", HttpStatus.BAD_REQUEST);
        }

        publication.setContent(rsaEncoder.encode(publication.getContent()));

        Publication result = publicationRepository.saveAndFlush(publication);

        if(publication.getAttachment() != null) {
            Attachment attachment = attachmentRepositoy.findById(publication.getAttachment().getId()).orElseThrow(() -> new PomboException("Anexo não encontrado.", HttpStatus.BAD_REQUEST));
            attachment.setPublication(publication);

            attachmentRepositoy.save(attachment);
        }

        return result;
    }

    public List<Publication> findAll() {
        return publicationRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Publication findById(String id) throws PomboException {
        Publication publication = publicationRepository.findById(id).orElseThrow(() -> new PomboException("Publicação não encontrada.", HttpStatus.BAD_REQUEST));
        publication.setContent(rsaEncoder.decode(publication.getContent()));

        return publication;
    }

    // if the publication is already liked, the method will unlike it
    public void like(String userId, String publicationId) throws PomboException {
        Publication publication = publicationRepository.findById(publicationId).orElseThrow(() -> new PomboException("Publicação não encontrada.", HttpStatus.BAD_REQUEST));
        User user = userRepository.findById(userId).orElseThrow(() -> new PomboException("Usuário não encontrado.", HttpStatus.BAD_REQUEST));

        List<User> likes = publication.getLikes();

        if(likes.contains(user)) {
            likes.remove(user);
        } else {
            likes.add(user);
        }

        publication.setLikes(likes);
        publicationRepository.save(publication);
    }

    public List<Publication> fetchWithFilter(PublicationSelector selector) {
        List<Publication> publications;

        if(selector.hasPagination()) {
            int pageNumber = selector.getPage();
            int pageSize = selector.getLimit();

            PageRequest page = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
            publications = new ArrayList<>(publicationRepository.findAll(selector, page).toList());

        } else {
            publications = new ArrayList<>(publicationRepository.findAll(selector, Sort.by(Sort.Direction.DESC, "createdAt")));
        }

        for(Publication p : publications) {
            p.setContent(rsaEncoder.decode(p.getContent()));
        }

        return publications;
    }

    public List<User> fetchPublicationLikes(String publicationId) throws PomboException {
        Publication publication = publicationRepository.findById(publicationId).orElseThrow(() -> new PomboException("Publicação não encontrada.", HttpStatus.BAD_REQUEST));

        return publication.getLikes();
    }

    public List<Complaint> fetchPublicationComplaints(String publicationId) throws PomboException {
        Publication publication = publicationRepository.findById(publicationId).orElseThrow(() -> new PomboException("Publicação não encontrada.", HttpStatus.BAD_REQUEST));

        return publication.getComplaints();
    }

    public List<PublicationDTO> fetchDTOs() throws PomboException {
        List<Publication> publications = this.findAll();
        List<PublicationDTO> dtos = new ArrayList<>();

        for(Publication p : publications) {
            p.setContent(rsaEncoder.decode(p.getContent()));
            Integer likeAmount = this.fetchPublicationLikes(p.getId()).size();
            Integer complaintAmount = this.fetchPublicationComplaints(p.getId()).size();
            PublicationDTO dto = Publication.toDTO(p, likeAmount, complaintAmount);
            dtos.add(dto);
        }

        return dtos;
    }
}
