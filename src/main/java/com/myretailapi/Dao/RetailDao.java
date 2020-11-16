package com.myretailapi.Dao;

import com.myretailapi.Model.CurrentPrice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RetailDao extends MongoRepository<CurrentPrice,Integer> {
    /* Call to the local Mongo DB to retrieve CurrentPrice object by Id */
    CurrentPrice findById(String id);
}
