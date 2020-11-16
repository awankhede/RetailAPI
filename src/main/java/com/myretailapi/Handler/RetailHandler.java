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

    /*  Handler Method to get Product Details based on ID provided */
    public ResponseEntity<RetailVO> getProductDetails(Integer id) throws Exception{
        LOGGER.info("Enter RetailHandler.getProductDetails");
        RetailVO returnObject;
        ResponseEntity<RetailVO> returnEntity;
        String idString = id.toString();

        /* Making & checking the assumption that ids can only be numeric and of length 8 */
        if(!idString.matches(ID_REGEX) || !(idString.length() == 8)){
            LOGGER.info("Error... Exit RetailHandler.getProductDetails");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            /* Retrieve retail object from the service */
            returnObject = retailService.getProductDetails(idString);
        }

        if(returnObject != null) {
            /* Send a success status with request object as a json */
            returnEntity = ResponseEntity.ok(returnObject);
        }else{
            /* Fail if no object returned */
            LOGGER.info("Error... Exit RetailHandler.getProductDetails");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LOGGER.info("Exit RetailHandler.getProductDetails");
        return returnEntity;
    }

    /*  Handler Method to update Product Details based on ID provided  */
    public ResponseEntity<RetailVO> updateProductDetails(Integer id, RetailVO retailVO) throws Exception {
        LOGGER.info("Enter RetailHandler.updateProductDetails");
        RetailVO returnObject;
        String idString = id.toString();
        ResponseEntity<RetailVO> returnEntity;

        /* Check to see that the provided id and the id in the retail object are the same */
        if (!idString.equals(retailVO.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!idString.matches(ID_REGEX) || !(idString.length() == 8)) {
            /* Making & checking the assumption that ids can only be numeric and of length 8 */
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (retailVO.getCurrentPrice() == null || retailVO.getCurrentPrice().getCurrencyCode()==null||retailVO.getCurrentPrice().getValue()==null){
            /* Making & checking the assumption that product price and currency codes cannot be null */
            throw new Exception(" Please check product price and currency code details, it should not be empty ");
        }

        /* Retrieve & update retail object from the service */
        if(retailService.updateProductDetails(retailVO)) {
            returnEntity = ResponseEntity.ok(retailVO);
        }else{
            /* Fail if any exception thrown */
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LOGGER.info("Exit RetailHandler.getProductDetails");
        return returnEntity;
    }
}
