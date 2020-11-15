package com.myretailapi.Handler;

import com.myretailapi.Model.RetailVO;
import com.myretailapi.Service.RetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class RetailHandler {

    private final static Logger LOGGER = Logger.getLogger(RetailHandler.class.getName());
    private String ID_REGEX = "^[0-9]*$";

    @Autowired
    RetailService retailService;

    public ResponseEntity<RetailVO> getProductDetails(Integer id) throws Exception{
        LOGGER.info("Enter RetailHandler.getProductDetails");
        RetailVO returnObject;
        ResponseEntity<RetailVO> returnEntity;

        if(!id.toString().matches(ID_REGEX)){
            LOGGER.info("Error... Exit RetailHandler.getProductDetails");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            returnObject = retailService.getProductDetails(id);
        }

        if(returnObject != null) {
            returnEntity = ResponseEntity.ok(returnObject);
        }else{
            LOGGER.info("Error... Exit RetailHandler.getProductDetails");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LOGGER.info("Exit RetailHandler.getProductDetails");
        return returnEntity;
    }

    public ResponseEntity<RetailVO> updateProductDetails(Integer id, RetailVO retailVO) throws Exception{
        LOGGER.info("Enter RetailHandler.updateProductDetails");
        RetailVO returnObject;
        ResponseEntity<RetailVO> returnEntity;

        if(!id.toString().matches(ID_REGEX)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            returnObject = retailService.updateProductDetails(retailVO);
        }

        if(returnObject != null) {
            returnEntity = ResponseEntity.ok(returnObject);
        }else{
            LOGGER.info("Error... Exit RetailHandler.getProductDetails");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LOGGER.info("Exit RetailHandler.getProductDetails");
        return returnEntity;
    }
}
