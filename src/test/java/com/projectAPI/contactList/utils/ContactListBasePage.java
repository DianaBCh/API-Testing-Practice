package com.projectAPI.contactList.utils;

import org.junit.jupiter.api.BeforeAll;
import static io.restassured.RestAssured.baseURI;

public class ContactListBasePage {

    @BeforeAll
    public static void init(){

        baseURI= "https://thinking-tester-contact-list.herokuapp.com";
    }
}
