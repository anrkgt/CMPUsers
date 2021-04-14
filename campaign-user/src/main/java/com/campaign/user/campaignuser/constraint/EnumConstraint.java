package com.campaign.user.campaignuser.constraint;

import com.campaign.user.campaignuser.constraint.validator.EnumValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotNull(message = "State cannot be null")
public @interface EnumConstraint {
    String[] acceptedValues();

    Class<? extends Enum<?>> enumClass();

    String message() default "State is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
