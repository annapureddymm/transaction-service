package com.maveric.transactionservice.controller;
import com.maveric.transactionservice.dto.PairClassDto;
import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.service.TransactionService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    TransactionService transactionService;



    /* Dummy Returns list of total transactions */
    @GetMapping("accounts/{accountId}/transaction")
    public ResponseEntity<List<TransactionDto>> getTransactions(@PathVariable String accountId,@RequestParam(defaultValue = "0") Integer page,
                                                                @RequestParam(defaultValue = "10") Integer pageSize)  {

        List<TransactionDto> transactionDtoResponse = transactionService.getTransactions(page,pageSize);
        return new ResponseEntity<>(transactionDtoResponse, HttpStatus.OK);
    }

    /* Returns list of transactions for the given valid Account ID */
    @GetMapping("accounts/{accountId}/transactions")
    public ResponseEntity<List<TransactionDto>> getTransactionsByAccountId(@PathVariable String accountId,@RequestParam(defaultValue = "0") Integer page,
                                                                           @RequestParam(defaultValue = "5") Integer pageSize)  {
        log.info("API call returning list of transactions for the given valid Account Id");
        List<TransactionDto> transactionDtoResponse = transactionService.getTransactionsByAccountId(page,pageSize,accountId);
        return new ResponseEntity<>(transactionDtoResponse, HttpStatus.OK);
    }

    /* Saves a valid transaction */
    @PostMapping("accounts/{accountId}/transactions")
    public ResponseEntity<TransactionDto> createTransaction(@PathVariable String accountId, @Valid @RequestBody TransactionDto transactionDto){
        log.info("API call to create a new transaction for valid Account Id");
        PairClassDto createResponse = transactionService.createTransaction(accountId,transactionDto);
        log.info("Balance information updated successfully");
        return new ResponseEntity<>(createResponse.getTransactionDto(), HttpStatus.CREATED);
    }

    /* Returns a valid transaction based on Transaction Id*/
    @GetMapping("accounts/{accountId}/transactions/{transactionId}")
    public ResponseEntity<TransactionDto> getTransactionDetails(@PathVariable String accountId,@PathVariable String transactionId) {
        log.info("API call to retrieve transaction information for valid Transaction Id");
        TransactionDto transactionDtoResponse = transactionService.getTransactionById(transactionId);
        log.info("Transaction retrieved from DB");
        return new ResponseEntity<>(transactionDtoResponse, HttpStatus.OK);
    }

    /* Deletes a valid transaction based on Transaction Id */
    @DeleteMapping("accounts/{accountId}/transactions/{transactionId}")
    public ResponseEntity<String> deleteTransaction(@PathVariable String accountId,@PathVariable String transactionId) {
        log.info("API call to delete transaction based on Transaction Id");
        String result = transactionService.deleteTransaction(transactionId);
        log.info("Transaction deleted successfully");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



}
