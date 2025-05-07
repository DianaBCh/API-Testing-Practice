package com.projectAPI.contactList.utils;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactListBasePage {

    public static String token;

    @BeforeAll
    public static void init(){

        baseURI= "https://thinking-tester-contact-list.herokuapp.com";

        String credentials = "{"
                + "\"email\": \"mhunnam@practice.com\","
                + "\"password\": \"matt1234\""
                + "}";



        Response response = given().contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post("/users/login");

        assertEquals(200, response.statusCode());

        JsonPath jsonPath = response.jsonPath();
        token = jsonPath.getString("token");
        System.out.println("Extracted token: " + token);

    }

    /**
     * Given accept type is JSON
     * And login credentials are correct
     * When user sends POST request to /users/login
     * Then response status code should be 200
     * And response body should contain a valid token
     */



}
