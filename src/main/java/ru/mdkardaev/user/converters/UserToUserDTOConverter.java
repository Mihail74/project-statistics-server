package ru.mdkardaev.user.converters;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.mdkardaev.user.dtos.UserDTO;
import ru.mdkardaev.user.entity.User;

@Component
public class UserToUserDTOConverter implements Converter<User, UserDTO> {

    @Override
    public UserDTO convert(User from) {
        return UserDTO.builder()
                      .id(from.getId())
                      .name(from.getName())
                      .email(from.getEmail()).build();
    }
}
