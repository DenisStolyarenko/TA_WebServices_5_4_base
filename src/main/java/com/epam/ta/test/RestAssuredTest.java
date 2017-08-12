package com.epam.ta.test;

import com.epam.ta.application.business_object.User;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RestAssuredTest {
    @BeforeTest
    public void initTest(){
        RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
    }

    @Test
    public void checkHttpStatus(){
        Response response = given().get("/users").andReturn();
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, 200, "Response returned NOT 200 status");
    }

    @Test
    public void checkHttpResponseHeader(){
        Response response = given().get("/users").andReturn();
        String valueOfContentTypeHeader = response.getHeader("content-type");
        Assert.assertTrue(valueOfContentTypeHeader.contains("application/json; charset=utf-8"));
    }

    @Test
    public void checkResponseBody(){
        Response response = given().get("/users").andReturn();
        User[] users = response.as(User[].class);
        Assert.assertEquals(users.length, 10, "Count of users is NOT equal 10");
    }
}
