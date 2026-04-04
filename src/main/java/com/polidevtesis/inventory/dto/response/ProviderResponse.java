package com.polidevtesis.inventory.dto.response;

import com.polidevtesis.inventory.entity.Provider;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProviderResponse {

    private Long id;
    private String name;
    private String contactName;
    private String phone;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProviderResponse from(Provider p) {
        ProviderResponse r = new ProviderResponse();
        r.id = p.getId();
        r.name = p.getName();
        r.contactName = p.getContactName();
        r.phone = p.getPhone();
        r.email = p.getEmail();
        r.createdAt = p.getCreatedAt();
        r.updatedAt = p.getUpdatedAt();
        return r;
    }
}
