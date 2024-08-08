package com.spring.card.interfaceadapters.presenters.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CardTransactionRequestDto {
    @JsonProperty("cpf")
    @Schema(description = "CPF do usuário que cadastrou o cartão", example = "21910056081")
    private String cpf;

    @JsonProperty("numero")
    @Schema(description = "Número do cartão", example = "5568872479420825")
    private String cardNumber;

    @JsonProperty("data_validade")
    @Schema(description = "Data de expiração do cartão", example = "0625")
    private String expirationDate;

    @JsonProperty("cvv")
    @Schema(description = "CVV do cartão", example = "545")
    private String cvv;

    @JsonProperty("valor")
    @Schema(description = "Valor a ser transferido", example = "100")
    private double value;
}
