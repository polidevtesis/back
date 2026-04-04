package com.polidevtesis.inventory.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProviderRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 150)
    private String name;

    @Size(max = 150)
    private String contactName;

    @Size(max = 30)
    private String phone;

    @Email
    @Size(max = 100)
    private String email;
}
