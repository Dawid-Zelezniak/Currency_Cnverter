package org.converter.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.converter.util.Money;

public record AccountCreationRequest(
        @NotBlank(message = "First name can not be blank")
        @Size(min = 3, message = "Name size must contains at lest 3 characters")
        String firstName,

        @NotBlank(message = "Last name can not be blank")
        @Size(min = 3, message = "Last name size must contains at lest 3 characters")
        String lastName,

        Money startBalance
) {


}
