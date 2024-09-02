package com.pruu.pombo.service;

import com.pruu.pombo.model.entity.Pruu;
import com.pruu.pombo.model.entity.User;
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

    public List<Pruu> findAll() {
        return pruuRepository.findAll();
    }

    public Pruu findById(UUID id) {
        return pruuRepository.findById(id).orElse(null);
    }

    //public Set<Pruu> fetchByUserId(UUID userId) {
      //  return pruuRepository.findByUserId(userId);
    //}

    public Pruu create(Pruu pruu) {
        return pruuRepository.save(pruu);
    }

    // throw exception caso o usuario ja tenha dado like? ou como é um set nao precisa ja que o usuario nao vai se repetir?
    public void like(UUID userId, UUID pruuId) {
        Pruu pruu = pruuRepository.findById(pruuId).orElse(null);
        Set<User> likes = pruu.getLikes();
        User user = userRepository.findById(userId).orElse(null);
        likes.add(user);
        pruu.setLikes(likes);
        pruuRepository.save(pruu);
    }
}
