package com.wekids.baas.card.service;

import com.wekids.baas.card.dto.request.CardCreateRequest;
import com.wekids.baas.card.dto.request.CardStateChangeRequest;
import com.wekids.baas.card.dto.response.CardCreateResponse;
import com.wekids.baas.card.dto.response.CardStateChangeResponse;

public interface CardService {
    CardCreateResponse createCard(CardCreateRequest cardCreateRequest);
    CardStateChangeResponse changeCardState(CardStateChangeRequest request);
}
