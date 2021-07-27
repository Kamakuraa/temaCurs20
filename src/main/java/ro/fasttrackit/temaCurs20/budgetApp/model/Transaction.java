package ro.fasttrackit.temaCurs20.budgetApp.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Transaction {

    @Id
    @GeneratedValue

    private Integer id;

    private String product;
    private Type type;
    private Double amount;

    public Transaction(String product, Type transactionType, Double amount) {
        this.product = product;
        this.type = transactionType;
        this.amount = amount;
    }

    public Transaction() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Type getTransactionType() {
        return type;
    }

    public void setTransactionType(Type transactionType) {
        this.type = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", product='" + product + '\'' +
                ", transactionType=" + type +
                ", amount=" + amount +
                '}';
    }

}
