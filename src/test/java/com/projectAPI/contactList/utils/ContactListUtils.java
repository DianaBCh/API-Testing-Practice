package com.projectAPI.contactList.utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ContactListUtils {

    /**TC05LoginRequest
     * Given accept type is JSON
     * And login credentials are correct
     * When user sends POST request to /users/login
     * Then response status code should be 200
     * And response body should contain a valid token
     */
    public static String getToken(String email, String password){

        String credentials = "{"
                + "\"email\": \"" + email + "\","
                + "\"password\": \"" + password + "\""
                + "}";

        Response response = given()
                .baseUri("https://thinking-tester-contact-list.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post("/users/login");

        System.out.println("LOGIN STATUS CODE: " + response.statusCode());
        System.out.println("LOGIN RESPONSE BODY: " + response.getBody().asString());


        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Login failed. Status code: " + response.getStatusCode());
        }

        return response.jsonPath().getString("token");
    }

}
