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
public class CardResquestDto {
    @JsonProperty("cpf")
    @Schema(description = "CPF do usuário que cadastrou o cartão", example = "12345678901")
    private String cpf;

    @JsonProperty("limite")
    @Schema(description = "Limite de uso do cartão", example = "1000")
    private double limite;

    @JsonProperty("numero")
    @Schema(description = "Número do cartão", example = "5568872479420825")
    private String numero;

    @JsonProperty("data_validade")
    @Schema(description = "Data de expiração do cartão", example = "0625")
    private String data_validade;

    @JsonProperty("cvv")
    @Schema(description = "CVV do cartão", example = "545")
    private String cvv;

}
