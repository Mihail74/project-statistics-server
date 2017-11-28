package ru.mdkardaev.user.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mdkardaev.security.requests.RegisterUserRequest;
import ru.mdkardaev.user.dtos.UserDTO;
import ru.mdkardaev.user.entity.User;
import ru.mdkardaev.user.repository.UserRepository;
import ru.mdkardaev.user.roles.Role;

import java.util.Collections;
import java.util.HashSet;

@Service
@Slf4j
public class UserRegistrationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConversionService conversionService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register user and return it's
     */
    public UserDTO register(RegisterUserRequest request) {
        User user = User.builder()
                        .login(request.getLogin())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .name(request.getName())
                        .roles(new HashSet<>(Collections.singletonList(Role.USER)))
                        .build();

        user = userRepository.save(user);
        return conversionService.convert(user, UserDTO.class);
    }
}
