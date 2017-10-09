package ru.mdkardaev.team.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.mdkardaev.team.dtos.InviteDTO;
import ru.mdkardaev.team.entity.Invite;

@Component
public class InviteToInviteDTOConverter implements Converter<Invite, InviteDTO> {

    @Override
    public InviteDTO convert(Invite invite) {
//        return InviteDTO.builder().
        return null;
    }
}
