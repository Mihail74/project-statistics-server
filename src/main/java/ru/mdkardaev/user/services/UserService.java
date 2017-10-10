package ru.mdkardaev.user.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mdkardaev.common.exceptions.sql.SQLStates;
import ru.mdkardaev.common.exceptions.utils.DBExceptionUtils;
import ru.mdkardaev.security.requests.RegisterUserRequest;
import ru.mdkardaev.user.dtos.UserDTO;
import ru.mdkardaev.user.entity.User;
import ru.mdkardaev.user.exceptions.UserAlreadyExist;
import ru.mdkardaev.user.repository.UserRepository;
import ru.mdkardaev.user.roles.Role;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConversionService conversionService;

    /**
     * return user with specified login
     */
    public UserDTO getUserByLogin(String login) {
        return conversionService.convert(userRepository.findByLogin(login), UserDTO.class);
    }

    /**
     * return list of users exclude user with spicified login
     */
    public List<UserDTO> getUsersExcludeUserWithLogin(String login) {
        return userRepository.findAll()
                             .stream()
                             .filter(e -> !e.getLogin().equals(login))
                             .map(e -> conversionService.convert(e, UserDTO.class))
                             .collect(Collectors.toList());
    }
}
