package com.spring.card.frameworks.db;

import com.spring.card.cards.CardLimit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardLimitRepository extends JpaRepository<CardLimit, Integer> {
    CardLimit findByCardId(Integer cardId);
}
