package com.wekids.baas.card.repository;

import com.wekids.baas.card.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByCardNumberAndCvc(String carNumber, String cvc);
}
