package com.myretailapi.Controller;

import com.myretailapi.Handler.RetailHandler;
import com.myretailapi.Model.RetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/products")
public class RetailController {

    private final static Logger LOGGER = Logger.getLogger(RetailController.class.getName());

    @Autowired
    private RetailHandler retailHandler;

    /* Basic get call to ensure the service can be pinged on hosted port */
    @RequestMapping(value ="/ping", method = RequestMethod.GET)
    public ResponseEntity ping(){
        LOGGER.info("Enter RetailController.ping");
        return new ResponseEntity(HttpStatus.OK);
    }

    /* Get call with parameter {id} to retrieve retail object associated to that id */
    /* Set to return a JSON object */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<RetailVO> getProductDetails(@PathVariable Integer id) throws Exception{
        LOGGER.info("Enter RetailController.getProductDetails");
        ResponseEntity<RetailVO> returnResponse = retailHandler.getProductDetails(id);
        LOGGER.info("Exit RetailController.getProductDetails");
        return returnResponse;
    }

    /* Put call with parameter {id} and JSON object (RetailVO) found in request body to update retail information associated to that id */
    /* Expecting to consume a JSON object */
    /* Set to return a JSON object */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
            consumes = "application/json", produces = "application/json")
    public ResponseEntity<RetailVO> updateProductDetails(@PathVariable Integer id, @RequestBody RetailVO retailVO) throws Exception{
        LOGGER.info("Enter RetailController.updateProductDetails");
        LOGGER.info(retailVO.getCurrentPrice().getValue());
        LOGGER.info(id.toString());
        ResponseEntity<RetailVO> returnResponse = retailHandler.updateProductDetails(id,retailVO);
        LOGGER.info("Exit RetailController.updateProductDetails");
        return returnResponse;
    }

}
