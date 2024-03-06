package test;

import com.github.javafaker.Faker;
import dto.CreateUserRequest;

import dto.CreateUserResponse;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static test.BaseTest.postRequest;

public class CreateUser {
    Faker faker = new Faker();
    @Test
    public void successCreateUserWithRequiredFields() {

        Response response = BaseTest.createUser(200);

        CreateUserResponse responseBody = response.getBody().as(CreateUserResponse.class);

        assertFalse(responseBody.getId().isEmpty());
        assertFalse(responseBody.getFirstName().isEmpty());
        assertFalse(responseBody.getLastName().isEmpty());
        assertFalse(responseBody.getEmail().isEmpty());
        assertFalse(responseBody.getRegisterDate().isEmpty());
        assertFalse(responseBody.getUpdatedDate().isEmpty());

        assertEquals(responseBody.getEmail(), responseBody.getEmail());
        assertEquals(responseBody.getRegisterDate(), responseBody.getUpdatedDate());

        File schemaFile = new File("src/test/java/schemas/userFull.json");
        response.then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
    }

    @Test
    public void successCreateUserWithAdditionalFields() {


        String userEmail = faker.internet().emailAddress();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String gender = "male";
        String title = "mr";
        String phone = "67584954633";
        CreateUserRequest requestBody = CreateUserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(userEmail)
                .gender(gender)
                .title(title)
                .phone(phone)
                .build();
            Response response = postRequest("/user/create", 200,
               requestBody

        );

        CreateUserResponse responseBody = response.getBody().as(CreateUserResponse.class);

        assertFalse(responseBody.getId().isEmpty());
        assertFalse(responseBody.getFirstName().isEmpty());
        assertFalse(responseBody.getLastName().isEmpty());
        assertFalse(responseBody.getEmail().isEmpty());
        assertFalse(responseBody.getGender().isEmpty());
        assertFalse(responseBody.getTitle().isEmpty());
        assertFalse(responseBody.getRegisterDate().isEmpty());
        assertFalse(responseBody.getPhone().isEmpty());
        assertFalse(responseBody.getUpdatedDate().isEmpty());


    }
    @Test
    public void createUserWithoutEmail() {

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();

        CreateUserRequest requestBody = CreateUserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();

        Response response = postRequest("/user/create", 400,
                requestBody);


        assertTrue(response.getBody().asString().contains("Path `email` is required."));
    }

    @Test
    public void createUserWithoutFirstName() {

        String userEmail = faker.internet().emailAddress();
        String lastName = faker.name().lastName();

        CreateUserRequest requestBody = CreateUserRequest.builder()
                 .lastName(lastName)
                .email(userEmail)
                .build();

        Response response = postRequest("/user/create", 400,
                requestBody);


        assertTrue(response.getBody().asString().contains("Path `firstName` is required."));
    }

    @Test
    public void createUserWithoutLastName() {

        String userEmail = faker.internet().emailAddress();
        String firstName = faker.name().firstName();

        CreateUserRequest requestBody = CreateUserRequest.builder()
                .firstName(firstName)
                .email(userEmail)
                .build();

        Response response = postRequest("/user/create", 400,
                requestBody);


        assertTrue(response.getBody().asString().contains("Path `lastName` is required."));
    }

    @Test
    public void createUserWithoutRequiredFields() {


        CreateUserRequest requestBody = CreateUserRequest.builder()
                                .build();

        Response response = postRequest("/user/create", 400,
                requestBody);


        assertTrue(response.getBody().asString().contains("Path `lastName` is required.")
                && response.getBody().asString().contains("Path `firstName` is required.")
                && response.getBody().asString().contains("Path `email` is required."));
    }
}

