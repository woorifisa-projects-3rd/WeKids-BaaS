package com.wekids.baas.card.controller;

import com.wekids.baas.card.dto.request.CardCreateRequest;
import com.wekids.baas.card.dto.request.CardStateChangeRequest;
import com.wekids.baas.card.dto.response.CardCreateResponse;
import com.wekids.baas.card.dto.response.CardStateChangeResponse;
import com.wekids.baas.card.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CardCreateResponse> createCard(@RequestBody @Valid CardCreateRequest cardCreateRequest) {
        CardCreateResponse response = cardService.createCard(cardCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/state")
    public ResponseEntity<CardStateChangeResponse> changeCardState(@RequestBody @Valid CardStateChangeRequest request){
        CardStateChangeResponse cardStateChangeResponse = cardService.changeCardState(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(cardStateChangeResponse);
    }
}
