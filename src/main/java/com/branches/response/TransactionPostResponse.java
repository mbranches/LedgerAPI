package com.branches.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionPostResponse {
    private Long id;
    private Double value;
    private String description;
    private LocalDateTime date;
}
