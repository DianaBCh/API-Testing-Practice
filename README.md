
# API Testing Practice

This project is a portfolio demonstration designed to showcase my skills in API automation testing. The framework targets the Contact list API available at [https://thinking-tester-contact-list.herokuapp.com](https://thinking-tester-contact-list.herokuapp.com) and includes a robust set of tests for both user and contact endpoints.



## Project Overview

This project validates a public RESTful API using automated tests written in **Java** with **RestAssured** and **JUnit 5**. It simulates real user behavior, including authentication and CRUD operations on user and contact resources.


## Project Structure

```
API-Testing-Practice/
├── src/
│   └── test/
│       └── java/
│           └── com/
│               └── projectAPI/
│                   └── contactList/
│                       ├── tests/
│                       │   ├── UsersTests.java
│                       │   └── ContactsTests.java
│                       └── utils/
│                           ├── ContactListBasePage.java
│                           └── ContactListUtils.java
├── pom.xml
├── TestCases.md
└── .gitignore
```

* **tests/**

  * `UsersTests.java` — Handles the user lifecycle: creation, login, profile management, logout, and deletion.
  * `ContactsTests.java` — Handles full CRUD flow for contacts: create, retrieve, update, and delete.

* **utils/**

  * `ContactListBasePage.java` — Sets the `baseURI` and shared setup (used via inheritance).
  * `ContactListUtils.java` — Contains helper methods like `getToken()` and `createContact()` used across test classes.
 

## Test Cases

* **UsersTests**

  * *Create User:* Tests user creation (if necessary) and verifies successful creation.
  * *Login:* Validates user login and token retrieval.
  * *Get User Profile:* Fetches the profile for the logged-in user.
  * *Update User Profile:* Updates user details and verifies the changes.
  * *Logout:* Logs the user out from the system.
  * *Delete User:* Removes the user, ensuring cleanup after tests.

* **ContactsTests**

  * *Get Contact List:* Retrieves the list of contacts.
  * *Get Contact By ID:* Fetches a specific contact using the contact ID.
  * *Update Contact:* Performs a complete update of contact information.
  * *Partial Update:* Updates only selected fields of a contact.
  * *Delete Contact:* Deletes a contact and validates successful deletion.    


## End-to-End Test Flow

* Both test classes (`UsersTests` and `ContactsTests`) simulate complete end-to-end flows when executed in sequence.

> To run Contacts tests successfully:
>
> 1. Create a user and log in via `UsersTests`.
> 2. Ensure the authentication token is valid and accessible to the contact-related tests.

Tokens and contact IDs are managed using static variables and utility methods in `ContactListUtils`, ensuring shared accessibility across tests when run in sequence.

> **Note:**
> You can run the entire `UsersTests` class independently to observe the full user flow (create, login, profile, update, logout, delete).
> However, if you intend to run the `ContactsTests`, you must run **only** the first two tests from `UsersTests` — `TC01CreateNewUser` and `TC02LoginAndGetToken`. These tests create the user and generate a valid authentication token required by the contact-related tests.
> Running the full `UsersTests` beforehand will log out and delete the user, which invalidates the token and causes the contact tests to fail.


  ## Technologies Used

* **Java**: Programming language used to write the tests.
* **RestAssured**: For API testing and fluent HTTP request generation.
* **JUnit 5**: For structuring and running tests.
* **Maven**: Project build and dependency management.


## Prerequisites

* JDK installed (Java 8+ recommended)
* Maven installed and configured in your system PATH (optional, see below)
* IDE of your choice (IntelliJ IDEA, Eclipse, VS Code)


## How to Run Tests

### Using an IDE (Recommended)
1. Clone the project from GitHub:  
   `https://github.com/DianaBCh/API-Testing-Practice.git`

2. Import the project into your IDE (e.g., IntelliJ IDEA, Eclipse).

3. Run the tests by right-clicking on a test class (e.g., `UsersTests.java`, `ContactsTests.java`) and selecting **Run**.


### Using Maven CLI (Optional)

If you have Maven installed and want to run tests from the terminal:

* Run all tests:

  ```bash
  mvn test
  ```

* Run only UsersTests:

  ```bash
  mvn -Dtest=UsersTests test
  ```

## Notes

* **User Token Management:**
  The token for user requests is generated once after user creation. This token is reused across tests to avoid duplicate logins and to simulate a real-life workflow.

* **Utility Functions:**
  All common functionalities (e.g., token generation, contact creation) are encapsulated in `ContactListUtils.java` for ease of maintenance and reusability.

* **Test Cases Document:**
  Refer to `TestCases.md` for a detailed explanation of each test case, including expected inputs and outputs.


