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

    public RetailVO getProductDetails(String id) throws Exception{
        LOGGER.info("Enter RetailService.getProductDetails");

        /* Get product name -- rest call*/
        RetailVO returnObject = getRetailObjectFromURLResponse(id);
        returnObject.setId(id);

        /* Get product price & currency type -- connect to noSQL(mongoDB) */
        CurrentPrice currentPrice = this.getPriceDetails(id);
        if(currentPrice == null || currentPrice.getCurrencyCode() == null || currentPrice.getValue() == null){
            throw new MongoException("No price detail found in DB for id: " + id);
        }
        returnObject.setCurrentPrice(currentPrice);

        return returnObject;
    }

    private RetailVO getRetailObjectFromURLResponse(String id) throws Exception {
        ObjectMapper ObjMapper = new ObjectMapper();
        RetailVO retailVO = new RetailVO();
        CurrentPrice currentPrice = new CurrentPrice();
        JsonNode root1;
        JsonNode root2;

        String url = URL_FRONT + id + URL_END;
        ResponseEntity<String> responseFromURL = restTemplate.getForEntity(url,String.class);

        try{
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
                            retailVO.setName(root2.findValue("title").toString());
                        }
                    }
                }
            }
        }catch(IOException ex){
            LOGGER.info("Object parsing failed due to IOException: " + ex.getMessage());
            throw ex;
        }
        retailVO.setCurrentPrice(currentPrice);
        return retailVO;
    }

    public Boolean updateProductDetails(RetailVO retailVO){
        LOGGER.info("Enter RetailService.updateProductDetails");

        CurrentPrice newPrice = retailVO.getCurrentPrice();
        newPrice.setId(retailVO.getId());
        updatePriceDetails(retailVO.getId(), newPrice);
        return true;
    }

    /* -----Helper Methods -----*/

    private CurrentPrice getPriceDetails(String id) throws MongoException{
        return retailDao.findById(id);
    }

    private void updatePriceDetails(String id, CurrentPrice newPrice) throws MongoException{
        CurrentPrice currentPrice = retailDao.findById(id);
        if(currentPrice != null){
          //  retailDao.delete(currentPrice);
            retailDao.save(newPrice);
        }else{
            throw new MongoException("price details for product with id="+id+" not found in mongo db for collection 'productprice'");
        }
    }

}
