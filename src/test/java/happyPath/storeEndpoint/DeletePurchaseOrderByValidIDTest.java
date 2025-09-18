package happyPath.storeEndpoint;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class DeletePurchaseOrderByValidIDTest {
    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
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
    @DisplayName("Delete order with valid ID returns 200")
    void deleteValidOrderId_Returns200() {
        response = deleteOrder("12");
        MatcherAssert.assertThat(response.statusCode(), is(200));
    }

    @Test
    @DisplayName("Delete order with valid 2 ID returns 200")
    void deleteValid1OrderId_Returns200() {
        response = deleteOrder("2");
        MatcherAssert.assertThat(response.statusCode(), is(200));
    }

}
