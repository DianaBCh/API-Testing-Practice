package com.projectAPI.contactList.tests;

import com.projectAPI.contactList.utils.ContactListBasePage;
import com.projectAPI.contactList.utils.ContactListUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersTests extends ContactListBasePage{

    public static String email = "canel@practice.com";
    public static String password = "lora1234";


    /**
     * Given content type is JSON
     * And user details are provided
     * When user sends POST request to /users
     * Then response status code should be 201
     * And response body should contain created user information
     */
    @Order(1)
    @Test
    public void TC01CreateNewUser(){

        String newUser = "{"
                + "\"firstName\": \"Lora\","
                + "\"lastName\": \"Cane\","
                + "\"email\": \"" + email + "\","
                + "\"password\": \"" + password + "\""
                + "}";

        Response response =
        given()
                .contentType(ContentType.JSON)
                .body(newUser)
                .when()
                .post("/users");

        assertEquals(201, response.statusCode(), "User should be created successfully");
        System.out.println("User registration response: " + response.asString());

        // 2. Login to get token
        //token = ContactListUtils.getToken(email, password);


    }

    @Test
    @Order(2)
    public void TC02LoginAndGetToken() {

        String token = ContactListUtils.getToken(email, password);
        assertNotNull(token);
        System.out.println("TOKEN: " + token);
        System.out.println("email = " + email);

    }


    /** GET GetUserProfile
     * Given I am an authenticated user with a valid token
     * When I send a GET request to /users/me
     * Then the response status should be 200 (OK)
     * And the response body should contain my user profile information
     */
    @Order(3)
    @Test
    public void TC03GetUserProfile(){
        String token = ContactListUtils.getToken(email, password);
        System.out.println("Token: " + token);

        Response response =
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/users/me");


        assertEquals(200, response.statusCode(), "Status code should be 200");

        System.out.println("User Profile: " + response.asPrettyString());


    }


    /**
     * PATCH UpdateUser
     * Given I am an authenticated user and I have a valid user ID
     * When I send a PATCH request to /users/{id} with updated user details
     * Then the response status should be 200 (OK)
     * And the response body should reflect the updated user information
     */
    @Order(4)
    @Test
    public void TC04UpdateUserProfile() {
        String token = ContactListUtils.getToken(email, password);

        Response getResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/users/me");

        assertEquals(200, getResponse.statusCode(), "Should successfully fetch user profile");

        String userId = getResponse.jsonPath().getString("_id");
        assertNotNull(userId, "User ID should not be null");

        // updated details
        String updatedInfo = "{ \"firstName\": \"Cely\", \"lastName\": \"Stones\" }";


        Response patchResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(updatedInfo)
                .when()
                .patch("/users/me");

        assertEquals(200, patchResponse.statusCode(), "Update should return 200");

        // Validate response body
        String updatedFirstName = patchResponse.jsonPath().getString("firstName");
        String updatedLastName = patchResponse.jsonPath().getString("lastName");

        assertEquals("Cely", updatedFirstName);
        assertEquals("Stones", updatedLastName);

        System.out.println("Updated User: " + patchResponse.asPrettyString());
    }


    /**
     * POST LogoutRequest
     * Given I am an authenticated user with a valid token
     * When I send a POST request to /users/logout
     * Then the response status should be 200 (OK)
     * And the response body should confirm a successful logout
     */
    @Order(5)
    @Test
    public void TC05LogoutRequest(){
        String token = ContactListUtils.getToken(email, password);

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("users/logout");

        assertEquals(200, response.getStatusCode());

    }



    /**
     * DELETE DeleteUser
     * Given I am an authenticated user and I have a valid user ID
     * When I send a DELETE request to /users/{id}
     * Then the response status should be 204 (No Content)
     * And the user should be deleted with no content returned in the response
     */
    @Order(6)
    @Test
    public void TC06DeleteUser(){
        String token = ContactListUtils.getToken(email, password);

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("users/me");

        assertEquals(200, response.getStatusCode());
        System.out.println("User with email:" + email + "--> was successfully deleted.");


    }





}
