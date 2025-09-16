import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class UpdatePetByIdTest {

    private static Response response;
    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
    private static final String POST_BY_ID_PATH = "/pet/{petId}";

    @BeforeAll
    static void setup() {
        response = RestAssured
                .given()
                .baseUri(BASE_URI)
                .basePath(POST_BY_ID_PATH)
                .headers(Map.of("Accept", "application/json"))
                .pathParam("petId", "10")
                .queryParams(Map.of(
                        "name", "jason",
                        "status", "available"
                        ))

                .when()
                .post()

                .then()
                .log().all()
                .extract().response();

    }

    @Test
    @DisplayName("Given valid data return response code 200")
    void updatePetById_ValidData_ReturnCode200() {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Given valid input data check data in return JSON")
    void updatePetById_ValidData_NameIsJason() {
        MatcherAssert.assertThat(response.jsonPath().getString("name"), Matchers.is("jason"));
    }

    @Test
    @DisplayName("Given valid input data check data in return JSON")
    void updatePetById_ValidData_IdIs10() {
        MatcherAssert.assertThat(response.jsonPath().getInt("id"), Matchers.is(10));
    }

    @Test
    @DisplayName("Given valid input data check data in return JSON")
    void updatePetById_ValidData_StatusIsAvailable() {
        MatcherAssert.assertThat(response.jsonPath().getString("status"), Matchers.is("available"));
    }


}
