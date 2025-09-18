package happyPath;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class CreateUserValidTest {
    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
    private static Response response;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    private Response getLoginUser(String username, String password) {
        return RestAssured
                .given()
                .basePath("/user/login")
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get()
                .then()
                .extract()
                .response();
    }

    private Response postCreateUserRequest(String body) {
        return RestAssured
                .given()
                .basePath("/user")
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post()
                .then()
                .extract().response();
    }

    @Test
    @DisplayName("Login to an account then post with valid user account body returns 200 ")
    void getLoginThenPostCreateValidUser_Returns200() {
        String loginUsername = "user1"; // from source code in UserData.java class
        String loginPassword = "XXXXXXXXXXX"; // from source code and is default password for those usernames
        Response loginResponse = getLoginUser(loginUsername, loginPassword);

        System.out.println("Login status code: " + loginResponse.statusCode());
        System.out.println("Login response: " + loginResponse.body().asString());
        assertThat(loginResponse.statusCode(), is(200));

        String requestBody = """
            {
              "id": 453,
              "username": "theApple",
              "firstName": "Apple",
              "lastName": "Banana",
              "email": "apple.banana@abemail.com",
              "password": "I-Am-*Appl$*",
              "phone": "021326833146",
              "userStatus": 1
            }
        """; // user status is 1-registered, 2-active, 3-closed

        response = postCreateUserRequest(requestBody);
        System.out.println("Create user status code: " + response.statusCode());
        System.out.println("Create user response: " + response.body().asString());
        assertThat(response.statusCode(), is(200));
    }
}
