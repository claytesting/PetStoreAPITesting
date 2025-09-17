import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class GetPetsByInvalidStatusTest {

    private static Response response;
    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
    private static final String FIND_BY_STATUS_PATH = "/pet/findByStatus";

    private Response getResponseForStatus(String status) {
        return RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath(FIND_BY_STATUS_PATH)
                .queryParam("status", status)
                .header("accept", "application/json")
                .when()
                .get()
                .then()
                .extract().response();
    }

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    @DisplayName("Get request for invalid 'purchased' status return 400")
    void getInvalidStatus_Returns400() {
        response = getResponseForStatus("purchased");
        MatcherAssert.assertThat(response.statusCode(), is(400));
    }

    @Test
    @DisplayName("Get null status parameter return 400")
    void getMissingStatus_Returns400() {
        response = getResponseForStatus("");
        MatcherAssert.assertThat(response.statusCode(), is(400));
    }

    @Test
    @DisplayName("Get omitted status parameter return 400")
    void testMissingStatus() {
        response = RestAssured
                .given()
                .basePath(FIND_BY_STATUS_PATH)
                .header("accept", "application/json")
                .when()
                .get()
                .then()
                .log().all()
                .extract().response();
        MatcherAssert.assertThat(response.statusCode(), is(400));
    }
}
