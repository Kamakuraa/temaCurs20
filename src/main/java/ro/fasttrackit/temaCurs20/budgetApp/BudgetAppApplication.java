package ro.fasttrackit.temaCurs20.budgetApp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ro.fasttrackit.temaCurs20.budgetApp.model.Transaction;
import ro.fasttrackit.temaCurs20.budgetApp.model.Type;
import ro.fasttrackit.temaCurs20.budgetApp.repository.TransactionRepository;

import java.util.List;

@SpringBootApplication
public class BudgetAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetAppApplication.class, args);
    }

    @Bean
    CommandLineRunner asStart(TransactionRepository transactionRepository) {
        return args -> {
            transactionRepository.saveAll(List.of(
                    new Transaction("Laptop", Type.SELL, 999.9),
                    new Transaction("Computer", Type.SELL, 111.1),
                    new Transaction("TV", Type.SELL, 900.9),
                    new Transaction("Radio", Type.BUY, 899.9)
            ));
        };
    }
}
