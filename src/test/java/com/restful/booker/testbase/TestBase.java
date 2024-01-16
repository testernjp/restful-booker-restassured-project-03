package com.restful.booker.testbase;

import com.restful.booker.utils.PropertyReader;
import com.restful.booker.utils.TestUtils;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class TestBase extends TestUtils {
    @BeforeClass
    public void inIt() {
        RestAssured.baseURI = PropertyReader.getInstance().getProperty("baseURI");
    }
}
