package com.example.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Transaction extends PanacheEntity {
    public String accountNumber;
    public String operationNumber;
    public double amount;
}
