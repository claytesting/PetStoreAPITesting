package happyPath;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static utils.PetShopAPI.setupPetForQuery;
import static utils.PetShopAPI.updatePetByIdRequestSpec;

public class UpdatePetByIdNoStatusTest {

    private static Response response;

    @BeforeAll
    static void setup() {
        int petId = setupPetForQuery();
        response = RestAssured
                .given(updatePetByIdRequestSpec(petId))
                .queryParams(Map.of(
                        "name", "jason"
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
        MatcherAssert.assertThat(response.jsonPath().getInt("id"), Matchers.is(7041));
    }

}
