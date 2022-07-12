package co.mvpfactory.maze.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author uchechukwu
 */
@Slf4j
public class GridSizeValidator implements
        ConstraintValidator<GridSizeValidation, String> {

    final String delimiter = "x";

    @Override
    public void initialize(GridSizeValidation cost) {
    }

    @Override
    public boolean isValid(String size,
            ConstraintValidatorContext cxt) {
        boolean isValid = false;
        try {
            if (isValidGridSize(size.trim())) {
                isValid = true;
            }

        } catch (Exception ex) {
            log.error("Grid Size Validator error==>> " + ex.getMessage());
        }
        return isValid;
    }

    boolean isValidGridSize(String size) {
        String[] gridSizeDimensions = size.split(delimiter);
        Integer column = Integer.parseInt(String.valueOf(gridSizeDimensions[0]));
        Integer row = Integer.parseInt(String.valueOf(gridSizeDimensions[1]));
        return ((column >= 2 && column <= 26) && (row >= 2));
    }

}
