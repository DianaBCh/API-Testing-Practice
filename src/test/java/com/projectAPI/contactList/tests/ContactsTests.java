package com.projectAPI.contactList.tests;

import com.projectAPI.contactList.utils.ContactListBasePage;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContactsTests extends ContactListBasePage {

    static String id;

    /**
     * Given content type is JSON
     * And valid contact data is provided
     * And Authorization token is set
     * When user sends POST request to /contacts
     * Then response status code should be 201
     * And response body should contain the contact information
     *
     */
    @Order(1)
    @Test()
    public void TC01CreateContact(){

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
                + "\"country\": \"Canada\"" +
                 "}";

        System.out.println("Token: " + UsersTests.token);


        JsonPath jsonPath = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + UsersTests.token)
                .body(contactInfo)
                .when()
                .post("/contacts")
                .then()
                .statusCode(201)
                .contentType("application/json")
                .extract().jsonPath();

        System.out.println("Full Response Body: " + jsonPath.prettify());

        String createdId = jsonPath.getString("_id");
        System.out.println("New contact ID = " + createdId);
        id = createdId;



       /*
        //assertions
        assertEquals("Cely", jsonPath.getString("firstName"));
        assertEquals("Benoit", jsonPath.getString("lastName"));
        assertEquals("1998-11-02", jsonPath.getString("birthdate"));
        assertEquals("cbenoit@practice.com", jsonPath.getString("email"));
        assertEquals("2347612947", jsonPath.getString("phone"));
        assertEquals("23 Main St.", jsonPath.getString("street1"));
        assertEquals("Apartment X", jsonPath.getString("street2"));
        assertEquals("Quebec", jsonPath.getString("city"));
        assertEquals("QC", jsonPath.getString("stateProvince"));
        assertEquals("J6S3V5", jsonPath.getString("postalCode"));
        assertEquals("Canada", jsonPath.getString("country"));

        */

    }


    /**
     * TC02Given accept type is JSON
     * And Authorization token is set
     * When user sends GET request to /contacts
     * Then response status code should be 200
     * And response body should contain a non-empty contact list
     */
    @Order(2)
    @Test
    public void TC02GetContactList(){

        Response response = given()
                .header("Authorization", "Bearer " + UsersTests.token)
                .accept(ContentType.JSON)
                .when()
                .get("/contacts")
                .then()
                .statusCode(200)
                .extract()
                .response();


        List<Object> contactList = response.jsonPath().getList("");
        assertFalse(contactList.isEmpty(), "Contact list should not be empty");


    }


    /**
     * tc03 Given accept type is JSON
     * And Authorization token is set
     * And path param id is a valid contact ID
     * When user sends GET request to /contacts/{id}
     * Then response status code should be 200
     * And response body should contain contact details
     */
    @Order(3)
    @Test
    public void TC03GetContactById(){

        System.out.println("Id =" + id);

        Response response = given()
                .header("Authorization", "Bearer " + UsersTests.token)
                .accept(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .get("/contacts/{id}");

        // Validate status code and contentType
        assertEquals(200, response.getStatusCode());
        assertEquals("application/json; charset=utf-8",response.contentType());
        //System.out.println("Response Body: " + response.getBody().asString());// Print the response body for debugging

        // Retrieve data by using JsonPath
        JsonPath jsonPath = response.jsonPath();

        String actualId = jsonPath.getString("_id");
        String firstName = jsonPath.getString("firstName");
        String lastName = jsonPath.getString("lastName");
        String email = jsonPath.getString("email");

        //print data
        System.out.println("actualId = " + actualId);
        System.out.println("firstName = " + firstName);
        System.out.println("lastName = " + lastName);
        System.out.println("email = " + email);

        //assert data
        assertEquals(ContactsTests.id, actualId);
        assertEquals("Lola", firstName);
        assertEquals("Benoit", lastName);
        assertEquals("lobenoit@practice.com", email);

    }

    /**
     * TC04 Given content type is JSON
     *     And updated contact data is provided
     *     And Authorization token is set
     *     And path param id is valid
     * When user sends PUT request to /contacts/{id}
     * Then response status code should be 200
     *     And response body should reflect updated contact
     */
    @Order(4)
    @Test
    public void TC04UpdateContact(){

        System.out.println("Id= " + id);

        String contactInfoUpdated = "{"
                + "\"firstName\": \"Mary\","
                + "\"lastName\": \"Benoit\","
                + "\"birthdate\": \"1998-11-02\","
                + "\"email\": \"mbenoit@practice.com\","
                + "\"phone\": \"2347612947\","
                + "\"street1\": \"23 Main St.\","
                + "\"street2\": \"Apartment X\","
                + "\"city\": \"Quebec\","
                + "\"stateProvince\": \"QC\","
                + "\"postalCode\": \"J6S3V5\","
                + "\"country\": \"Canada\"" +
                "}";
        /*
        "_id": "681a97d4e961e400152f5312",
    "firstName": "Lola",
    "lastName": "Benoit",
    "birthdate": "1998-11-02",
    "email": "lobenoit@practice.com",
    "phone": "2347612947",
    "street1": "23 Main St.",
    "street2": "Apartment X",
    "city": "Quebec",
    "stateProvince": "QC",
    "postalCode": "J6S3V5",
    "country": "Canada",
    "owner": "6813fe9fabed1400150a7699",
    "__v": 0
         */

        Response response = given()
                .header("Authorization", "Bearer " + UsersTests.token)
                .contentType(ContentType.JSON)
                .body(contactInfoUpdated)
                //.log().all()
                .when()
                .put("/contacts/" + id);


        //assertions
        assertEquals(200, response.getStatusCode(), "Expected status code 200");
        assertEquals("application/json; charset=utf-8", response.getContentType(), "Expected Content type Json");
        assertEquals("Mary", response.jsonPath().getString("firstName"), "First name updated");
        assertEquals("mbenoit@practice.com", response.jsonPath().getString("email"), "Email updated");

        //print updated contact
        //System.out.println("Updated contact: " + response.asPrettyString());



    }

    /**
     * TC05 Given content type is JSON
     *     And specific fields to update are provided
     *     And Authorization token is set
     *     And path param id is valid
     * When user sends PATCH request to /contacts/{id}
     * Then response status code should be 200
     *     And response body should contain updated fields
     */
    @Order(5)
    @Test
    public void TC05PartialUpdate(){

        System.out.println("Id= " + id);

        String partialUpdate = "{"
                + "\"firstName\": \"Anna\""
                + "}";

        Response response = given()
                .header("Authorization", "Bearer " + UsersTests.token)
                .accept(ContentType.JSON)
                .body(partialUpdate)
                //.log().all()
                .when()
                .patch("/contacts/" + id);


        assertEquals(200, response.statusCode());
        assertEquals("application/json; charset=utf-8", response.contentType());
        assertEquals("Anna", response.jsonPath().getString("firstName"), "Last name updated");


    }

    /**
     * TC06 Given Authorization token is set
     *     And path param id is a valid contact ID
     * When user sends DELETE request to /contacts/{id}
     * Then response status code should be 204
     *     And contact is removed from list
     */
    @Order(6)
    @Test
    public void TC06DeleteContact(){

        System.out.println("Id= " + id);

        Response deleteResponse = given()
                .header("Authorization", "Bearer " + UsersTests.token)
                .pathParam("id", id)
                .when()
                .delete("/contacts/{id}");

        assertEquals(200, deleteResponse.getStatusCode(), "Expected status code 200");


        // send GET request to confirm deletion
        Response getResponse = given()
                .header("Authorization", "Bearer " + UsersTests.token)
                .accept(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .get("/contacts/{id}");

        // validate that the contact no longer exists (status 404)
        assertEquals(404, getResponse.getStatusCode(), "Expected status code 404 after deletion");

        System.out.println("Contact with ID " + id + " was successfully deleted.");
    }

}
