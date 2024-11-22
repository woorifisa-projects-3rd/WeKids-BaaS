package com.wekids.baas.card.service;

import com.wekids.baas.card.dto.request.CardCreateRequest;
import com.wekids.baas.card.dto.response.CardCreateResponse;

public interface CardService {
    CardCreateResponse createCard(CardCreateRequest cardCreateRequest);
}
