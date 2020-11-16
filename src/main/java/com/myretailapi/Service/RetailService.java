package com.myretailapi.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import com.myretailapi.Dao.RetailDao;
import com.myretailapi.Model.CurrentPrice;
import com.myretailapi.Model.RetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.logging.Logger;

@Service
@Scope(proxyMode= ScopedProxyMode.TARGET_CLASS)
public class RetailService {

    private final static Logger LOGGER = Logger.getLogger(RetailService.class.getName());

    private final static String URL_FRONT = "https://redsky.target.com/v3/pdp/tcin/";
    private final static String URL_END = "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics&key=candidate";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RetailDao retailDao;

    /*  Service Method to get Product Details based on ID provided  */
    public RetailVO getProductDetails(String id) throws Exception {
        LOGGER.info("Enter RetailService.getProductDetails");

        /* Get product name -- rest call*/
        RetailVO returnObject = getRetailObjectFromURLResponse(id);
        returnObject.setId(id);

        /* Get product price & currency type -- connect to noSQL(mongoDB) */
        CurrentPrice currentPrice = this.getPriceDetails(id);
        if(currentPrice == null || currentPrice.getCurrencyCode() == null || currentPrice.getValue() == null){
            LOGGER.info("Fail... Exit RetailService.getProductDetails");
            throw new MongoException("No price detail found in DB for id: " + id);
        }
        returnObject.setCurrentPrice(currentPrice);

        /* Return RetailVO Object */
        LOGGER.info("Exit RetailService.getProductDetails");
        return returnObject;
    }

    /*  Service Method to get Product Description from Rest call  */
    private RetailVO getRetailObjectFromURLResponse(String id) throws Exception {
        LOGGER.info("Enter RetailService.getRetailObjectFromURLResponse");

        ObjectMapper ObjMapper = new ObjectMapper();
        RetailVO retailVO = new RetailVO();
        JsonNode root1;
        JsonNode root2;

        /* Setup url and perform rest call on URL to target repository */
        String url = URL_FRONT + id + URL_END;
        ResponseEntity<String> responseFromURL = restTemplate.getForEntity(url,String.class);

        try{
            /* Convert restResponse to String & parse through string to find product description */
            String productDetailString = responseFromURL.getBody();
            if(productDetailString != null && !productDetailString.isEmpty()){
                root1 = ObjMapper.readTree(productDetailString);
                root2 = root1.findValue("product");
                if(root2 != null){
                    root1 = root2;
                    root2 = root1.findValue("item");
                    if(root2 != null){
                        root1 = root2;
                        root2 = root1.findValue("product_description");
                        if(root2 != null){
                            /* Replace any special character add-ons that may get transfered from the response */
                            String productDescription= root2.get("title").asText();
                            productDescription.replace("[\"]","");
                            retailVO.setName(productDescription);
                        }
                    }
                }
            }
        }catch(IOException ex){
            /* Fail if any IOException is thrown from parsing through rest response */
            LOGGER.info("Exit RetailService.getRetailObjectFromURLResponse. Object parsing failed due to IOException: " + ex.getMessage());
            throw ex;
        }

        LOGGER.info("Exit RetailService.getRetailObjectFromURLResponse");
        return retailVO;
    }

    /*  Service Method to update Product Details based on ID provided  */
    public Boolean updateProductDetails(RetailVO retailVO){
        LOGGER.info("Enter RetailService.updateProductDetails");

        /* Update currentPrice object to define id to be equal to the id in the retailVO object */
        CurrentPrice newPrice = retailVO.getCurrentPrice();
        newPrice.setId(retailVO.getId());

        /* Call Mongo Database locally defined at myretailDB */
        updatePriceDetails(retailVO.getId(), newPrice);

        LOGGER.info("Exit RetailService.updateProductDetails");
        return true;
    }

    /* -----Helper Methods -----*/

    /*  Call to local Mongo DB to retrieve Price details */
    private CurrentPrice getPriceDetails(String id) throws MongoException{
        return retailDao.findById(id);
    }

    /* Call to local Mongo DB to update and Id's Price details if the Id is existing in DB source */
    private void updatePriceDetails(String id, CurrentPrice newPrice) throws MongoException{
        CurrentPrice currentPrice = retailDao.findById(id);
        if(currentPrice != null){
            retailDao.save(newPrice);
        }else{
            throw new MongoException("Exit RetailService.UpdatePriceDetails. Price details for product with id="+id+" not found in DB collection");
        }
    }

}
