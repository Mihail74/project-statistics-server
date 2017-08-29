package ru.mdkardaev.user.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.mdkardaev.common.exceptions.sql.SQLStates;
import ru.mdkardaev.common.exceptions.utils.DBExceptionUtils;
import ru.mdkardaev.user.dtos.UserDTO;
import ru.mdkardaev.user.entity.User;
import ru.mdkardaev.user.exceptions.UserAlreadyExist;
import ru.mdkardaev.user.repository.UserRepository;
import ru.mdkardaev.user.requests.RegisterUserRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private DBExceptionUtils dbExceptionUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConversionService conversionService;

    public void register(RegisterUserRequest request) {

        User user = User.builder()
                        .name(request.getName())
                        .password(request.getPassword())
                        .email(request.getEmail())
                        .build();

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            dbExceptionUtils
                    .conditionThrowNewException(e,
                                                SQLStates.UNIQUE_VIOLATION,
                                                () -> new UserAlreadyExist(String.format(
                                                        "User with email: [%s] already exist.",
                                                        request.getEmail())));
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public List<UserDTO> getUsers() {
        return userRepository.findAll()
                             .stream()
                             .map(e -> conversionService.convert(e, UserDTO.class))
                             .collect(Collectors.toList());
    }
}
