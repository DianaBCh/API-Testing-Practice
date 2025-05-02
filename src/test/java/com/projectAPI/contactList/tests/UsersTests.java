package com.projectAPI.contactList.tests;

import com.projectAPI.contactList.utils.ContactListBasePage;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class UsersTests extends ContactListBasePage {



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

        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users");
        // Assert status code
        assertEquals(201, response.statusCode());

        // Assert response body using JsonPath
        JsonPath json = response.jsonPath();
        assertEquals("Matt", json.getString("user.firstName"));
        assertEquals("Hunnam", json.getString("user.lastName"));
        assertEquals("mhunnam@practice.com", json.getString("user.email"));

    }



}
