package com.myretailapi.Dao;

import com.myretailapi.Model.CurrentPrice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RetailDao extends MongoRepository<CurrentPrice,Integer> {
    CurrentPrice findById(String id);
}
