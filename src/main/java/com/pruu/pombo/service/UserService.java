package com.pruu.pombo.service;

import com.pruu.pombo.exception.PomboException;
import com.pruu.pombo.model.dto.UserDTO;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.repository.UserRepository;
import com.pruu.pombo.model.selector.UserSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttachmentService attachmentService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado " + username));
    }

    public User create(User user) throws PomboException {
        this.standardizeCpf(user);
        this.verifyIfUserExists(user);

        return userRepository.save(user);
    }

    public User update(User user) throws PomboException {
        this.standardizeCpf(user);
        this.verifyIfUserExists(user);
        User userOnDatabase = this.userRepository.findById(user.getId()).orElseThrow(() -> new PomboException("Usuário não encontrado.", HttpStatus.BAD_REQUEST));

        user.setEmail(userOnDatabase.getEmail());
        user.setCpf(userOnDatabase.getCpf());
        user.setPassword(userOnDatabase.getPassword());
        user.setCreatedAt(userOnDatabase.getCreatedAt());
        user.setRole(userOnDatabase.getRole());

        return userRepository.save(user);
    }

    public UserDTO findById(String id) throws PomboException {
        User user = userRepository.findById(id).orElse(null);

        String profilePictureUrl = null;

        if(user.getProfilePicture() != null) {
            profilePictureUrl = attachmentService.getAttachmentUrl(user.getProfilePicture().getId());
        }

        return User.toDTO(user, profilePictureUrl);
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

//    public void delete(String id) throws PomboException {
//        User userOnDatabase = this.userRepository.findById(id).orElseThrow(() -> new PomboException("Usuário não encontrado.", HttpStatus.BAD_REQUEST));
//
//        if(userOnDatabase.getPublications().isEmpty() || userOnDatabase.getComplaints().isEmpty()){
//            userRepository.deleteById(id);
//        } else {
//            throw new PomboException("Usuário não pode ser excluído pois possui publicações ou denuncias criadas.", HttpStatus.BAD_REQUEST);
//        }
//    }

    public void standardizeCpf(User user) {
        user.setCpf(user.getCpf().replaceAll("[.]", ""));
        user.setCpf(user.getCpf().replaceAll("-", ""));
    }

    public void verifyIfUserExists(User user) throws PomboException {
        Optional<User> userWithSameEmail = userRepository.findByEmail(user.getEmail());
        User userWithSameCpf = userRepository.findByCpf(user.getCpf());

        if(user.getId() == null) {
            user.setId("");
        }

        if(userWithSameEmail.isPresent()) {
            if (!user.getId().equals(userWithSameEmail.get().getId())) {
                throw new PomboException("Email já cadastrado.", HttpStatus.CONFLICT);
            }
        }

        if(userWithSameCpf != null && !user.getId().equals(userWithSameCpf.getId())) {
            throw new PomboException("CPF já cadastrado.", HttpStatus.CONFLICT);
        }
    }
}
