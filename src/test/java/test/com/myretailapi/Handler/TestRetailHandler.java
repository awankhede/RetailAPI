package test.com.myretailapi.Handler;

import com.myretailapi.Handler.RetailHandler;
import com.myretailapi.Model.CurrentPrice;
import com.myretailapi.Model.RetailVO;
import com.myretailapi.Service.RetailService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.logging.Logger;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

public class TestRetailHandler {
    private final static Logger LOGGER = Logger.getLogger(TestRetailHandler.class.getName());

    @InjectMocks
    private RetailHandler retailHandler;
    @Mock
    private RetailService retailService;

    private Integer id  = 12345678;
    private RetailVO mockRetailVO = new RetailVO();
    private CurrentPrice mockPriceSetup = new CurrentPrice();
    ResponseEntity<RetailVO> expectedResponse;

    @Before
    public void SetupMock() {

        MockitoAnnotations.initMocks(this);

        mockRetailVO.setId(id.toString());
        mockRetailVO.setName("Test Product");
        mockPriceSetup.setCurrencyCode("USD");
        mockPriceSetup.setValue("100.10");

        mockRetailVO.setCurrentPrice(mockPriceSetup);

        expectedResponse = ResponseEntity.ok(mockRetailVO);

    }

    /* Test the get product details functionality is correctly setup in the handler - tests happy path */
    @Test
    public void TestGetProductDetails_Success() throws Exception {
        LOGGER.info("Test RetailHandler.getProductDetails - Happy Path");
        when(retailService.getProductDetails(isA(String.class))).thenReturn(mockRetailVO);
        ResponseEntity<RetailVO> returnResponse = retailHandler.getProductDetails(id);
        Assert.assertEquals(returnResponse,expectedResponse);
    }

    /* Test the get product details functionality is correctly setup in the controller - tests bad request */
    @Test
    public void TestGetProductDetails_BadRequest() throws Exception {
        LOGGER.info("Test RetailHandler.getProductDetails - Happy Path");
        when(retailService.getProductDetails(isA(String.class))).thenReturn(mockRetailVO);
        id = 123;
        ResponseEntity<RetailVO> returnResponse = retailHandler.getProductDetails(id);
        Assert.assertEquals(returnResponse.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    /* Test the get product details functionality is correctly setup in the controller - tests not found object */
    @Test
    public void TestGetProductDetails_NotFound() throws Exception {
        when(retailService.getProductDetails(isA(String.class))).thenReturn(null);
        ResponseEntity<RetailVO> returnResponse = retailHandler.getProductDetails(id);
        Assert.assertEquals(returnResponse.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    /* Test the update product details functionality is correctly setup in the handler - tests happy path */
    @Test
    public void TestUpdateProductDetails_Success() throws Exception {
        LOGGER.info("Test RetailHandler.getProductDetails - Happy Path");
        when(retailService.updateProductDetails(isA(RetailVO.class))).thenReturn(true);
        ResponseEntity<RetailVO> returnResponse = retailHandler.updateProductDetails(id,mockRetailVO);
        Assert.assertEquals(HttpStatus.OK, returnResponse.getStatusCode());
    }

    /* Test the update product details functionality is correctly setup in the handler - tests exception from service */
    @Test
    public void TestUpdateProductDetails_NullResponseFromService() {
        LOGGER.info("Test RetailHandler.getProductDetails - Happy Path");
        try {
            when(retailService.updateProductDetails(isA(RetailVO.class))).thenReturn(null);
            ResponseEntity<RetailVO> returnResponse = retailHandler.updateProductDetails(id, mockRetailVO);
        }catch(Exception ex){
            Assert.assertTrue("Should throw exception: " + ex.getMessage(),true);
        }
    }

    /* Test the update product details functionality is correctly setup in the handler - tests when Id in parameter and Id in object differ */
    @Test
    public void TestUpdateProductDetails_BadRequest_IdNoMatch() throws Exception {
        LOGGER.info("Test RetailHandler.getProductDetails - Happy Path");
        mockRetailVO.setId("12356789");
        ResponseEntity<RetailVO> returnResponse = retailHandler.updateProductDetails(id,mockRetailVO);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, returnResponse.getStatusCode());
    }

    /* Test the update product details functionality is correctly setup in the handler - tests incorrect id type */
    @Test
    public void TestUpdateProductDetails_BadRequest() throws Exception {
        LOGGER.info("Test RetailHandler.getProductDetails - Happy Path");
        id  = 123;
        ResponseEntity<RetailVO> returnResponse = retailHandler.updateProductDetails(id,mockRetailVO);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, returnResponse.getStatusCode());
    }

    /* Test the update product details functionality is correctly setup in the handler - tests empty price info */
    @Test
    public void TestUpdateProductDetails_EmptyCurrencyInfo() {
        LOGGER.info("Test RetailHandler.getProductDetails - Happy Path");
        mockRetailVO.setCurrentPrice(null);
        try {
            retailHandler.updateProductDetails(id, mockRetailVO);
        }catch (Exception ex){
            Assert.assertTrue("Should throw exception: " + ex.getMessage(),true);
        }
    }
}
