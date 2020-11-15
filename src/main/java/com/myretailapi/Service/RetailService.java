package com.myretailapi.Service;

import com.myretailapi.Model.RetailVO;
import org.springframework.stereotype.Service;

@Service
public class RetailService {
    public RetailVO getProductDetails(Integer id){
        return new RetailVO();
    }

    public RetailVO updateProductDetails(RetailVO retailVO){
        return retailVO;
    }
}
