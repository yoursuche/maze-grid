package co.mvpfactory.maze.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author uchechukwu
 */
@Slf4j
public class MazePointValidator implements
        ConstraintValidator<MazePointValidation, String> {

    @Override
    public void initialize(MazePointValidation cost) {
    }

    @Override
    public boolean isValid(String point,
            ConstraintValidatorContext cxt) {
        boolean isValid = false;
        try {
            if (isValidMazePoint(point.trim())) {
                isValid = true;
            }

        } catch (Exception ex) {
            log.error("Point Validator error==>> " + ex.getMessage());
        }
        return isValid;
    }

    boolean isValidMazePoint(String point) {
        if (point.length() < 2 || point.length() > 2) {
            return false;
        }
        char[] value = point.toCharArray();
        if (!Character.isUpperCase(value[0])) {
            return false;
        }
        Integer row = Integer.parseInt(String.valueOf(value[1]));
        return ((Character.isLetter(value[0]) && Character.isDigit(value[1])) && (row > 0 && row <= 26));
    }

}
