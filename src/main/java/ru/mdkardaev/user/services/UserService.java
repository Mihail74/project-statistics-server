package ru.mdkardaev.user.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mdkardaev.common.exceptions.sql.SQLStates;
import ru.mdkardaev.common.exceptions.utils.DBExceptionUtils;
import ru.mdkardaev.security.jwt.JwtFactory;
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
    private DBExceptionUtils dbExceptionUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtFactory jwtTokenFactory;
    @Autowired
    private ConversionService conversionService;


    /**
     * return list of users exclude user with login == userName
     */
    public List<UserDTO> getUsers(String userName) {
        return userRepository.findAll()
                .stream()
                .filter(e -> !e.getLogin().equals(userName))
                .map(e -> conversionService.convert(e, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO getUser(String login) {
        return conversionService.convert(userRepository.findByLogin(login), UserDTO.class);
    }

    public void register(RegisterUserRequest request) {

        User user = User.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .roles(new HashSet<>(Collections.singletonList(Role.USER)))
                .build();

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            dbExceptionUtils
                    .conditionThrowNewException(e,
                                                SQLStates.UNIQUE_VIOLATION,
                                                () -> new UserAlreadyExist(String.format(
                                                        "User with login: [%s] already exist.",
                                                        request.getLogin())));
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
