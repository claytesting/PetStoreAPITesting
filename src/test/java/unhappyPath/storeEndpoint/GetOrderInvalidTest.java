package unhappyPath.storeEndpoint;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.Matchers.*;

public class GetOrderInvalidTest {

    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
    private static final String GET_ORDER_PATH = "/store/order";

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = BASE_URI;
    }

    private Response getOrderById(String orderId) {
        return RestAssured
                .given()
                .basePath(GET_ORDER_PATH + "/" + orderId)
                .headers(Map.of("Accept", "application/json"))
                .when()
                .get()
                .then()
                .extract().response();
    }

    @Test
    @DisplayName("Unhappy Path: Given non-existent orderId (9) return 404")
    void getOrder_NonExistentId9_Return404() {
        Response invalidResponse = getOrderById("9");
        MatcherAssert.assertThat(invalidResponse.statusCode(), is(404));
    }

    @Test
    @DisplayName("Unhappy Path: Given non-existent orderId (11) return 404")
    void getOrder_NonExistentId11_Return404() {
        Response invalidResponse = getOrderById("11");
        MatcherAssert.assertThat(invalidResponse.statusCode(), is(404));
    }

    // Should return 400 for invalid ID, but returns 200
    @Test
    @DisplayName("Unhappy Path: Given negative orderId (-10) return 404")
    void getOrder_NegativeId_Return404() {
        Response invalidResponse = getOrderById("-10");
        MatcherAssert.assertThat(invalidResponse.statusCode(), is(400));
    }

    @Test
    @DisplayName("Unhappy Path: Given non-integer orderId (abc) return 400")
    void getOrder_InvalidType_Return400() {
        Response invalidResponse = getOrderById("abc");
        MatcherAssert.assertThat(invalidResponse.statusCode(), is(400));
    }

    @Test
    @DisplayName("Unhappy Path: Unexpected error should return 400")
    void getOrder_UnexpectedError_Return400() {
        Response invalidResponse = getOrderById("null");
        MatcherAssert.assertThat(invalidResponse.statusCode(), is(400));
    }
}
