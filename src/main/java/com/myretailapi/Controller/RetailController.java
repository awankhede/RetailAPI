package com.myretailapi.Controller;

import com.myretailapi.Handler.RetailHandler;
import com.myretailapi.Model.RetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

@RestController
@RequestMapping("/products")
public class RetailController {

    private final static Logger LOGGER = Logger.getLogger(RetailController.class.getName());

    @Autowired
    private RetailHandler retailHandler;

    @RequestMapping(value ="/ping", method = RequestMethod.GET)
    public ResponseEntity ping(){
        LOGGER.info("Enter RetailController.ping");
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<RetailVO> getProductDetails(@PathVariable Integer id) throws Exception{
        LOGGER.info("Enter RetailController.getProductDetails");
        ResponseEntity<RetailVO> returnResponse = retailHandler.getProductDetails(id);
        LOGGER.info("Exit RetailController.getProductDetails");
        return returnResponse;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<RetailVO> updateProductDetails(@PathVariable Integer id, @RequestBody RetailVO retailVO) throws Exception{
        LOGGER.info("Enter RetailController.updateProductDetails");
        LOGGER.info(retailVO.getCurrentPrice().getValue());
        LOGGER.info(id.toString());
        ResponseEntity<RetailVO> returnResponse = retailHandler.updateProductDetails(id,retailVO);
        LOGGER.info("Exit RetailController.updateProductDetails");
        return returnResponse;
    }

}
