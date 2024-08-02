package com.spring.card.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CardDto {

    @Schema(description = "ID do usuário que cadastrou o cartão")
    private String users;

    @Schema(description = "CPF do usuário que cadastrou o cartão", example = "12345678901")
    private String cpf;

    @NotBlank
    @Schema(description = "Número do cartão", example = "5568872479420825")
    @Size(min = 16, max = 16)
    private String numero;


    @Pattern(regexp = "^(0[1-9]|1[0-2])((\\d{2}|\\d{4}))$")
    @Schema(description = "Data de expiração do cartão", example = "0625")
    @Size(min = 4, max = 6)
    private String data_validade;


    @Pattern(regexp = "^([0-9]{3})$")
    @Schema(description = "CVV do cartão", example = "545")
    @Size(min = 3, max = 4)
    private String cvv;

}
