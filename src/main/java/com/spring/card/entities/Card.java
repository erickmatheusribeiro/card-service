package com.spring.card.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "card")
@Table(name = "card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "cpf")
    private String cpf;

    @Column(name ="cardnumber")
    private String cardNumber;

    @Column(name = "validation")
    private String expirationDate;

    @Column(name = "cvv")
    private String cvv;

    @Column(name = "datetimecreation")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateTimeCreated;


    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CardLimit cardLimit;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CardTransaction> cardTransactions;

}
