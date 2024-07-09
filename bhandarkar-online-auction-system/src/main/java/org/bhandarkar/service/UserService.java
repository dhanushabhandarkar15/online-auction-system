package org.bhandarkar.service;

import org.bhandarkar.entity.UserEntity;
import org.bhandarkar.model.User;
import org.bhandarkar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserEntity registerUser(User signUpRequest) throws Exception {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new Exception("User already exists");
        }
        UserEntity userEntity = UserEntity.builder()
                .username(signUpRequest.getUsername())
                .role(signUpRequest.getRole()).password(signUpRequest.getPassword()).build();

        userEntity = userRepository.save(userEntity);
        return userEntity;
    }
}
