package com.spring.card.frameworks.db;

import com.spring.card.entities.CardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardTransactionRepository extends JpaRepository<CardTransaction, Integer> {
}
