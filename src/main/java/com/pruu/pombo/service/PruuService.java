package com.pruu.pombo.service;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.entity.Complaint;
import com.pruu.pombo.model.entity.Pruu;
import com.pruu.pombo.model.entity.Role;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.repository.ComplaintRepository;
import com.pruu.pombo.model.repository.PruuRepository;
import com.pruu.pombo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class PruuService {

    @Autowired
    private PruuRepository pruuRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    public List<Pruu> findAll() {
        return pruuRepository.findAll();
    }

    public Pruu findById(String id) throws PomboException {
        return pruuRepository.findById(id).orElseThrow(() -> new PomboException("Pruu nao encontrado."));
    }

    public List<Pruu> fetchByUserId(String userId) {
        return pruuRepository.findByUserId(userId);
    }

    public Pruu create(Pruu pruu) throws PomboException {
        verifyIfUserExists(pruu);

        return pruuRepository.save(pruu);
    }

    public void like(String userId, String pruuId) {
        Pruu pruu = pruuRepository.findById(pruuId).orElse(null);
        List<User> likes = pruu.getLikes();

        User user = userRepository.findById(userId).orElse(null);

        if(likes.contains(user)) {
            likes.remove(user);
        } else {
            likes.add(user);
        }

        pruu.setLikes(likes);
        pruuRepository.save(pruu);
    }

    public void block(String userId, String pruuId) throws PomboException{
        verifyAdmin(userId);

        Complaint complaint = complaintRepository.findByPruuId(pruuId);
        Pruu pruu = pruuRepository.findById(pruuId).orElse(null);

        if(pruu == null) {
            throw new PomboException("O Pruu não existe.");
        }

        if(complaint == null) {
            throw new PomboException("O Pruu não foi denunciado.");
        }

        if (pruu.isBlocked() == true) {
            throw new PomboException("O Pruu já esta bloqueado.");
        }

        pruu.setBlocked(true);
        pruuRepository.save(pruu);
    }

    public void verifyIfUserExists(Pruu pruu) throws PomboException {
        User user = userRepository.findById(pruu.getUser().getId()).orElse(null);

        if(user == null) {
            throw new PomboException("Usuário não existente.");
        }
    }

    public void verifyAdmin(String userId) throws PomboException{
        User user = userRepository.findById(userId).orElse(null);

        if(user == null) {
            throw new PomboException("Usuário não existe.");
        }

        if(user.getRole() == Role.USER) {
            throw new PomboException("Usuário sem acesso.");
        }
    }
}
