package org.ecohub.rest.api.validation;

import org.ecohub.rest.api.data.Area;
import org.ecohub.rest.model.Location;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AreaValidator implements ConstraintValidator<AreaAnnotation, Area> {

    @Override
    public void initialize(AreaAnnotation constraintAnnotation) {

    }

    /**
     * Validate area, 'from' x,y should be lesser or equal 'to' x, y
     */
    public boolean isValid(Area object, ConstraintValidatorContext context) {
        if (!(object instanceof Area)) {
            throw new IllegalArgumentException("@Area only applies to Area");
        }

        Location from = object.getFrom();
        Location to = object.getTo();
        return from.getLongitude()<= from.getLongitude() && from.getLatitude() <= to.getLatitude() ;

    }
}