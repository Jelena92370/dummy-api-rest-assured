package test;

import dto.User;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static test.BaseTest.*;

public class GetUserById {
String requestId = "60d0fe4f5311236168a109db";
    @Test
    public void getUserById() {
       User user = getRequest("/user/" + requestId, 200)
                .body().jsonPath().getObject("", User.class);

       assertFalse(user.getId().isEmpty());
        assertFalse(user.getFirstName().isEmpty());
        assertFalse(user.getLastName().isEmpty());
        assertFalse(user.getEmail().isEmpty());


        if (user != null) {
            Field[] fields = User.class.getDeclaredFields();

            for (Field field : fields) {
                try {

                    Object value = field.get(user);
                    assertNotNull(value, "Field " + field.getName() + " is null");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    String invalidId = "T543";
    @Test public void getUserByInvalidId() {

                Response response = getRequest("/user/" + invalidId, 400);
        String error = response.body().jsonPath().getString("error");

        assertEquals("PARAMS_NOT_VALID", error);

    }

    @Test
    public void getUserByInvalidMethod() {

        Response response = postRequestWithoutBody("/user/" + requestId, 404);

        String error = response.getBody().jsonPath().getString("error");
        assertEquals("PATH_NOT_FOUND", error);

    }
}
