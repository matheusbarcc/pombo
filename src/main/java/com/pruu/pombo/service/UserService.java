package com.pruu.pombo.service;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.repository.UserRepository;
import com.pruu.pombo.model.selector.UserSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User create(User user) throws PomboException {
        this.standardizeCpf(user);
//        this.verifyIfUserExists(user);

        return userRepository.save(user);
    }

    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User update(User user) throws PomboException {
        this.standardizeCpf(user);
        this.verifyIfUserExists(user);

        return userRepository.save(user);
    }

    public List<User> fetchWithFilter(UserSelector selector) {
        if(selector.hasPagination()) {
            int pageNumber = selector.getPage();
            int pageSize = selector.getLimit();

            PageRequest page = PageRequest.of(pageNumber - 1, pageSize);
            return userRepository.findAll(selector, page).toList();
        }

        return userRepository.findAll(selector);
    }

    public boolean delete(String id) {
        userRepository.deleteById(id);
        return true;
    }

    public void standardizeCpf(User user) {
        user.setCpf(user.getCpf().replaceAll("[.]", ""));
        user.setCpf(user.getCpf().replaceAll("-", ""));
    }

    public void verifyIfUserExists(User user) throws PomboException {
        User userWithSameEmail = userRepository.findByEmail(user.getEmail());
        User userWithSameCpf = userRepository.findByCpf(user.getCpf());

        if(user.getId() == null) {
            user.setId("");
        }

        if(userWithSameEmail != null && !user.getId().equals(userWithSameEmail.getId())) {
            throw new PomboException("Email já cadastrado.", HttpStatus.CONFLICT);
        }

        if(userWithSameCpf != null && !user.getId().equals(userWithSameCpf.getId())) {
            throw new PomboException("CPF já cadastrado.", HttpStatus.CONFLICT);
        }
    }
}
