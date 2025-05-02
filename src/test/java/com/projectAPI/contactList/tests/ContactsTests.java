package com.projectAPI.contactList.tests;

import com.projectAPI.contactList.utils.ContactListBasePage;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactsTests extends ContactListBasePage {

    /**
     * Given content type is JSON
     * And valid contact data is provided
     * And Authorization token is set
     * When user sends POST request to /contacts
     * Then response status code should be 201
     * And response body should contain the contact information
     * "firstName": "Molly",
     *     "lastName": "Doe",
     *     "birthdate": "1972-07-13",
     *     "email": "mpllydoe@fake.com",
     *     "phone": "987336730",
     *     "street1": "11 Main St.",
     *     "street2": "Apartment Q",
     *     "city": "Longueuil",
     *     "stateProvince": "QC",
     *     "postalCode": "J9W2E2",
     *     "country": "Canada"
     */
    @Test
    public void TC01CreateContact(){

        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("firstName", "Jane");
        requestBody.put("lastName", "Run");
        requestBody.put("birthdate", "1990-09-13");
        requestBody.put("email", "janerun@practice.com");
        requestBody.put("phone", "234561234");
        requestBody.put("street1", "6 Main St.");
        requestBody.put("street2", "Apartment P");
        requestBody.put("city", "Saint-Lambert");
        requestBody.put("stateProvince", "QC");
        requestBody.put("postalCode","J4W2F2");
        requestBody.put("country", "Canada");

        System.out.println("Token: " + UsersTests.token);


        JsonPath jsonPath = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + UsersTests.token)
                .body(requestBody)
                .when()
                .post("/contacts")
                .then()
                .statusCode(201)
                .contentType("application/json")
                .extract().jsonPath();

        //assertions

        System.out.println(jsonPath.getString("id"));

        assertEquals("Jane", jsonPath.getString("firstName"));
        assertEquals("Run", jsonPath.getString("lastName"));
        assertEquals("1990-09-13", jsonPath.getString("birthdate"));
        assertEquals("janerun@practice.com", jsonPath.getString("email"));
        assertEquals("234561234", jsonPath.getString("phone"));
        assertEquals("6 Main St.", jsonPath.getString("street1"));
        assertEquals("Apartment P", jsonPath.getString("street2"));
        assertEquals("Saint-Lambert", jsonPath.getString("city"));
        assertEquals("QC", jsonPath.getString("stateProvince"));
        assertEquals("J4W2F2", jsonPath.getString("postalCode"));
        assertEquals("Canada", jsonPath.getString("country"));



    }
    /**
     * TC02Given accept type is JSON
     *     And Authorization token is set
     * When user sends GET request to /contacts
     * Then response status code should be 200
     *     And response body should contain a non-empty contact list
     */

    /**
     * tc03 Given accept type is JSON
     *     And Authorization token is set
     *     And path param id is a valid contact ID
     * When user sends GET request to /contacts/{id}
     * Then response status code should be 200
     *     And response body should contain contact details
     */

    /**
     * TC04 Given content type is JSON
     *     And updated contact data is provided
     *     And Authorization token is set
     *     And path param id is valid
     * When user sends PUT request to /contacts/{id}
     * Then response status code should be 200
     *     And response body should reflect updated contact
     */

    /**
     * TC05 Given content type is JSON
     *     And specific fields to update are provided
     *     And Authorization token is set
     *     And path param id is valid
     * When user sends PATCH request to /contacts/{id}
     * Then response status code should be 200
     *     And response body should contain updated fields
     */

    /**
     * TC07 Given Authorization token is set
     *     And path param id is a valid contact ID
     * When user sends DELETE request to /contacts/{id}
     * Then response status code should be 204
     *     And contact is removed from list
     */

}
