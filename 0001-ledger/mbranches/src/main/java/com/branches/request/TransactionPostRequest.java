package com.branches.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionPostRequest {
    private Double value;
    private String description;
}
