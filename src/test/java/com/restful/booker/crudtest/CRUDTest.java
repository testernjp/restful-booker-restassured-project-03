package com.restful.booker.crudtest;

import com.restful.booker.model.AuthPojo;
import com.restful.booker.model.BookingPojo;
import com.restful.booker.testbase.TestBase;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasValue;

public class CRUDTest extends TestBase {

    static String username = "admin";
    static String password = "password123";

    static String name = "James";

    static int bookingid;



    @Test
    public void getAllBookingIDs() {
        Response response = given()
                .when()
                .get("/booking");
        response.then().statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void getSingleBookingID() {
        Response response = given()
                .pathParam("id", 10)
                .when()
                .get("/booking/{id}");
        response.then().statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void getPingHealthCheck() {
        Response response = given()
                .when()
                .get("https://restful-booker.herokuapp.com/ping");
        response.then()
                .statusCode(201);
    }

    @Test
    public void Test001() {

        AuthPojo authPojo = new AuthPojo();
        authPojo.setUsername(username);
        authPojo.setPassword(password);

        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .body(authPojo)
                .post("/auth");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void Test002() {

        HashMap<String, String> checkInOutDatesData = new HashMap<String, String>();
        checkInOutDatesData.put("checkin", "2018-01-01");
        checkInOutDatesData.put("checkout", "2019-01-01");

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname("Jim");
        bookingPojo.setLastname("Brown");
        bookingPojo.setTotalprice(111);
        bookingPojo.setDepositpaid(true);
        bookingPojo.setBookingdates(checkInOutDatesData);
        bookingPojo.setAdditionalneeds("Breakfast");

        Response response = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .auth().preemptive().basic("admin", "password123")
                .when()
                .body(bookingPojo)
                .post("/booking");
        response.then().statusCode(200).extract().path("bookingid");
        response.prettyPrint();
    }

    @Test
    public void Test003() {
        String s1 = "findAll{it.name == '";
        String s2 = "'}.get(0)";

        ValidatableResponse response = given()
                .header("Content-Type", "application/json")
                .auth().preemptive().basic("admin", "password123")
//                .header("cookie", "token=351d246a6fa7b5f")
                .when()
                .get("/users")
                .then().statusCode(200);
        HashMap<String, Object> studentMap = response.extract()
                .path(s1 + name + s2);
        response.body(s1 + name + s2, hasValue(name));
        bookingid = (int) studentMap.get("bookingid");


    }

    @Test
    public void Test004() {

        HashMap<String, String> checkInOutDatesData = new HashMap<String, String>();
        checkInOutDatesData.put("checkin", "2018-01-01");
        checkInOutDatesData.put("checkout", "2019-01-01");

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname("James");
        bookingPojo.setLastname("Brown");
        bookingPojo.setTotalprice(111);
        bookingPojo.setDepositpaid(true);
        bookingPojo.setBookingdates(checkInOutDatesData);
        bookingPojo.setAdditionalneeds("Breakfast");

        Response response = given()
                .header("Content-Type", "application/json")
//                .header("cookie", "token=351d246a6fa7b5f")
                .auth().preemptive().basic("admin","password123")
                .pathParam("id", bookingid)
                .when()
                .body(bookingPojo)
                .put("/booking/{id}");
        response.then().statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void Test005() {

        HashMap<String, String> checkInOutDatesData = new HashMap<String, String>();
        checkInOutDatesData.put("checkin", "2018-01-01");
        checkInOutDatesData.put("checkout", "2019-01-01");

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname("James");
        bookingPojo.setLastname("Brown");
        bookingPojo.setBookingdates(checkInOutDatesData);

        Response response = given()
                .header("Content-Type", "application/json")
                .auth().preemptive().basic("admin", "password123")
//                .header("cookie", "token=351d246a6fa7b5f")
                .pathParam("id", bookingid)
                .when()
                .body(bookingPojo)
                .patch("/booking/{id}");
        response.then().statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void Test006() {
        Response response = given()
                .header("Content-Type", "application/json")
//                .header("cookie", "token=351d246a6fa7b5f")
                .auth().preemptive().basic("admin", "password123")
                .pathParam("id", bookingid)
                .when()
                .delete("/booking/{id}");
        response.prettyPrint();
        response.then().statusCode(200);

        given()
                .pathParam("id", bookingid)
                .when()
                .get("/booking/{id}")
                .then()
                .statusCode(404);
    }
}