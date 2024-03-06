package test;

import com.github.javafaker.Faker;
import dto.CreateUserRequest;
import dto.CreateUserResponse;
import dto.UpdateUserRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static test.BaseTest.*;

public class UpdateUserTest {
    Faker faker = new Faker();

    @Test

    public void successUpdateLastName() {
        Response response = BaseTest.createUser(200);

        CreateUserResponse responseBody = response.getBody().as(CreateUserResponse.class);
        String userId = responseBody.getId();

        String newLastName = faker.name().lastName();;
        CreateUserRequest body = CreateUserRequest.builder().lastName(newLastName).build();

        Response updateUserResponse = putRequest("/user/" + userId, 200, body);


        assertEquals(newLastName, updateUserResponse.getBody().jsonPath().getString("lastName"));

    }

    @Test

    public void updateWithShortLastName() {

        Response response = BaseTest.createUser(200);


        CreateUserResponse responseBody = response.getBody().as(CreateUserResponse.class);
        String userId = responseBody.getId();

        String newLastName = "3";
        CreateUserRequest body = CreateUserRequest.builder().lastName(newLastName).build();

        Response updateUserResponse = putRequest("/user/" + userId, 400, body);

        String errorMessage = "BODY_NOT_VALID";
        assertEquals(errorMessage, updateUserResponse.getBody().jsonPath().getString("error"));

    }

    @Test

    public void updateEmail() {

        Response response = BaseTest.createUser(200);

        CreateUserResponse responseBody = response.getBody().as(CreateUserResponse.class);
        String userId = responseBody.getId();

        String newEmail = faker.internet().emailAddress();
        CreateUserRequest body = CreateUserRequest.builder().email(newEmail).build();

        Response updateUserResponse = putRequest("/user/" + userId, 200, body);


        assertNotEquals(newEmail, updateUserResponse.getBody().jsonPath().getString("email"));

    }

    @Test

    public void updateWithInvalidUserId() {



        Response updateUserResponse = putRequest("/user/33", 400, "");

        String errorMessage = "PARAMS_NOT_VALID";
        assertEquals(errorMessage, updateUserResponse.getBody().jsonPath().getString("error"));

    }

    @Test

    public void updateRegisteredDate() {

        Response response = BaseTest.createUser(200);


        CreateUserResponse responseBody = response.getBody().as(CreateUserResponse.class);
        String userId = responseBody.getId();

        String newRegisterDate = "(44444)";
        UpdateUserRequest body = UpdateUserRequest.builder().registerDate(newRegisterDate).build();

        Response updateUserResponse = putRequest("/user/" + userId, 200, body);


        assertNotEquals(newRegisterDate, updateUserResponse.getBody().jsonPath().getString("email"));

    }

    @Test

    public void updateWithPostMethod() {

        Response response = BaseTest.createUser(200);


        CreateUserResponse responseBody = response.getBody().as(CreateUserResponse.class);
        String userId = responseBody.getId();

         CreateUserRequest body = CreateUserRequest.builder().build();

        Response updateUserResponse = postRequest("/user/" + userId, 404, body);

        String errorMessage = "PATH_NOT_FOUND";
        assertEquals(errorMessage, updateUserResponse.getBody().jsonPath().getString("error"));

    }

    @Test

    public void updateUserId() {

        Response response = BaseTest.createUser(200);


        CreateUserResponse responseBody = response.getBody().as(CreateUserResponse.class);
        String userId = responseBody.getId();

        String newUserId = "45444rt";
        UpdateUserRequest body = UpdateUserRequest.builder().registerDate(newUserId).build();

        Response updateUserResponse = putRequest("/user/" + userId, 200, body);


        assertNotEquals(newUserId, updateUserResponse.getBody().jsonPath().getString("id"));

    }



}
