package com.branches.service;

import com.branches.mapper.TransactionMapper;
import com.branches.model.Transaction;
import com.branches.repository.TransactionRepository;
import com.branches.request.TransactionPostRequest;
import com.branches.response.TransactionGetResponse;
import com.branches.response.TransactionPostResponse;
import com.branches.utils.TransactionUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransactionServiceTest {
    @InjectMocks
    private TransactionService service;
    @Mock
    private TransactionRepository repository;
    @Mock
    private TransactionMapper mapper;
    private List<Transaction> transactionList;
    private List<TransactionGetResponse> transactionGetResponseList;

    @BeforeEach
    void init() {
        transactionList = TransactionUtils.newTransactionList();
        transactionGetResponseList = TransactionUtils.newTransactionGetResponseList();
    }

    @Test
    @DisplayName("save creates transaction when successful")
    @Order(1)
    void save_CreatesTransaction_WhenSuccessful() {
        TransactionPostRequest postRequest = TransactionUtils.newTransactionPostRequest();
        Transaction transactionToSave = TransactionUtils.newTransactionToSave();
        Transaction transactionSaved = TransactionUtils.newTransactionSaved();
        TransactionPostResponse postResponse = TransactionUtils.newTransactionPostResponse();

        BDDMockito.when(mapper.toTransaction(postRequest)).thenReturn(transactionToSave);
        BDDMockito.when(repository.save(transactionToSave)).thenReturn(transactionSaved);
        BDDMockito.when(mapper.toTransactionPostResponse(transactionSaved)).thenReturn(postResponse);

        TransactionPostResponse response = service.save(postRequest);
        Assertions.assertThat(response)
                .isNotNull()
                .isEqualTo(postResponse);
    }

    @Test
    @DisplayName("getBalance returns balance when successful")
    @Order(2)
    void getBalance_ReturnsBalance_WhenSuccessful() {
        double expectedBalance = TransactionUtils.getBalance();

        BDDMockito.when(repository.sumAllTransactions()).thenReturn(expectedBalance);

        Double response = service.getBalance();
        Assertions.assertThat(response)
                .isNotNull()
                .isEqualTo(expectedBalance);
    }

    @Test
    @DisplayName("findAll returns all transactions when successful")
    @Order(3)
    void findAll_ReturnsAllTransactions_WhenSuccessful() {
        Sort sort = Sort.by("date").descending();

        BDDMockito.when(repository.findAll(sort))
                .thenReturn(transactionList);
        BDDMockito.when(mapper.toTransactionGetResponseList(transactionList))
                .thenReturn(transactionGetResponseList);

        List<TransactionGetResponse> response = service.findAll(null, null);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(transactionGetResponseList);
    }

    @Test
    @DisplayName("findAll returns found transactions when starDate and endDate are given")
    @Order(4)
    void findAll_ReturnsFoundTransactions_WhenTheStartDateAndEndDateAreGiven() {
        LocalDateTime starDate = TransactionUtils.getStartDateValid();
        LocalDateTime endDate = TransactionUtils.getEndDateValid();

        Sort sort = Sort.by("date").descending();
        BDDMockito.when(repository.findAllByDateBetween(starDate, endDate, sort))
                .thenReturn(transactionList);
        BDDMockito.when(mapper.toTransactionGetResponseList(transactionList))
                .thenReturn(transactionGetResponseList);

        List<TransactionGetResponse> response = service.findAll(starDate, endDate);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(transactionGetResponseList);
    }

    @Test
    @DisplayName("findAll returns an empty list when date range does not contain transactions")
    @Order(5)
    void findAll_ReturnsEmptyList_WhenDateRangeDoesNotContainsTransactions() {
        LocalDateTime starDate = LocalDateTime.of(2030, 12, 1, 23, 59, 59);
        LocalDateTime endDate = LocalDateTime.of(2030, 12, 31, 23, 59, 59);

        BDDMockito.when(repository.findAllByDateBetween(starDate, endDate, Sort.by("date").descending()))
                .thenReturn(Collections.emptyList());
        BDDMockito.when(mapper.toTransactionGetResponseList(Collections.emptyList()))
                .thenReturn(Collections.emptyList());

        List<TransactionGetResponse> response = service.findAll(starDate, endDate);

        Assertions.assertThat(response)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findAll returns found transactions when the starDate is given")
    @Order(6)
    void findAll_ReturnsFoundTransactions_WhenTheStartDateIsGiven() {
        LocalDateTime starDate = TransactionUtils.getStartDateValid();

        BDDMockito.when(repository.findAllByDateGreaterThanEqual(starDate, Sort.by("date").descending()))
                .thenReturn(transactionList);
        BDDMockito.when(mapper.toTransactionGetResponseList(transactionList))
                .thenReturn(transactionGetResponseList);


        List<TransactionGetResponse> response = service.findAll(starDate, null);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(transactionGetResponseList);
    }



    @Test
    @DisplayName("findAll returns an empty list when does not exists transactions after starDate")
    @Order(7)
    void findAll_ReturnsEmptyList_WhenDoesNotExistsTransactionsAfterDateStart() {
        LocalDateTime starDate = LocalDateTime.of(2030, 12, 1, 23, 59, 59);

        BDDMockito.when(repository.findAllByDateGreaterThanEqual(starDate, Sort.by("date").descending()))
                .thenReturn(Collections.emptyList());
        BDDMockito.when(mapper.toTransactionGetResponseList(Collections.emptyList()))
                .thenReturn(Collections.emptyList());

        List<TransactionGetResponse> response = service.findAll(starDate, null);

        Assertions.assertThat(response)
                .isNotNull()
                .isEmpty();
    }


    @Test
    @DisplayName("findAll returns found transactions when the end date is given")
    @Order(8)
    void findAll_ReturnsFoundTransactions_WhenTheEndDateIsGiven() {
        LocalDateTime endDate = TransactionUtils.getEndDateValid();

        BDDMockito.when(repository.findAllByDateLessThanEqual(endDate, Sort.by("date").descending()))
                .thenReturn(transactionList);
        BDDMockito.when(mapper.toTransactionGetResponseList(transactionList))
                .thenReturn(transactionGetResponseList);

        List<TransactionGetResponse> response = service.findAll(null, endDate);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(transactionGetResponseList);
    }

    @Test
    @DisplayName("findAll returns an empty list when does not exists transactions before endDate")
    @Order(9)
    void findAll_ReturnsEmptyList_WhenDoesNotExistsTransactionsBeforeDateEnd() {
        LocalDateTime endDate = LocalDateTime.of(2025, 1, 1, 0, 0, 0);

        BDDMockito.when(repository.findAllByDateLessThanEqual(endDate, Sort.by("date").descending()))
                .thenReturn(Collections.emptyList());
        BDDMockito.when(mapper.toTransactionGetResponseList(Collections.emptyList()))
                .thenReturn(Collections.emptyList());

        List<TransactionGetResponse> response = service.findAll(null, endDate);

        Assertions.assertThat(response)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("getIncomes returns all positive transactions when successful")
    @Order(10)
    void getIncomes_ReturnsAllPositiveTransactions_WhenSuccessful() {
        List<Transaction> transactions = TransactionUtils.newPositiveTransactionsList();
        List<TransactionGetResponse> transactionGetResponseList = TransactionUtils.newPositiveTransactionGetResponseList();

        BDDMockito.when(repository.findAllByValueGreaterThan(0D))
                .thenReturn(transactions);
        BDDMockito.when(mapper.toTransactionGetResponseList(transactions))
                .thenReturn(transactionGetResponseList);

        List<TransactionGetResponse> response = service.getIncomes();

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(transactionGetResponseList);
    }

    @Test
    @DisplayName("getIncomes returns an empty list when does not exists positive transactions")
    @Order(11)
    void getIncomes_ReturnsEmptyList_WhenDoesNotExistsPositiveTransactions() {
        BDDMockito.when(repository.findAllByValueGreaterThan(0D))
                .thenReturn(Collections.emptyList());
        BDDMockito.when(mapper.toTransactionGetResponseList(Collections.emptyList()))
                .thenReturn(Collections.emptyList());

        List<TransactionGetResponse> response = service.getIncomes();

        Assertions.assertThat(response)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("getExpenses returns all negative transactions when successful")
    @Order(12)
    void getExpenses_ReturnsAllNegativeTransactions_WhenSuccessful() {
        List<Transaction> transactions = TransactionUtils.newNegativeTransactionsList();
        List<TransactionGetResponse> transactionGetResponseList = TransactionUtils.newNegativeTransactionGetResponseList();

        BDDMockito.when(repository.findAllByValueIsLessThan(0D))
                .thenReturn(transactions);
        BDDMockito.when(mapper.toTransactionGetResponseList(transactions))
                .thenReturn(transactionGetResponseList);

        List<TransactionGetResponse> response = service.getExpenses();

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(transactionGetResponseList);
    }

    @Test
    @DisplayName("getExpenses returns an empty list when does not exists negative transactions")
    @Order(13)
    void getExpenses_ReturnsEmptyList_WhenDoesNotExistsNegativeTransactions() {
        BDDMockito.when(repository.findAllByValueIsLessThan(0D))
                .thenReturn(Collections.emptyList());
        BDDMockito.when(mapper.toTransactionGetResponseList(Collections.emptyList()))
                .thenReturn(Collections.emptyList());

        List<TransactionGetResponse> response = service.getExpenses();

        Assertions.assertThat(response)
                .isNotNull()
                .isEmpty();
    }
}