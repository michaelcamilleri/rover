package com.rover.interview.validation.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class OwnerForm {

    @NotBlank(message = "Name is required")
    @ApiModelProperty(required = true)
    private String name;

    @NotBlank(message = "Phone number is required")
    @ApiModelProperty(required = true)
    private String phoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Incorrect email format")
    @ApiModelProperty(required = true)
    private String email;

    private String imageUrl;
}
