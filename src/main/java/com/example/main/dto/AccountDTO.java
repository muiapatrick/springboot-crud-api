package com.example.main.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountDTO {
    @NotBlank(message = "Iban Required")
    private String iban;

    @NotBlank(message = "BICSwift Required")
    private String bicSwift;

    @NotBlank(message = "Client ID Required")
    private String clientId;
}
