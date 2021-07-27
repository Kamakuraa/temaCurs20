package ro.fasttrackit.temaCurs20.budgetApp.controller;

import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.temaCurs20.budgetApp.model.Transaction;
import ro.fasttrackit.temaCurs20.budgetApp.model.Type;
import ro.fasttrackit.temaCurs20.budgetApp.service.TransactionService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    public final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    List<Transaction> getAll(@RequestParam(required = false) Type type,
                             @RequestParam(required = false) Double minAmount,
                             @RequestParam(required = false) Double maxAmount) {
        return transactionService.getAll(type, minAmount, maxAmount);
    }

    @GetMapping("{transactionId}")
    Transaction getById(@PathVariable int transactionId) {
        return transactionService.findById(transactionId)
                .orElse(null);
    }

    @PostMapping
    Transaction createTransaction(@RequestBody Transaction transaction){
        return transactionService.addTransaction(transaction);
    }

    @PutMapping("{transactionId}")
    Optional<Transaction> replaceTransaction(@PathVariable int transactionId,
                                   @RequestParam Transaction newTransaction){
        return transactionService.replaceTransaction(transactionId, newTransaction);
    }

    @DeleteMapping("{transactionId}")
    Transaction deleteTransaction (@PathVariable int transactionId){
        return transactionService.deleteTransaction(transactionId)
                .orElse(null);
    }

    @PatchMapping("{transactionId}")
    Transaction patchTransaction(@PathVariable int transactionId,
                                 @RequestBody Transaction newTransaction){
        return transactionService.patchTransaction(transactionId, newTransaction)
                .orElse(null);
    }

    @GetMapping("transaction/type")
    Map<Type, List<Transaction>> mapTypeToSumAmount(){
        return transactionService.mapTypeToSumAmount();
    }

    @GetMapping("transaction/product")
    Map<String, List<Transaction>> mapFromProductAmount(){
        return transactionService.mapFromProductAmount();
    }


}
