package com.branches.controller;

import com.branches.request.TransactionPostRequest;
import com.branches.response.TransactionGetResponse;
import com.branches.response.TransactionPostResponse;
import com.branches.service.TransactionService;
import com.branches.utils.FileUtils;
import com.branches.utils.TransactionUtils;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@WebMvcTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.branches")
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private TransactionService service;
    private final String URL = "/v1/transactions";
    @Autowired
    private FileUtils fileUtils;
    private List<TransactionGetResponse> transactionGetResponseList;

    @BeforeEach
    void init() {
        transactionGetResponseList = TransactionUtils.newTransactionGetResponseList();
    }

    @Test
    @DisplayName("POST /v1/transactions creates transaction when successful")
    @Order(1)
    void save_CreatesTransaction_WhenSuccessful() throws Exception {
        String request = fileUtils.readResourceFile("transaction/post-request-transaction-201.json");
        String expectedResponse = fileUtils.readResourceFile("transaction/post-response-transaction-201.json");

        TransactionPostRequest postRequest = TransactionUtils.newTransactionPostRequest();
        TransactionPostResponse postResponse = TransactionUtils.newTransactionPostResponse();

        BDDMockito.when(service.save(postRequest)).thenReturn(postResponse);

        mockMvc.perform(
                MockMvcRequestBuilders.post(URL)
                    .content(request)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("GET /v1/transactions/balance returns balance when successful")
    @Order(2)
    void getBalance_ReturnsBalance_WhenSuccessful() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("transaction/get-balance-200.json");

        double balance = TransactionUtils.getBalance();

        BDDMockito.when(service.getBalance()).thenReturn(balance);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/balance"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("GET /v1/transactions returns all transactions when successful")
    @Order(3)
    void findAll_ReturnsAllTransactions_WhenSuccessful() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("transaction/get-transactions-200.json");

        BDDMockito.when(service.findAll(null, null))
                .thenReturn(transactionGetResponseList);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("GET /v1/transactions?startDate=2025-01-29T15:43:30&endDate=2025-01-28T14:20:30 returns found transactions when dateStart and dateEnd are given")
    @Order(4)
    void findAll_ReturnsFoundTransactions_WhenTheStartDateAndEndDateAreGiven() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("transaction/get-transactions-200.json");

        LocalDateTime startDate = TransactionUtils.getStartDateValid();
        LocalDateTime endDate = TransactionUtils.getEndDateValid();

        BDDMockito.when(service.findAll(startDate, endDate))
                .thenReturn(transactionGetResponseList);

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("GET /v1/transactions?startDate=2030-12-01T23:59:59&endDate=2030-12-31T23:59:59 returns an empty list when date range does not contain transactions")
    @Order(5)
    void findAll_ReturnsEmptyList_WhenDateRangeDoesNotContainsTransactions() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("transaction/get-transactions-invalid-date-200.json");

        LocalDateTime startDate = LocalDateTime.of(2030, 12, 1, 23, 59, 59);
        LocalDateTime endDate = LocalDateTime.of(2030, 12, 31, 23, 59, 59);


        BDDMockito.when(service.findAll(startDate, endDate))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("GET /v1/transactions?startDate=2025-01-29T15:43:30 returns found transactions when the dateStart is given")
    @Order(6)
    void findAll_ReturnsFoundTransactions_WhenTheStartDateIsGiven() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("transaction/get-transactions-200.json");

        LocalDateTime startDate = TransactionUtils.getStartDateValid();

        BDDMockito.when(service.findAll(startDate, null))
                .thenReturn(transactionGetResponseList);

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .param("startDate", startDate.toString())
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }



    @Test
    @DisplayName("GET /v1/transactions?startDate=2030-12-01T23:59:59 returns an empty list when does not exists transactions after dateStart")
    @Order(7)
    void findAll_ReturnsEmptyList_WhenDoesNotExistsTransactionsAfterDateStart() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("transaction/get-transactions-invalid-startDate-200.json");

        LocalDateTime startDate = LocalDateTime.of(2030, 12, 1, 23, 59, 59);

        BDDMockito.when(service.findAll(startDate, null))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .param("startDate", startDate.toString())
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }


    @Test
    @DisplayName("GET /v1/transactions?endDate=2025-01-28T14:20:30 returns found transactions when the end date is given")
    @Order(8)
    void findAll_ReturnsFoundTransactions_WhenTheEndDateIsGiven() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("transaction/get-transactions-200.json");

        LocalDateTime endDate = TransactionUtils.getEndDateValid();

        BDDMockito.when(service.findAll(null, endDate))
                .thenReturn(transactionGetResponseList);

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .param("endDate", endDate.toString())
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("GET /v1/transactions?endDate=2025-01-01T00:00:00 returns an empty list when does not exists transactions before dateEnd")
    @Order(9)
    void findAll_ReturnsEmptyList_WhenDoesNotExistsTransactionsBeforeDateEnd() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("transaction/get-transactions-invalid-endDate-200.json");

        LocalDateTime endDate = LocalDateTime.of(2025, 1, 1, 0, 0, 0);

        BDDMockito.when(service.findAll(null, endDate))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .param("endDate", endDate.toString())
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("GET /v1/transactions/incomes returns all positive transactions when successful")
    @Order(10)
    void getIncomes_ReturnsAllPositiveTransactions_WhenSuccessful() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("transaction/get-positives-transactions-200.json");

        List<TransactionGetResponse> positiveTransactionGetResponseList = TransactionUtils.newPositiveTransactionGetResponseList();

        BDDMockito.when(service.getIncomes())
                .thenReturn(positiveTransactionGetResponseList);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/incomes"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("GET /v1/transactions/incomes returns an empty list when does not exists positive transactions")
    @Order(11)
    void getIncomes_ReturnsEmptyList_WhenDoesNotExistsPositiveTransactions() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("transaction/get-empty-positive-transactions-200.json");

        BDDMockito.when(service.getIncomes())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/incomes"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("GET /v1/transactions/expenses returns all negative transactions when successful")
    @Order(12)
    void getExpenses_ReturnsAllNegativeTransactions_WhenSuccessful() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("transaction/get-negatives-transactions-200.json");

        List<TransactionGetResponse> negativeTransactionGetResponseList = TransactionUtils.newNegativeTransactionGetResponseList();

        BDDMockito.when(service.getExpenses())
                .thenReturn(negativeTransactionGetResponseList);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/expenses"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("GET /v1/transactions/expenses returns an empty list when does not exists negative transactions")
    @Order(13)
    void getExpenses_ReturnsEmptyList_WhenDoesNotExistsNegativeTransactions() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("transaction/get-empty-negative-transactions-200.json");

        BDDMockito.when(service.getExpenses())
                .thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/expenses"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }
}