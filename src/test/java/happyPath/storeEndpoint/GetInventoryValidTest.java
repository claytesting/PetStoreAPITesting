package happyPath.storeEndpoint;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class GetInventoryValidTest {

    private static Response response;
    private static Map<String, Object> inventory;
    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
    private static final String GET_INVENTORY_PATH = "/store/inventory";

    @BeforeAll
    static void setup() {
        response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath(GET_INVENTORY_PATH)
                .headers(Map.of("Accept", "application/json"))
                .when()
                .get()
                .then()
                .extract().response();

        inventory = response.as(Map.class);
    }

    @Test
    @DisplayName("Happy Path: Given valid request return response code 200")
    void getInventory_ValidRequest_ReturnCode200() {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Happy Path: Inventory contains available status key")
    void getInventory_ContainsAvailableKey() {
        //Test skipped as condition is false
        assumeTrue(response.statusCode() == 200, "Skipping because API did not return 200");
        MatcherAssert.assertThat(inventory.keySet(), Matchers.hasItem("available"));
    }

    @Test
    @DisplayName("Happy Path: Inventory contains pending status key")
    void getInventory_ContainsPendingKey() {
        //Test skipped as condition is false
        assumeTrue(response.statusCode() == 200, "Skipping because API did not return 200");
        MatcherAssert.assertThat(inventory.keySet(), Matchers.hasItem("pending"));
    }

    @Test
    @DisplayName("Happy Path: Inventory contains sold status key")
    void getInventory_ContainsSoldKey() {
        //Test skipped as condition is false
        assumeTrue(response.statusCode() == 200, "Skipping because API did not return 200");
        MatcherAssert.assertThat(inventory.keySet(), Matchers.hasItem("sold"));
    }
}
