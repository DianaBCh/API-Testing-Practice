package com.projectAPI.contactList.tests;

import com.projectAPI.contactList.utils.ContactListBasePage;
import com.projectAPI.contactList.utils.ContactListUtils;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.List;

import static com.projectAPI.contactList.tests.UsersTests.email;
import static com.projectAPI.contactList.tests.UsersTests.password;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContactsTests extends ContactListBasePage{

    public static String token;

    static String id;



    @BeforeAll
    public static void setUp(){

        token = ContactListUtils.getToken(email, password);
        id = ContactListUtils.TC01createContact(token);

    }


    /**
     * TC02Given accept type is JSON
     * And Authorization token is set
     * When user sends GET request to /contacts
     * Then response status code should be 200
     * And response body should contain a non-empty contact list
     */
    @Order(1)
    @Test
    public void TC02GetContactList(){

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .when()
                .get("/contacts")
                .then()
                .statusCode(200)
                .extract()
                .response();


        List<Object> contactList = response.jsonPath().getList("");
        System.out.println(contactList);
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
    @Order(2)
    @Test
    public void TC03GetContactById(){

        System.out.println("Id =" + id);

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .get("/contacts/{id}");

        // Validate status code and contentType
        assertEquals(200, response.getStatusCode());
        assertEquals("application/json; charset=utf-8",response.contentType());

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
        assertEquals(id, actualId);
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
    @Order(3)
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

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(contactInfoUpdated)
                .when()
                .put("/contacts/" + id);


        //assertions
        assertEquals(200, response.getStatusCode(), "Expected status code 200");
        assertEquals("application/json; charset=utf-8", response.getContentType(), "Expected Content type Json");
        assertEquals("Mary", response.jsonPath().getString("firstName"), "First name updated");
        assertEquals("mbenoit@practice.com", response.jsonPath().getString("email"), "Email updated");

        //print updated contact
        System.out.println("Updated contact: " + response.asPrettyString());

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
    @Order(4)
    @Test
    public void TC05PartialUpdate(){

        System.out.println("Id= " + id);

        String partialUpdate = "{"
                + "\"firstName\": \"Anna\""
                + "}";

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(partialUpdate)
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
    @Order(5)
    @Test
    public void TC06DeleteContact(){

        System.out.println("Id= " + id);

        Response deleteResponse = given()
                .header("Authorization", "Bearer " + token)
                .pathParam("id", id)
                .when()
                .delete("/contacts/{id}");

        assertEquals(200, deleteResponse.getStatusCode(), "Expected status code 200");


        // send GET request to confirm deletion
        Response getResponse = given()
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .get("/contacts/{id}");

        // validate that the contact no longer exists (status 404)
        assertEquals(404, getResponse.getStatusCode(), "Expected status code 404 after deletion");

        System.out.println("Contact with ID " + id + " was successfully deleted.");
    }

}
