package test;

import dto.User;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import static test.BaseTest.*;

public class GetUserListTest {
    @Test
    public void getUserList() {
        List<User> users = getRequest("/user", 200)
                .body().jsonPath().getList("data", User.class);
        System.out.println(users.get(0).getFirstName());
        users.forEach(user -> assertFalse(user.getId().isEmpty()));
        users.forEach(user -> assertTrue(user.getPicture().endsWith("jpg")));
        users.forEach(user -> assertTrue(user.getPicture().startsWith("https")));
        users.forEach(user -> assertFalse(user.getFirstName().isEmpty()));
        users.forEach(user -> assertFalse(user.getLastName().isEmpty()));
        assertEquals(20, users.size());

        int limit = getRequest("/user", 200)
                .body().jsonPath().getInt("limit");
        assertEquals(limit, users.size());



        /* for (User oneUser: users) {assertFalse(user.getId().isEmpty()  }

*/
 /*given().baseUri("https://dummyapi.io/data/v1")
 .header("app-id", "654382f6d1f7d9687daf1c94")
                 .when().log().all()
                .get("/user")
                .then().log().all().statusCode(200)
                .statusLine("HTTP/1.1 200 OK");*/

    }

    @Test
    public void getUserListWithoutAppId() {
        getRequestWithoutAppID("/user", 403);

        /*given().baseUri("https://dummyapi.io/data/v1")
                 .when().log().all()
                .get("/user")
                .then().log().all().statusCode(403)
                .statusLine("HTTP/1.1 403 Forbidden");*/




    }

    @Test
    public void getUserListWithSpecLimit() {
        int limitValue = 20;
        Response response = getRequest("/user?limit=" + limitValue, 200);
        int expectedLimit = response.body().jsonPath().getInt("limit");

        List<User> users = response.body().jsonPath().getList("data", User.class);
        assertEquals(expectedLimit, users.size());

}

    @ParameterizedTest
    @MethodSource("unvalidData")
    public void getUserListWithLimitOutOfBounds(int limitValue, int expectedLimit) {
        Response response = getRequest("/user?limit=" + limitValue, 200);

                assertEquals(expectedLimit, response.body().jsonPath().getInt("limit"));
    }

    static Stream<Arguments> unvalidData() {
        return Stream.of(
                Arguments.arguments(0, 5),
                Arguments.arguments(-1, 5),
                Arguments.arguments(4, 5),
                Arguments.arguments(51, 50),
                Arguments.arguments(100, 50),
                Arguments.arguments(1000, 50)

        );
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 20, 50})
    public void getUserListWithSpecLimit(int limitValue) {
        Response response = getRequest("/user?limit=" + limitValue, 200);
        int expectedLimit = response.body().jsonPath().getInt("limit");

        assertEquals(expectedLimit, response.body().jsonPath().getInt("limit"));
    }
}

