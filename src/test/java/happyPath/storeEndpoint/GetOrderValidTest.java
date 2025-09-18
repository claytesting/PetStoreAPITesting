package happyPath.storeEndpoint;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojos.Order;

import java.util.Map;

public class GetOrderValidTest {

    private static Response response;
    private static Order order;
    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
    private static final String GET_ORDER_PATH = "/store/order/10";

    @BeforeAll
    static void setup() {
        response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath(GET_ORDER_PATH)
                .headers(Map.of("Accept", "application/json"))
                .when()
                .get()
                .then()
                .extract().response();
        order = response.as(Order.class);
    }

    @Test
    @DisplayName("Happy Path: Given valid orderId (10) return response code 200")
    void getOrder_ValidId_ReturnCode200() {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Happy Path: Given valid orderId (10) check id is 10")
    void getOrder_ValidId_IdIs10() {
        MatcherAssert.assertThat(order.getId(), Matchers.is(10));
    }

    @Test
    @DisplayName("Happy Path: Given valid orderId (10) check status is approved")
    void getOrder_ValidId_StatusIsApproved() {
        MatcherAssert.assertThat(order.getStatus(), Matchers.is("approved"));
    }

    @Test
    @DisplayName("Happy Path: Given valid orderId (10) check quantity is 5")
    void getOrder_ValidId_QuantityIs5() {
        MatcherAssert.assertThat(order.getQuantity(), Matchers.is(5));
    }

    //Returns false
    @Test
    @DisplayName("Happy Path: Given valid orderId (10) check complete is true")
    void getOrder_ValidId_CompleteIsTrue() {
        MatcherAssert.assertThat(order.isComplete(), Matchers.is(true));
    }

}

