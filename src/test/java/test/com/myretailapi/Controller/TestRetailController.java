package test.com.myretailapi.Controller;

import com.myretailapi.Controller.RetailController;
import com.myretailapi.Handler.RetailHandler;
import com.myretailapi.Model.CurrentPrice;
import com.myretailapi.Model.RetailVO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestRetailController {
    private final static Logger LOGGER = Logger.getLogger(TestRetailController.class.getName());

    Integer testID = 12345678;
    RetailVO mockRetailVO = new RetailVO();
    RetailVO newMockRetailVO = new RetailVO();
    CurrentPrice mockPriceSetup = new CurrentPrice();
    ResponseEntity<RetailVO> returnEntity;

    @InjectMocks
    private RetailController retailController;
    @Mock
    private RetailHandler retailHandler;

    private MockMvc mockMvc;

    @Before
    public void SetupMock() {

        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(retailController).build();

        mockRetailVO.setId(testID.toString());
        mockRetailVO.setName("Test Product");
        mockPriceSetup.setCurrencyCode("USD");
        mockPriceSetup.setValue("100.10");
        mockRetailVO.setCurrentPrice(mockPriceSetup);

        newMockRetailVO.setId(testID.toString());
        newMockRetailVO.setName("Test Product");
        mockPriceSetup.setCurrencyCode("USD");
        mockPriceSetup.setValue("200.20");
        newMockRetailVO.setCurrentPrice(mockPriceSetup);

        returnEntity = ResponseEntity.ok(mockRetailVO);
    }

    /* Test the get product details functionality is correctly setup in the controller - tests happy path */
    @Test
    public void testGetProductDetails_Success() throws Exception {
        LOGGER.info("Test GetProductDetails - Success");

        when(retailHandler.getProductDetails(isA(Integer.class))).thenReturn(returnEntity);

        mockMvc.perform(get("/products/12345678"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)) ;
    }

    /* Test the get product details functionality is incorrectly called */
    @Test
    public void testGetProductDetails_BadRequest() throws Exception {
        LOGGER.info("Test UpdateProductDetails - Success");

        returnEntity = ResponseEntity.ok(newMockRetailVO);
        when(retailHandler.updateProductDetails(isA(Integer.class),isA(RetailVO.class))).thenReturn(returnEntity);

        mockMvc.perform(get("/products/aafsd"))
                .andExpect(status().isBadRequest());
    }

    /* Test the get product details functionality is correctly setup in the controller - tests happy path */
    @Test
    public void testUpdateProductDetails_Success() throws Exception {
        LOGGER.info("Test UpdateProductDetails - Success");

        returnEntity = ResponseEntity.ok(newMockRetailVO);
        when(retailHandler.updateProductDetails(isA(Integer.class),isA(RetailVO.class))).thenReturn(returnEntity);

        String jsonContent = "{\"id\": \"12345678\",\"name\": \"\\\"Test Product\\\"\",\"current_price\": {\"value\": \"200.20\",\"currency_code\": \"USD\"}}";

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/products/12345678")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /* Test the update product details functionality is incorrectly called */
    @Test
    public void testUpdateProductDetails_BadRequest() throws Exception {
        LOGGER.info("Test UpdateProductDetails - Success");

        returnEntity = ResponseEntity.ok(newMockRetailVO);
        when(retailHandler.updateProductDetails(isA(Integer.class),isA(RetailVO.class))).thenReturn(returnEntity);

        mockMvc.perform(put("/products/12345678"))
                .andExpect(status().isBadRequest());
    }
}
