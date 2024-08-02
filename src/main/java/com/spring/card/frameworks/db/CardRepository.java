package com.spring.card.frameworks.db;

import com.spring.card.cards.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByCardNumber(String cardNumber);

    long countByCpf(String cpf);

    Card findByCpfAndCardNumber(String cpf, String cardNumber);
}
