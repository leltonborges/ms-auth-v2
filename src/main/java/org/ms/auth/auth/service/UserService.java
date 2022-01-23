package org.ms.auth.auth.service;

import org.modelmapper.ModelMapper;
import org.ms.auth.auth.dtos.UserDTO;
import org.ms.auth.auth.entities.User;
import org.ms.auth.auth.repositories.UserRepository;
import org.ms.auth.auth.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    public User findByUserName(String userName) {
        return userRepository
                .findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("User not found: "+ userName));
    }

    public <S extends User> S save(S entity) {
        return userRepository.save(entity);
    }

    public UserDTO fromDTO(User user){
        return mapper.map(user, UserDTO.class);
    }
    public User fromUser(UserDTO userDTO){
        return mapper.map(userDTO, User.class);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = findByUserName(userName);
        return user;
    }
}
