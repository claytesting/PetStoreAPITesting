package unhappyPath;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class CreateListOfUsersInvalidTest {
    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
    private static Response response;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    private Response postCreateListOfUsersRequest(String body) {
        return RestAssured
                .given()
                .basePath("/user/createWithList")
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post()
                .then()
                .extract().response();
    }

    @Test
    @DisplayName("Post invalid JSON format list of users returns 400")
    void postInvalidJsonFormat_Returns400() {
        String invalidJsonFormat = """
        [
          {
            "id": 3142,
            "username": "pearLime",
            "firstName": "Pear"
          },
          {
            "food": "pasta",
            "firstName": "Fork"
          }
        ]
    """;
        response = postCreateListOfUsersRequest(invalidJsonFormat);
        assertThat(response.statusCode(), is(400));
    }

    @Test
    @DisplayName("Post missing fields in list of users returns 400")
    void postMissingRequiredFields_Returns400() {
        String incompleteJsonFormat = """
            [
              {
                "id": 429,
                "firstName": "The",
                "lastName": "Lime",
                "email": "the.lime@limemail.com",
                "password": "Lime232*",
                "userStatus": 1
              },4
              {
                "id": 1734,
                "username": "theGrapes",
                "firstName": "The",
                "lastName": "Grapes",
                "phone": "02657845234",
                "userStatus": 1
              }
            ]
        """;
        response = postCreateListOfUsersRequest(incompleteJsonFormat);
        assertThat(response.statusCode(), is(400));
    }
}
