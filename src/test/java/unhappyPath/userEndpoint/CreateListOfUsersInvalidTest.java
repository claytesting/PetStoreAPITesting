package unhappyPath.userEndpoint;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CreateListOfUsersInvalidTest {

    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
    private static final String CREATE_WITH_LIST_PATH = "/user/createWithList";

    private static Response response;

    @Test
    @DisplayName("Post invalid JSON format list of users returns 400")
    void postInvalidJsonFormat_Returns400() {
        String invalidJson = """
        [
          {
            "id": 3142,
            "username": "pearLime",
            "firstName": "Pear"
          },
          {
            "food": "pasta",
            "firstName": "Fork"
        ]
        """;

        response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath(CREATE_WITH_LIST_PATH)
                .header("Content-Type", "application/json")
                .body(invalidJson)
                .when()
                .post()
                .then()
                .extract().response();

        assertThat(response.statusCode(), is(400));
    }
}
