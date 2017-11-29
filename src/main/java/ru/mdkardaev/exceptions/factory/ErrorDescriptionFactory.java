package ru.mdkardaev.exceptions.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mdkardaev.exceptions.constants.ErrorID;
import ru.mdkardaev.exceptions.responses.ErrorDescription;
import ru.mdkardaev.i18n.services.Messages;

@Component
public class ErrorDescriptionFactory {

    @Autowired
    private Messages messages;

    public ErrorDescription createInvalidParameterError(String parameter, String detail) {
        return new ErrorDescription(ErrorID.INVALID_PARAMETER, parameter, detail);
    }
}
