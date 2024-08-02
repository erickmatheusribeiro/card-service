package com.spring.card.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "card_limit")
@Table(name = "card_limit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @OneToOne
    @JoinColumn(name = "cardid")
    private Card card;

    @Column(name = "limite")
    private double limite;

    @Column(name = "updated")
    private LocalDateTime updated;

}
