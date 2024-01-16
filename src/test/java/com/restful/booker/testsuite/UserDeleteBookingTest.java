package com.restful.booker.testsuite;

import com.restful.booker.testbase.TestBase;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UserDeleteBookingTest extends TestBase {

    @Test
    public void VerifyBookingDeleteSuccessfully(){
        Response response = given()
                .header("Content-Type", "application/json")
//                .header("cookie", "token=351d246a6fa7b5f")
                .auth().preemptive().basic("admin","password123")
                .pathParam("id", 3366)
                .when()
                .delete("/booking/{id}");
        response.prettyPrint();
        response.then().statusCode(201);

        given()
                .pathParam("id",3366)
                .when()
                .get("/booking/{id}")
                .then()
                .statusCode(404);
    }
}
