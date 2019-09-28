package org.ecohub.rest.api.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TrashOperationValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TrashOperationAnnotation {
    String message() default "{error.area}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}