package unhappyPath.userEndpoint;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojos.User;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class CreateUserInvalidTest {
    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
    private static Response response;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URI;
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
    @DisplayName("Post request for creating user with missing username returns 400")
    void postCreateUserWithMissingUsername_Returns400() {
        String requestBody = """
            {
              "id": 101,
              "firstName": "Lemon",
              "lastName": "Tart",
              "email": "lemontart@bakeryemail.com",
              "password": "Lemon!1",
              "phone": "025647806020",
              "userStatus": 1
            }
        """;
        response = postCreateUserRequest(requestBody);
        MatcherAssert.assertThat(response.statusCode(), is(400));
    }

    @Test // email is accepted as String with no checks on email regex
    @DisplayName("Post user with invalid email format returns 400")
    void postCreateUserWithInvalidEmailFormat_Returns400() {
        String requestBody = """
            {
              "id": 12,
              "username": "eggMuffin",
              "firstName": "Egg",
              "lastName": "Smith",
              "email": "eggMuffin.com",
              "password": "BreAk*Fast3",
              "phone": "02983578325",
              "userStatus": 1
            }
        """;
        response = postCreateUserRequest(requestBody);
        MatcherAssert.assertThat(response.statusCode(), is(400));
    }

    @Test  // overwrites account details instead of giving a conflict error
    @DisplayName("Post user with existing username returns 409")
    void postCreateUserWithExistingUsername_Returns409() {
        String createOriginalUser = """
            {
              "id": 7251,
              "username": "cookieDough",
              "firstName": "Cookie",
              "lastName": "Dough",
              "email": "cookie.dough@icecreamail.com",
              "password": "CookieDoughyIceCream3456835746",
              "phone": "370258930-745789",
              "userStatus": 1
            }
        """;
        response = postCreateUserRequest(createOriginalUser);
        MatcherAssert.assertThat(response.statusCode(), is(200));

        String createNewUserWithSameUsername = """
            {
              "id": 7411,
              "username": "cookieDough",
              "firstName": "Chocolate",
              "lastName": "Strawberry",
              "email": "chocystrawberry@dessertmail.com",
              "password": "hAppyDessert1*",
              "phone": "20356352-5",
              "userStatus": 1
            }
        """;
        response = postCreateUserRequest(createNewUserWithSameUsername);
        assertThat(response.statusCode(), is(409));

        // Get request for cookieDough to see whether original cookieDough details has been overwritten instead
        response = RestAssured
                .given()
                .basePath("/user/cookieDough")
                .when()
                .get()
                .then()
                .extract().response();

        User actualUser = response.as(User.class);

        assertThat(response.statusCode(), is(200));
        assertThat(actualUser.getFirstName(), is("Chocolate"));
        assertThat(actualUser.getUsername(), is("cookieDough"));
    }
}
