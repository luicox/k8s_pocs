package com.example;

import com.example.model.Transaction;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.enterprise.context.ApplicationScoped;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class TransactionRepository {

    @Inject
    EntityManager em;

    @Transactional
    public Response create(Transaction transaction) {
        em.persist(transaction);
        return Response.status(Response.Status.CREATED)
                       .entity(transaction)
                       .build();
       
    }
}
