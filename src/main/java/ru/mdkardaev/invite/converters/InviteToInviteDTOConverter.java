package ru.mdkardaev.invite.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.mdkardaev.invite.dtos.InviteDTO;
import ru.mdkardaev.invite.entity.Invite;
import ru.mdkardaev.team.dtos.TeamDTO;
import ru.mdkardaev.user.dtos.UserDTO;

@Component
public class InviteToInviteDTOConverter implements Converter<Invite, InviteDTO> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public InviteDTO convert(Invite invite) {
        return InviteDTO.builder()
                        .id(invite.getId())
                        .status(invite.getStatus())
                        .team(conversionService.convert(invite.getTeam(), TeamDTO.class))
                        .user(conversionService.convert(invite.getUser(), UserDTO.class))
                        .build();
    }
}
