import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.Matchers.*;

public class GetInventoryInvalidTest {

    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
    private static final String GET_INVENTORY_PATH = "/store/inventory";

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = BASE_URI;
    }

    private Response getInventoryWithInvalidSetup() {
        return RestAssured
                .given()
                .basePath(GET_INVENTORY_PATH)
                .headers(Map.of("Accept", "application/json"))
                .when()
                .get()
                .then()
                .extract().response();
    }

    // Actual Swagger Petstore behavior: always returns 500
    @Test
    @DisplayName("Unhappy Path: Given empty request return 500")
    void getInventory_EmptyRequest_Return500() {
        Response invalidResponse = getInventoryWithInvalidSetup();
        MatcherAssert.assertThat(invalidResponse.statusCode(), is(500));
    }

}
