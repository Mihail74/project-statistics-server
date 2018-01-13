package ru.mdkardaev.user.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.mdkardaev.user.dtos.UserDTO;
import ru.mdkardaev.user.entity.User;
import ru.mdkardaev.user.repository.UserRepository;
import ru.mdkardaev.user.requests.EditUserRequest;

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
     * Return user with specified login
     */
    public UserDTO getUserByLogin(String login) {
        return conversionService.convert(userRepository.findByLogin(login), UserDTO.class);
    }

    /**
     * Return list of users exclude user with specified id
     */
    public List<UserDTO> getUsersExcludeUserWithID(Long userID) {
        return userRepository.findAll()
                .stream()
                .filter(e -> !e.getId().equals(userID))
                .map(e -> conversionService.convert(e, UserDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Edit user according to the specified request
     *
     * @return edited user
     */
    public UserDTO editUser(EditUserRequest request, Long userID) {
        User user = userRepository.findOne(userID);

        user.setName(request.getName());

        user = userRepository.save(user);
        return conversionService.convert(user, UserDTO.class);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(e -> conversionService.convert(e, UserDTO.class))
                .collect(Collectors.toList());
    }
}
