package com.pruu.pombo.service;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.entity.Complaint;
import com.pruu.pombo.model.enums.Role;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.repository.ComplaintRepository;
import com.pruu.pombo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    public Complaint create(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    public List<Complaint> findAll(String userId) throws PomboException {
        verifyAdmin(userId);
        return complaintRepository.findAll();
    }

    public Complaint findById(String complaintId, String userId) throws PomboException {
        verifyAdmin(userId);
        return complaintRepository.findById(complaintId).orElseThrow(() -> new PomboException("Denúncia não encontrada."));
    }

    public void verifyAdmin(String userId) throws PomboException{
        User user = userRepository.findById(userId).orElseThrow(() -> new PomboException("Usuário não encontrado."));

        if(user.getRole() == Role.USER) {
            throw new PomboException("Usuário não autorizado.");
        }
    }
}
