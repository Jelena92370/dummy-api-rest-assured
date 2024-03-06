package test;

import com.github.javafaker.Faker;
import dto.CreateUserRequest;
import dto.CreateUserResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static test.BaseTest.deleteRequest;
import static test.BaseTest.postRequest;

public class DeleteUserTest {


    @Test
    public void deleteExistingUser() {
        Response response = BaseTest.createUser(200);


        CreateUserResponse responseBody = response.getBody().as(CreateUserResponse.class);
        String userId = responseBody.getId();


        Response deleteUserResponse = deleteRequest("/user/" + userId, 200);


        assertEquals(userId, deleteUserResponse.getBody().jsonPath().getString("id"));

        //Expected result = 200 ok, return id
    }

    @Test
    public void deleteDeletedUser() {
        Response response = BaseTest.createUser(200);


        CreateUserResponse responseBody = response.getBody().as(CreateUserResponse.class);
        String userId = responseBody.getId();


        Response deleteUserResponse = deleteRequest("/user/" + userId, 200);

        assertEquals(userId, deleteUserResponse.getBody().jsonPath().getString("id"));

        Response deletedUserDeleteResponse = deleteRequest("/user/" + userId, 404);

        String errorText = deletedUserDeleteResponse.getBody().jsonPath().getString("error");

    assertEquals("RESOURCE_NOT_FOUND", errorText);
    }

    @Test
    public void deleteInvalidUser() {
        Response deleteUserResponse = deleteRequest("/user/456" , 400);
        String errorText = deleteUserResponse.getBody().jsonPath().getString("error");
        assertEquals("PARAMS_NOT_VALID", errorText);
    }
}
