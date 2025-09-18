package unhappyPath;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.PetShopAPI;

import static org.hamcrest.Matchers.*;

public class GetPetsByInvalidStatusTest {

    private static Response response;

    @Test
    @DisplayName("Get request for invalid 'purchased' status return 400")
    void getInvalidStatus_Returns400() {
        response = PetShopAPI.getPetsByStatus("purchased");
        MatcherAssert.assertThat(response.statusCode(), is(400));
    }

    @Test
    @DisplayName("Get null status parameter return 400")
    void getMissingStatus_Returns400() {
        response = PetShopAPI.getPetsByStatus("");
        MatcherAssert.assertThat(response.statusCode(), is(400));
    }

    @Test
    @DisplayName("Get omitted status parameter return 400")
    void getOmittedStatus_Returns400() {
        response = RestAssured
                .given()
                .baseUri("https://petstore3.swagger.io/api/v3")
                .basePath("/pet/findByStatus")
                .header("accept", "application/json")
                .when()
                .get()
                .then()
                .extract().response();
        MatcherAssert.assertThat(response.statusCode(), is(400));
    }
}
