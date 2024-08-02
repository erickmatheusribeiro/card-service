package com.spring.card.cards;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "card_transaction")
@Table(name = "card_transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "paymentid")
    private String paymentId;

    @Column(name = "value")
    private double value;

    @Column(name = "datetimecreation")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateTimeCreated;

    @OneToOne
    @JoinColumn(name = "cardid")
    private Card card;
}
