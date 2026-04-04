package com.polidevtesis.inventory.service;

import com.polidevtesis.inventory.dto.request.ProviderRequest;
import com.polidevtesis.inventory.dto.response.ProviderResponse;
import com.polidevtesis.inventory.entity.Provider;
import com.polidevtesis.inventory.exception.ResourceNotFoundException;
import com.polidevtesis.inventory.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;

    public Page<ProviderResponse> findAll(Pageable pageable) {
        return providerRepository.findAllByDeletedAtIsNull(pageable)
                .map(ProviderResponse::from);
    }

    public ProviderResponse findById(Long id) {
        return ProviderResponse.from(getActiveOrThrow(id));
    }

    public ProviderResponse create(ProviderRequest request) {
        Provider provider = Provider.builder()
                .name(request.getName())
                .contactName(request.getContactName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .build();
        return ProviderResponse.from(providerRepository.save(provider));
    }

    public ProviderResponse update(Long id, ProviderRequest request) {
        Provider provider = getActiveOrThrow(id);
        provider.setName(request.getName());
        provider.setContactName(request.getContactName());
        provider.setPhone(request.getPhone());
        provider.setEmail(request.getEmail());
        return ProviderResponse.from(providerRepository.save(provider));
    }

    public void delete(Long id) {
        Provider provider = getActiveOrThrow(id);
        provider.setDeletedAt(LocalDateTime.now());
        providerRepository.save(provider);
    }

    public Provider getActiveOrThrow(Long id) {
        return providerRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Provider not found with id: " + id, "PROVIDER_NOT_FOUND"));
    }
}
