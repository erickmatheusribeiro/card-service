package com.spring.card.frameworks.web;

import com.spring.card.interfaceadapters.controllers.CardController;
import com.spring.card.interfaceadapters.controllers.CardTransactionController;
import com.spring.card.interfaceadapters.presenters.dto.request.CardResquestDto;
import com.spring.card.interfaceadapters.presenters.dto.request.CardTransactionRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="/api/cartao")
@Tag(name = "Cartão", description = "Metódos para manipulçação de Cartão")
public class CardWeb {

    @Autowired
    private CardController controller;

    @Autowired
    private CardTransactionController transactionController;

    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Adiciona um novo cartão")
    public ResponseEntity<?> insert(@Valid @RequestBody CardResquestDto dto){
        return controller.insert(dto);
    }

    @GetMapping()
    @Operation(summary = "recuperar o saldo do cartão pelo número")
    public ResponseEntity<?> getCard(@RequestParam @Schema(description = "CPF do usuário que cadastrou o cartão", example = "12345678901") String cpf,
                                     @RequestParam @Schema(description = "Número do cartão", example = "5568872479420825") String numero,
                                     @RequestParam @Schema(description = "Data de expiração do cartão", example = "0625") String data,
                                     @RequestParam @Schema(description = "CVV do cartão", example = "545") String cvv){
        return controller.findByCardNumber(cpf, numero, data, cvv);
    }

    @PostMapping(value = "/transactions", consumes = "application/json", produces = "application/json")
    @Operation(summary = "atualizar o saldo do cartão pelo número")
    public ResponseEntity<?> updateCard(@RequestBody @Valid CardTransactionRequestDto dto ){
        return transactionController.createTransaction(dto);
    }
}
