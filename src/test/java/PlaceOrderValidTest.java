import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojos.Order;

import java.util.Map;

public class PlaceOrderValidTest {

    private static Response response;
    private static Order order;
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
        order = response.as(Order.class);
    }

    @Test
    @DisplayName("Happy Path: Given valid data return response code 200")
    void placeOrder_ValidData_ReturnCode200() {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Happy Path: Given valid input data check quantity is 5")
    void placeOrder_ValidData_QuantityIs5() {
        MatcherAssert.assertThat(order.getQuantity(), Matchers.is(5));
    }

    @Test
    @DisplayName("Happy Path: Given valid input data check status is approved")
    void placeOrder_ValidData_StatusIsApproved() {
        MatcherAssert.assertThat(order.getStatus(), Matchers.is("approved"));
    }

    @Test
    @DisplayName("Happy Path: Given valid input data check complete is true")
    void placeOrder_ValidData_CompleteIsTrue() {
        MatcherAssert.assertThat(order.isComplete(), Matchers.is(true));
    }

    @Test
    @DisplayName("Happy Path: Given valid input data check id is 10")
    void placeOrder_ValidData_IdIs10() {
        MatcherAssert.assertThat(order.getId(), Matchers.is(10));
    }
}
