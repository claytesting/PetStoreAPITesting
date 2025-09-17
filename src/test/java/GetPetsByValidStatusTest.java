import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class GetPetsByValidStatusTest {
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
    @DisplayName("Get available status pets to return 200 and correct available status field")
    void getAvailablePets_Returns200AndStatusField() {
        response = getResponseForStatus("available");
        MatcherAssert.assertThat(response.statusCode(), is(200));
    }

    @Test
    @DisplayName("Get request for pending status pets return 200 and correct status fields")
    void getPendingPets_Returns200AndStatusField() {
        response = getResponseForStatus("pending");
        MatcherAssert.assertThat(response.statusCode(), is(200));
    }

    @Test
    @DisplayName("Get request for sold status pets return 200 and correct status fields")
    void getSoldPets_Returns200AndStatusField() {
        response = getResponseForStatus("sold");
        MatcherAssert.assertThat(response.statusCode(), is(200));
    }
}
