package unhappyPath.storeEndpoint;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.Matchers.*;

public class DeletePurchaseOrderByInvalidIDTest {

    private final String BASE_URI = "https://petstore3.swagger.io/api/v3";
    private static Response response;

    private Response deleteOrder(String orderId) {
        return RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath("/store/order/" + orderId)
                .header("accept", "application/json")
                .when()
                .delete()
                .then()
                .extract().response();
    }

    @Test
    @DisplayName("Delete with decimal ID returns 400")
    void deleteDecimalOrderId_Returns400() {
        response = deleteOrder("2.7");
        MatcherAssert.assertThat(response.statusCode(), is(400));
    }

    @Test
    @DisplayName("Delete with string ID returns 400")
    void deleteStringOrderId_Returns400() {
        response = deleteOrder("apple");
        MatcherAssert.assertThat(response.statusCode(), is(400));
    }

    @Test
    @DisplayName("Delete with value of 0 ID returns 400")
    void deleteValue0OrderId_Returns400() {
        response = deleteOrder("0");
        MatcherAssert.assertThat(response.statusCode(), is(400));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-", ",", "#", "*", "}", ")", "!", "%"})
    @DisplayName("Delete with special characters as orderId returns 400")
    void deleteWithSpecialCharOrderId_Returns400(String character) {
        response = deleteOrder(character);
        MatcherAssert.assertThat(response.statusCode(), is(400));
    }

    @Test
    @DisplayName("Delete with empty order ID path returns 405")
    void deleteMissingOrderIdPath_Returns405() {
        response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath("/store/order/")
                .header("accept", "application/json")
                .when()
                .delete()
                .then()
                .extract().response();
        MatcherAssert.assertThat(response.statusCode(), is(405));
    }

    @Test
    @DisplayName("Delete order with negative ID returns 404")
    void deleteNegativeOrderId_Returns404() {
        response = deleteOrder("-12"); // get -1 produces 404
        MatcherAssert.assertThat(response.statusCode(), is(404));
    }

    @Test
    @DisplayName("Delete with order ID over 1000 returns 404")
    void deleteOrderIdOver1000_Returns404() {
        response = deleteOrder("1001"); // order id doesn't exist
        MatcherAssert.assertThat(response.statusCode(), is(404));
    }

    @Test
    @DisplayName("Delete with very large number returns 404")
    void deleteVeryLargeOrderId_Returns404() {
        response = deleteOrder("123047256473257"); // order id doesn't exist
        MatcherAssert.assertThat(response.statusCode(), is(404));
    }

}
