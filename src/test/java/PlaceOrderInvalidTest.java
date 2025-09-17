import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.Matchers.*;

public class PlaceOrderInvalidTest {

    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
    private static final String PLACE_ORDER_PATH = "/store/order";

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = BASE_URI;
    }

    private Response postInvalidOrder(String requestBody) {
        return RestAssured
                .given()
                .basePath(PLACE_ORDER_PATH)
                .headers(Map.of(
                        "Accept", "application/json",
                        "Content-Type", "application/json"
                ))
                .body(requestBody)
                .when()
                .post()
                .then()
                .extract().response();
    }

    // Should be throwing a 400 but accepts the negative and returns a 200
    @Test
    @DisplayName("Unhappy Path: Given invalid id (abc) return 400 with input error message")
    void placeOrder_InvalidId_Return400() {
        Response invalidResponse = postInvalidOrder("""
            {
              "id": "abc",
              "petId": 198772,
              "quantity": 5,
              "shipDate": "2025-09-16T10:17:34.144Z",
              "status": "approved",
              "complete": true
            }
            """);
        MatcherAssert.assertThat(invalidResponse.statusCode(), is(400));
        MatcherAssert.assertThat(
                invalidResponse.asString(),
                containsString("Input error: unable to convert input to io.swagger.petstore.model.Order"));
    }

    @Test
    @DisplayName("Unhappy Path: Given invalid quantity (five) return 400 with input error message")
    void placeOrder_InvalidQuantity_Return400() {
        Response invalidResponse = postInvalidOrder("""
                {
                  "id": 11,
                  "petId": 198772,
                  "quantity": "five",
                  "shipDate": "2025-09-16T10:17:34.144Z",
                  "status": "approved",
                  "complete": true
                }
                """);
        MatcherAssert.assertThat(invalidResponse.statusCode(), is(400));
        MatcherAssert.assertThat(
                invalidResponse.asString(),
                containsString("Input error: unable to convert input to io.swagger.petstore.model.Order"));
    }

    @Test
    @DisplayName("Unhappy Path: Given invalid id (-10) return 400 with input error message")
    void placeOrder_NegativeId_Return400() {
        Response invalidResponse = postInvalidOrder("""
            {
              "id": "-10",
              "petId": 198772,
              "quantity": 5,
              "shipDate": "2025-09-16T10:17:34.144Z",
              "status": "approved",
              "complete": true
            }
            """);
        MatcherAssert.assertThat(invalidResponse.statusCode(), is(400));
        MatcherAssert.assertThat(
                invalidResponse.asString(),
                containsString("Input error: unable to convert input to io.swagger.petstore.model.Order"));
    }

    @Test
    @DisplayName("Unhappy Path: Given invalid shipDate (invalid month) return 422")
    void placeOrder_InvalidDate_Return422() {
        Response invalidResponse = postInvalidOrder("""
                {
                  "id": 12,
                  "petId": 198772,
                  "quantity": 3,
                  "shipDate": "2025-13-01T10:17:34.144Z",
                  "status": "approved",
                  "complete": true
                }
                """);
        MatcherAssert.assertThat(invalidResponse.statusCode(), is(422));
    }

    @Test
    @DisplayName("Unhappy Path: Given non-existent ID return 422 with error message")
    void placeOrder_NonExistentId_Return422() {
        Response invalidResponse = postInvalidOrder("""
                {
                  "id": 999999,
                  "petId": 198772,
                  "quantity": 1,
                  "shipDate": "2025-09-16T10:17:34.144Z",
                  "status": "approved",
                  "complete": true
                }
                """);
        MatcherAssert.assertThat(invalidResponse.statusCode(), is(422));
        MatcherAssert.assertThat(
                invalidResponse.asString(),
                containsString("ID doesn’t exist"));
    }

    @Test
    @DisplayName("Unhappy Path: Unexpected error should return 500")
    void placeOrder_UnexpectedError_Return500() {
        Response invalidResponse = postInvalidOrder("""
                {
                  "id": null,
                  "petId": null,
                  "quantity": null,
                  "shipDate": null,
                  "status": null,
                  "complete": null
                }
                """);
        MatcherAssert.assertThat(invalidResponse.statusCode(), is(500));
    }
}
