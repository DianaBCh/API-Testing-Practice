package com.projectAPI.contactList.utils;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
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
        response.then().statusCode(200);


        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Login failed. Status code: " + response.getStatusCode());
        }

        return response.jsonPath().getString("token");
    }


    /**
     * Given content type is JSON
     * And valid contact data is provided
     * And Authorization token is set
     * When user sends POST request to /contacts
     * Then response status code should be 201
     * And response body should contain the contact information
     *
     */
    public static String TC01createContact(String token) {
        String contactInfo = "{"
                + "\"firstName\": \"Lola\","
                + "\"lastName\": \"Benoit\","
                + "\"birthdate\": \"1998-11-02\","
                + "\"email\": \"lobenoit@practice.com\","
                + "\"phone\": \"2347612947\","
                + "\"street1\": \"23 Main St.\","
                + "\"street2\": \"Apartment X\","
                + "\"city\": \"Quebec\","
                + "\"stateProvince\": \"QC\","
                + "\"postalCode\": \"J6S3V5\","
                + "\"country\": \"Canada\""
                + "}";

        JsonPath jsonPath = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(contactInfo)
                .when()
                .post("/contacts")
                .then()
                .statusCode(201)
                .contentType("application/json")
                .extract().jsonPath();

        return jsonPath.getString("_id");


    }
}


