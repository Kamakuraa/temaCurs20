package ro.fasttrackit.temaCurs20.budgetApp.service;

import org.springframework.stereotype.Service;
import ro.fasttrackit.temaCurs20.budgetApp.model.Transaction;
import ro.fasttrackit.temaCurs20.budgetApp.model.Type;
import ro.fasttrackit.temaCurs20.budgetApp.repository.TransactionRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAll(Type type, Double minAmount, Double maxAmount) {
        if (type == null && minAmount == null && maxAmount == null) {
            return transactionRepository.findAll();
        } else if (type != null && minAmount == null && maxAmount == null) {
            return transactionRepository.findByType(type);
        } else if (type == null && minAmount != null && maxAmount == null) {
            return transactionRepository.findByAmountGreaterThan(minAmount);
        } else if (type == null && minAmount == null && maxAmount != null) {
            return transactionRepository.findByAmountLessThan(minAmount);
        } else if (type != null && minAmount != null && maxAmount == null) {
            return transactionRepository.findByTypeAndAmountGreaterThan(type, minAmount);
        } else if (type != null && minAmount == null && maxAmount != null) {
            return transactionRepository.findByTypeAndAmountLessThan(type, minAmount);
        } else if (type == null && minAmount != null && maxAmount != null) {
            return transactionRepository.AmountGreaterThanAndAmountLessThan(minAmount, maxAmount);
        }
        return transactionRepository.findByTypeAndAmountGreaterThanAndAmountLessThan(type, minAmount, maxAmount);
    }

    public Optional<Transaction> findById(int transactionId) {
        return transactionRepository.findAll().stream()
                .filter(transaction -> transaction.getId() == transactionId)
                .findFirst();
    }

    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> replaceTransaction(int transactionId, Transaction newTransaction) {
        Optional<Transaction> replaceTransaction = deleteTransaction(transactionId);
        replaceTransaction
                .ifPresent(deleteTrans -> addTransaction(newTransaction));
        return replaceTransaction;
    }

    public Optional<Transaction> deleteTransaction(int transactionId) {
        Optional<Transaction> replaceTransaction = findById(transactionId);
        replaceTransaction
                .ifPresent(transactionRepository::delete);
        return replaceTransaction;
    }

    public Optional<Transaction> patchTransaction(int transactionId, Transaction newTransaction) {
        Optional<Transaction> transactionById = findById(transactionId);
        Optional<Transaction> patchedTransaction = transactionById
                .map(oldTransaction -> new Transaction(
                        newTransaction.getProduct() != null ? newTransaction.getProduct() : oldTransaction.getProduct(),
                        newTransaction.getTransactionType() != null ? newTransaction.getTransactionType() : oldTransaction.getTransactionType(),
                        newTransaction.getAmount() != 0 ? newTransaction.getAmount() : oldTransaction.getAmount()
                ));
        patchedTransaction.ifPresent(newTrans -> replaceTransaction(transactionId, newTransaction));
        return patchedTransaction;
    }

    public Map<Type, List<Transaction>> mapTypeToSumAmount() {
        return transactionRepository.findAll().stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getTransactionType()));
    }


    public Map<String, List<Transaction>> mapFromProductAmount() {
        return transactionRepository.findAll().stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getProduct()));
    }
}
