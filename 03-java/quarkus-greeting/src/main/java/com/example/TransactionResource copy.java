// package com.example;

// import com.example.model.Transaction;
// import jakarta.transaction.Transactional;
// import jakarta.ws.rs.*;
// import jakarta.ws.rs.core.MediaType;
// import jakarta.ws.rs.core.Response;

// @Path("/transactions")
// @Consumes(MediaType.APPLICATION_JSON)
// @Produces(MediaType.APPLICATION_JSON)
// public class TransactionResource {

//     @POST
//     @Transactional
//     public Response create(Transaction transaction) {
//         transaction.persist();
//         return Response.status(Response.Status.CREATED)
//                        .entity(transaction)
//                        .build();
//     }
// }
