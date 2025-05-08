package com.branches.mapper;

import com.branches.model.Transaction;
import com.branches.request.TransactionPostRequest;
import com.branches.response.TransactionPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {

    Transaction toTransaction(TransactionPostRequest postRequest);

    TransactionPostResponse toTransactionPostResponse(Transaction transaction);
}
