package com.projectAPI.contactList.tests;

import com.projectAPI.contactList.utils.ContactListBasePage;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.restassured.path.json.JsonPath.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersTests extends ContactListBasePage {

    public static String token;

    /**
     * Given content type is JSON
     * And user details are provided
     * When user sends POST request to /users
     * Then response status code should be 201
     * And response body should contain created user information
     */

    @Order(1)
    @Test
    public void TC07CreateUser(){
        HashMap<String, String> user = new HashMap<>();
        user.put("firstName", "Matt");
        user.put("lastName", "Hunnam");
        user.put("email", "mhunnam@practice.com");
        user.put("password", "matt1234");

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users");
        // Assert status code
        assertEquals(201, response.statusCode());

        // Assert response body using JsonPath
        JsonPath json = response.jsonPath();
        assertEquals("Matt", json.getString("firstName"));
        assertEquals("Hunnam", json.getString("lastName"));
        assertEquals("mhunnam@practice.com", json.getString("email"));

    }

    /**
     * Given accept type is JSON
     * And login credentials are correct
     * When user sends GET request to /users/login
     * Then response status code should be 200
     * And response body should contain a valid token
     */
    @Order(2)
    @Test
    public void TC11Login(){

        Response response = RestAssured
                .given().accept(ContentType.JSON)
                .queryParam("email", "mhunnam@practice.com")
                .queryParam("password", "matt1234").
                when().get("/users/login").
                then()
                .extract().response();

        assertEquals(200, response.statusCode());

        token = response.jsonPath().getString("token");
        System.out.println("Token = " + token);


    }

}
