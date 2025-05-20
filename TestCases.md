Test Cases for User API:

1. POST CreateNewUser
   Description:  
   This test case verifies that a new user can be created successfully using the POST /users endpoint.

Steps:
1. Send a POST request to /users with the user details.
2. Assert that the response status code is 201 (Created).
3. Verify that the response body contains the user information.
   Expected Outcome:
- A new user is created and returned in the response body with status code 201.

2. POST LoginRequest
   Description:
   This test case verifies that the POST /login endpoint logs a user in successfully.

Steps:
1. Send a POST request to /login with valid credentials (username and password).
2. Assert that the response status code is 200 (OK).
3. Verify that the response contains a valid authentication token.
   Expected Outcome:
- The user is successfully logged in, and the response contains an authentication token.



3. GET GetUserProfile
   Description:  
   This test case verifies that the GET /users/profile endpoint returns the correct user profile.

Steps:
1. Send a GET request to /users/profile with a valid user token.
2. Assert that the response status code is 200 (OK).
3. Verify that the response body contains the correct user profile information.
   Expected Outcome:
- The response contains the correct user profile data.


4. PATCH UpdateUser
   Description:  
   This test case verifies that the PATCH /users/{id} endpoint updates the user's information.

Steps:
1. Send a PATCH request to /users/{id} with the updated user details.
2. Assert that the response status code is 200 (OK).
3. Verify that the updated user data is returned in the response.
   Expected Outcome:
- The user's details are updated successfully and reflected in the response.


5. POST LogoutRequest
   Description:  
   This test case verifies that the POST /logout endpoint logs the user out successfully.

Steps:
1. Send a POST request to /logout with a valid user token.
2. Assert that the response status code is 200 (OK).
3. Verify that the user is logged out and the response contains confirmation.
   Expected Outcome:
- The user is logged out, and the response indicates a successful logout.



6. DELETE DeleteUser
   Description:  
   This test case verifies that the DELETE /users/{id} endpoint deletes a user.

Steps:
1. Send a DELETE request to /users/{id} with a valid user ID.
2. Assert that the response status code is 204 (No Content).
3. Verify that the user is deleted and no further information is returned.
   Expected Outcome:
- The user is deleted successfully, and no content is returned.


Test Cases for Contact List API:

1. POST CreateContact
Description:  
This test case verifies that a new contact can be created successfully using the POST /contacts endpoint.

Steps:
  1. Send a POST request to /contacts with the contact details.
  2. Assert that the response status code is 201 (Created).
  3. Verify that the response body contains the contact information that was sent.
Expected Outcome:
- A new contact is created and returned in the response body with status code 201.



2. GET GetContactList
Description:  
This test case verifies that the GET /contacts endpoint returns the list of all contacts.

Steps:
  1. Send a GET request to /contacts.
  2. Assert that the response status code is 200 (OK).
  3. Verify that the response body contains a list of contacts (non-empty array).
Expected Outcome:
- The response contains a list of contacts.


3. GET GetContactByID
Description:  
This test case verifies that the GET /contacts/{id} endpoint returns the correct contact when given a valid contact ID.

Steps:
  1. Send a GET request to /contacts/{id} with a valid contact ID.
  2. Assert that the response status code is 200 (OK).
  3. Verify that the response body contains the correct contact data.
Expected Outcome:
- The response contains the contact details of the contact with the given ID.


4. PUT UpdateContact
Description:  
This test case verifies that the PUT /contacts/{id} endpoint correctly updates a contact's details.

Steps:
  1. Send a PUT request to /contacts/{id} with the updated contact data.
  2. Assert that the response status code is 200 (OK).
  3. Verify that the updated contact data is returned in the response.
Expected Outcome:
- The contact details are updated successfully, and the updated data is returned.


5. PATCH UpdateSpecificElement
Description:  
This test case verifies that the PATCH /contacts/{id} endpoint can update specific contact fields.

Steps:
  1. Send a PATCH request to /contacts/{id} with the field(s) to update.
  2. Assert that the response status code is 200 (OK).
  3. Verify that only the specific field(s) are updated in the response.
Expected Outcome:
- The specific field(s) in the contact are updated, and the response contains the updated information.


6. DELETE DeleteContact
Description:  
This test case verifies that the DELETE /contacts/{id} endpoint correctly deletes a contact.

Steps:
  1. Send a DELETE request to /contacts/{id} with a valid contact ID.
  2. Assert that the response status code is 204 (No Content).
  3. Verify that the contact is no longer in the contact list.
Expected Outcome:
- The contact is deleted successfully, and no further information is returned in the response.




