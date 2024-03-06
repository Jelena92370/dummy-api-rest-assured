package test;

import com.github.javafaker.Faker;
import dto.CreateUserRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.*;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RedirectSpecification;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Collection;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseTest {
    final static String BASE_URI = "https://dummyapi.io/data/v1";

    final static String APP_ID_VALUE = "654382f6d1f7d9687daf1c94";

    static RequestSpecification specification = new RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .addHeader("app-id", APP_ID_VALUE)
            .setContentType(ContentType.JSON)
            .build();

    static RequestSpecification specWithoutAppID = new RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .setContentType(ContentType.JSON)
            .build();


    public static Response getRequest(String endPoint, Integer expectedStatusCode) {
        Response response = given()
                .spec(specification)
                .when().log().all()
                .get(endPoint)
                .then().log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    public static Response postRequestWithoutBody(String endPoint, Integer expectedStatusCode) {
        Response response = given()
                .spec(specification)
                .when().log().all()
                .post(endPoint)
                .then().log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    public static Response postRequest(String endPoint, Integer expectedStatusCode, Object body) {
        Response response = given()
                .spec(specification)
                .body(body)
                .when().log().all()
                .post(endPoint)
                .then().log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    public static Response deleteRequest(String endPoint, Integer expectedStatusCode) {
        Response response = given()
                .spec(specification)
                .when().log().all()
                .delete(endPoint)
                .then().log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }
    public static Response putRequest(String endPoint, Integer expectedStatusCode, Object body) {
        Response response = given()
                .spec(specification)
                .body(body)
                .when().log().all()
                .put(endPoint)
                .then().log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }


    public static Response getRequestWithoutAppID(String endPoint, Integer expectedStatusCode){
        Response response = given()
                .spec(specWithoutAppID)
                .when()
                .log().all()
                .get(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    public static Response createUser(Integer expectedStatusCode) {
        Faker faker = new Faker();
        String userEmail = faker.internet().emailAddress();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();

        CreateUserRequest reqBodyBuilder = CreateUserRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(userEmail)
                .build();

        Response response = postRequest("/user/create", expectedStatusCode, reqBodyBuilder);
        return response;
    }
    }
