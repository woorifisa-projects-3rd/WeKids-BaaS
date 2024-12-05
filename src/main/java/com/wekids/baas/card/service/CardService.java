package com.wekids.baas.card.service;

import com.wekids.baas.card.dto.request.CardCreateRequest;
import com.wekids.baas.card.dto.request.CardStateRequest;
import com.wekids.baas.card.dto.response.CardCreateResponse;
import com.wekids.baas.card.dto.response.CardStateResponse;

public interface CardService {
    CardCreateResponse createCard(CardCreateRequest cardCreateRequest);
    CardStateResponse changeCardState(CardStateRequest request);
}
