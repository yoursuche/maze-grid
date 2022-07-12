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
@Constraint(validatedBy = GridSizeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GridSizeValidation {

    String message() default "Invalid grid size value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
