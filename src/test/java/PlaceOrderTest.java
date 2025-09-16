import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class PlaceOrderTest {

    private static Response response;
    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
    private static final String PLACE_ORDER_PATH = "/store/order";

    private static final String VALID_REQUEST_BODY = """
        {
          "id": 10,
          "petId": 198772,
          "quantity": 5,
          "shipDate": "2025-09-16T10:17:34.144Z",
          "status": "approved",
          "complete": true
        }
        """;

    @BeforeAll
    static void setup() {
        response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath(PLACE_ORDER_PATH)
                .headers(Map.of("Accept", "application/json",
                        "Content-Type", "application/json"))
                .body(VALID_REQUEST_BODY)

                .when()
                .post()

                .then()
                .log().all()
                .extract().response();
    }


    // ---------- Happy Path Tests ----------------

    @Test
    @DisplayName("Happy Path: Given valid data return response code 200")
    void placeOrder_ValidData_ReturnCode200() {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Happy Path: Given valid input data check quantity is 5")
    void placeOrder_ValidData_QuantityIs5() {
        MatcherAssert.assertThat(response.jsonPath().getInt("quantity"), Matchers.is(5));
    }

    @Test
    @DisplayName("Happy Path: Given valid input data check status is approved")
    void placeOrder_ValidData_StatusIsApproved() {
        MatcherAssert.assertThat(response.jsonPath().getString("status"), Matchers.is("approved"));
    }

    @Test
    @DisplayName("Happy Path: Given valid input data check complete is true")
    void placeOrder_ValidData_CompleteIsTrue() {
        MatcherAssert.assertThat(response.jsonPath().getBoolean("complete"), Matchers.is(true));
    }

    @Test
    @DisplayName("Happy Path: Given valid input data check id is 10")
    void placeOrder_ValidData_IdIs10() {
        MatcherAssert.assertThat(response.jsonPath().getInt("id"), Matchers.is(10));
    }

    // ----- Unhappy Path Tests -----------


    @Test
    @DisplayName("Unhappy Path: Given invalid id (abc) return 400 with input error message")
    void placeOrder_InvalidId_Return400() {
        String invalidBody = """
            {
              "id": "abc",
              "petId": 198772,
              "quantity": 5,
              "shipDate": "2025-09-16T10:17:34.144Z",
              "status": "approved",
              "complete": true
            }
            """;

        Response invalidResponse = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath(PLACE_ORDER_PATH)
                .headers(Map.of("Accept", "application/json",
                        "Content-Type", "application/json"))
                .body(invalidBody)
                .when()
                .post()
                .then()
                .log().all()
                .extract().response();

        MatcherAssert.assertThat(invalidResponse.statusCode(), Matchers.is(400));
        MatcherAssert.assertThat(
                invalidResponse.asString(),
                Matchers.containsString("Input error: unable to convert input to io.swagger.petstore.model.Order")
        );
    }

    @Test
    @DisplayName("Unhappy Path: Given invalid quantity (five) return 400 with input error message")
    void placeOrder_InvalidQuantity_Return400() {
        String invalidBody = """
            {
              "id": 11,
              "petId": 198772,
              "quantity": "five",
              "shipDate": "2025-09-16T10:17:34.144Z",
              "status": "approved",
              "complete": true
            }
            """;

        Response invalidResponse = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath(PLACE_ORDER_PATH)
                .headers(Map.of("Accept", "application/json",
                        "Content-Type", "application/json"))
                .body(invalidBody)
                .when()
                .post()
                .then()
                .log().all()
                .extract().response();

        MatcherAssert.assertThat(invalidResponse.statusCode(), Matchers.is(400));
        MatcherAssert.assertThat(
                invalidResponse.asString(),
                Matchers.containsString("Input error: unable to convert input to io.swagger.petstore.model.Order")
        );
    }

    // Should be throwing a 400 but accepts the negative and returns a 200
    @Test
    @DisplayName("Unhappy Path: Given invalid id (abc) return 400 with input error message")
    void placeOrder_NegativeId_Return400() {
        String invalidBody = """
            {
              "id": "-10",
              "petId": 198772,
              "quantity": 5,
              "shipDate": "2025-09-16T10:17:34.144Z",
              "status": "approved",
              "complete": true
            }
            """;

        Response invalidResponse = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath(PLACE_ORDER_PATH)
                .headers(Map.of("Accept", "application/json",
                        "Content-Type", "application/json"))
                .body(invalidBody)
                .when()
                .post()
                .then()
                .log().all()
                .extract().response();

        MatcherAssert.assertThat(invalidResponse.statusCode(), Matchers.is(400));
        MatcherAssert.assertThat(
                invalidResponse.asString(),
                Matchers.containsString("Input error: unable to convert input to io.swagger.petstore.model.Order")
        );
    }

    // ---------------------------------------------------

    // Expected to fail as no validation on Pet Store API

    @Test
    @DisplayName("Unhappy Path: Given invalid shipDate (invalid month) return 422")
    void placeOrder_InvalidDate_Return422() {
        String invalidBody = """
            {
              "id": 12,
              "petId": 198772,
              "quantity": 3,
              "shipDate": "2025-13-01T10:17:34.144Z",
              "status": "approved",
              "complete": true
            }
            """;

        Response invalidResponse = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath(PLACE_ORDER_PATH)
                .headers(Map.of("Accept", "application/json",
                        "Content-Type", "application/json"))
                .body(invalidBody)
                .when()
                .post()
                .then()
                .log().all()
                .extract().response();

        MatcherAssert.assertThat(invalidResponse.statusCode(), Matchers.is(422));
    }

    @Test
    @DisplayName("Unhappy Path: Given non-existent ID return 422 with error message")
    void placeOrder_NonExistentId_Return422() {
        String invalidBody = """
            {
              "id": 999999,
              "petId": 198772,
              "quantity": 1,
              "shipDate": "2025-09-16T10:17:34.144Z",
              "status": "approved",
              "complete": true
            }
            """;

        Response invalidResponse = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath(PLACE_ORDER_PATH)
                .headers(Map.of("Accept", "application/json",
                        "Content-Type", "application/json"))
                .body(invalidBody)
                .when()
                .post()
                .then()
                .log().all()
                .extract().response();

        MatcherAssert.assertThat(invalidResponse.statusCode(), Matchers.is(422));
        MatcherAssert.assertThat(
                invalidResponse.asString(),
                Matchers.containsString("ID doesn’t exist")
        );
    }

    @Test
    @DisplayName("Unhappy Path: Unexpected error should return 500")
    void placeOrder_UnexpectedError_Return500() {
        String invalidBody = """
            {
              "id": null,
              "petId": null,
              "quantity": null,
              "shipDate": null,
              "status": null,
              "complete": null
            }
            """;

        Response invalidResponse = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath(PLACE_ORDER_PATH)
                .headers(Map.of("Accept", "application/json",
                        "Content-Type", "application/json"))
                .body(invalidBody)
                .when()
                .post()
                .then()
                .log().all()
                .extract().response();

        MatcherAssert.assertThat(invalidResponse.statusCode(), Matchers.is(500));
    }
}
