package com.pruu.pombo.service;

import com.pruu.pombo.model.entity.Attachment;
import com.pruu.pombo.model.repository.AttachmentRepository;
import com.pruu.pombo.utils.RSAEncoder;
import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.dto.PublicationDTO;
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
import java.util.stream.Collectors;

@Service
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private RSAEncoder rsaEncoder;

    public Publication create(Publication publication) throws PomboException {
        if(publication.getContent().length() > 300) {
            throw new PomboException("O conteúdo do Pruu deve conter no máximo 300 caracteres.", HttpStatus.BAD_REQUEST);
        }

        publication.setContent(rsaEncoder.encode(publication.getContent()));

        Publication result = publicationRepository.saveAndFlush(publication);

        if(publication.getAttachment() != null) {
            Attachment attachment = attachmentRepository.findById(publication.getAttachment().getId()).orElseThrow(() -> new PomboException("Anexo não encontrado.", HttpStatus.BAD_REQUEST));

            if(attachment.getUser() != null || attachment.getPublication() != null) {
                throw new PomboException("O anexo já está atribuído à uma publicação ou usuário.", HttpStatus.BAD_REQUEST);
            }

            attachment.setPublication(publication);

            attachmentRepository.save(attachment);
        }

        return result;
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

    public PublicationDTO findById(String id) throws PomboException {
        Publication publication = publicationRepository.findById(id).orElseThrow(() -> new PomboException("Publicação não encontrada.", HttpStatus.BAD_REQUEST));
        publication.setContent(rsaEncoder.decode(publication.getContent()));

        Integer likeAmount = this.fetchPublicationLikes(publication.getId()).size();
        Integer complaintAmount = publication.getComplaints().size();

        String attachmentUrl = null;
        String profilePictureUrl = null;

        if (publication.getAttachment() != null) {
            attachmentUrl = attachmentService.getAttachmentUrl(publication.getAttachment().getId());
        }

        if (publication.getUser().getProfilePicture() != null) {
            profilePictureUrl = attachmentService.getAttachmentUrl(publication.getUser().getProfilePicture().getId());
        }

        if(publication.isBlocked()) {
            throw new PomboException("Essa publicação foi bloqueada por um administrador.", HttpStatus.BAD_REQUEST);
        }

        if(publication.isDeleted()) {
            throw new PomboException("Essa publicação foi deletada.", HttpStatus.BAD_REQUEST);
        }

        return Publication.toDTO(publication, attachmentUrl, profilePictureUrl, likeAmount, complaintAmount);
    }

    public List<PublicationDTO> fetchWithFilter(PublicationSelector selector, String subjectId) throws PomboException {
        List<Publication> publications;

        if(selector.hasPagination()) {
            int pageNumber = selector.getPage();
            int pageSize = selector.getLimit();

            PageRequest page = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
            publications = new ArrayList<>(publicationRepository.findAll(selector, page).toList());

        } else {
            publications = new ArrayList<>(publicationRepository.findAll(selector, Sort.by(Sort.Direction.DESC, "createdAt")));
        }

        publications = removeBlockedAndDeletedPublications(publications);

        if (selector.isLiked()) {
            publications = publications.stream()
                    .filter(pub -> pub.getLikes().stream()
                            .anyMatch(user -> user.getId().equals(subjectId)))
                    .collect(Collectors.toList());
        }

        return this.convertToDTO(publications);
    }

    public List<User> fetchPublicationLikes(String publicationId) throws PomboException {
        Publication publication = publicationRepository.findById(publicationId).orElseThrow(() -> new PomboException("Publicação não encontrada.", HttpStatus.BAD_REQUEST));

        return publication.getLikes();
    }

    public void delete(String publicationId, String userId) throws PomboException {
        Publication publication = publicationRepository.findById(publicationId).orElseThrow(() -> new PomboException("Publicação não encontrada.", HttpStatus.BAD_REQUEST));

        if(!publication.getUser().getId().equals(userId)) {
            throw new PomboException("Você não pode excluir publicações de outros usuários.", HttpStatus.BAD_REQUEST);
        }

        publication.setDeleted(true);
        publicationRepository.save(publication);
    }

    public List<PublicationDTO> convertToDTO(List<Publication> publications) throws PomboException {
        List<PublicationDTO> dtos = new ArrayList<>();

        for(Publication p : publications) {
            String attachmentUrl = null;
            String profilePictureUrl = null;

            p.setContent(rsaEncoder.decode(p.getContent()));

            Integer likeAmount = this.fetchPublicationLikes(p.getId()).size();
            Integer complaintAmount = p.getComplaints().size();

            if (p.getAttachment() != null) {
                attachmentUrl = attachmentService.getAttachmentUrl(p.getAttachment().getId());
            }

            if (p.getUser().getProfilePicture() != null) {
                profilePictureUrl = attachmentService.getAttachmentUrl(p.getUser().getProfilePicture().getId());
            }

            PublicationDTO dto = Publication.toDTO(p, attachmentUrl, profilePictureUrl, likeAmount, complaintAmount);
            dtos.add(dto);
        }

        return dtos;
    }

    public List<Publication> removeBlockedAndDeletedPublications(List<Publication> publications) {
        return publications.stream()
                .filter(pub -> !pub.isBlocked() && !pub.isDeleted())
                .collect(Collectors.toList());
    }
}
