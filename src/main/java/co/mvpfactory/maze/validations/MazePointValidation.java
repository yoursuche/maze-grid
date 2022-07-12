package co.mvpfactory.maze.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author uchechukwu
 */
@Documented
@Constraint(validatedBy = MazePointValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MazePointValidation {

    String message() default "Alphabet/Letter part of the value must be in uppercase.Value must be AlphaNumeric";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
