package org.ecohub.rest.api.validation;

import org.ecohub.rest.model.TrashOperation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TrashOperationValidator implements ConstraintValidator<TrashOperationAnnotation, TrashOperation> {

    @Override
    public void initialize(TrashOperationAnnotation constraintAnnotation) {

    }

    /**
     * Validate area, 'from' x,y should be lesser or equal 'to' x, y
     */
    public boolean isValid(TrashOperation object, ConstraintValidatorContext context) {
        if (!(object instanceof TrashOperationAnnotation)) {
            throw new IllegalArgumentException("@TrashOperation only applies to TrashOperation");
         }

        return object.getClientId() != null && object.getReceiverId() != null;
    }
}