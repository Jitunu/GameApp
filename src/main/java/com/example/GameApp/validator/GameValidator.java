package com.example.GameApp.validator;

import com.example.GameApp.exception.ErrorResponse;
import com.example.GameApp.exception.GameValidationException;
import com.example.GameApp.model.Game;
import org.springframework.stereotype.Component;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.GameApp.exception.ErrorCode.BAD_REQUEST_ERROR_CODE;
import static com.example.GameApp.exception.ErrorMessage.*;

@Component
public class GameValidator implements ConstraintValidator<GameValidation, Game> {
    private static final String DATE_PATTERN = "MM/dd/yyyy HH:mm:ss";

    @Override
    public void initialize(GameValidation gameValidation) {
        ConstraintValidator.super.initialize(gameValidation);
    }


    public boolean isValid(Game game, ConstraintValidatorContext cxt) {
        List<String> errors = new ArrayList<>();
        boolean isValid = true;
        if (game == null) {
            isValid = false;
            errors.add(REQUEST_INVALID_MESSAGE.getValue());
        }
        if (game.getName() == null || game.getName().length() == 0) {
            isValid = false;
            errors.add(GAME_NAME_INVALID_MESSAGE.getValue());
        }
        if(game.getCreationDate() == null || !validDate(game.getCreationDate())) {
            isValid = false;
            errors.add(CREATION_DATE_INVALID_MESSAGE.getValue());
        }
        if(!isValid) {
            ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST_MESSAGE.getValue(), BAD_REQUEST_ERROR_CODE.getValue(), errors);
            throw new GameValidationException(errorResponse);
        } else {
            return true;
        }

    }

    private boolean validDate(String creationDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        try {
            Date date = sdf.parse(creationDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}