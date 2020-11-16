package test.com.myretailapi;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.com.myretailapi.Controller.TestRetailController;
import test.com.myretailapi.Handler.TestRetailHandler;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestRetailController.class,
        TestRetailHandler.class
})
public class TestAll { }
