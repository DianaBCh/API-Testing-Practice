package com.projectAPI.contactList.tests;

import com.projectAPI.contactList.utils.ContactListBasePage;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersTests{

    public static String token;

    @BeforeAll
    public static void init(){

        baseURI= "https://thinking-tester-contact-list.herokuapp.com";

    }

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
        /*
        HashMap<String, String> user = new HashMap<>();
        user.put("firstName", "Sony");
        user.put("lastName", "Hunnam");
        user.put("email", "shunnam@practice.com");
        user.put("password", "sony1234");

         */

        String newUser = "{"
                + "\"firstName\": \"Lora\","
                + "\"lastName\": \"Cane\","
                + "\"email\": \"loracane@practice.com\","
                + "\"password\": \"lora1234\""
                + "}";

        Response response =
        given()
                .contentType(ContentType.JSON)
                .body(newUser)
                .when()
                .post("/users");
                //.then().log().all()
                //.statusCode(201);

        assertEquals(201, response.statusCode(), "User should be created successfully");
        System.out.println("User registration response: " + response.asString());

    }

    /**TC05LoginRequest
     * Given accept type is JSON
     * And login credentials are correct
     * When user sends POST request to /users/login
     * Then response status code should be 200
     * And response body should contain a valid token
     */
    @Test
    public void TC07LoginRequest(){

        String credentials = "{"
                + "\"email\": \"loracane@practice.com\","
                + "\"password\": \"lora1234\""
                + "}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post("/users/login");

        assertEquals(200, response.statusCode(), "Login should be successful");

        token = response.jsonPath().getString("token");
        assertNotNull(token, "Token should not be null");
        System.out.println("Token: " + token);



    }


    /** GET GetUserProfile
     * Given I am an authenticated user with a valid token
     * When I send a GET request to /users/me
     * Then the response status should be 200 (OK)
     * And the response body should contain my user profile information
     */
    @Order(2)
    @Test
    public void TC02GetUserProfile(){

        System.out.println("Token: " + token);

        System.out.println("Using token: " + token);

        Response response = given()
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
    @Order(3)
    @Test
    public void updateUserProfile() {

        Response getResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/users/me");

        assertEquals(200, getResponse.statusCode(), "Should successfully fetch user profile");

        String userId = getResponse.jsonPath().getString("_id");
        assertNotNull(userId, "User ID should not be null");

        // Prepare updated details
        String updatedInfo = "{ \"firstName\": \"Cely\", \"lastName\": \"Stones\" }";

        // Then: Send PATCH request
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
    @Order(4)
    @Test
    public void TC04LogoutRequest(){

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
    @Order(5)
    @Test
    public void TC05DeleteUser(){

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("users/me");

        assertEquals(200, response.getStatusCode());


    }





}
