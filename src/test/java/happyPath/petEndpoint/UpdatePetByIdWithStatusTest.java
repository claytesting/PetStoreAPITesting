package happyPath.petEndpoint;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojos.Pet;

import java.util.Map;

import static utils.PetShopAPI.setupPetForQuery;
import static utils.PetShopAPI.updatePetByIdRequestSpec;

public class UpdatePetByIdWithStatusTest {

    private static Response response;
    private static Pet pet;

    @BeforeAll
    static void setup() {
        int petId = setupPetForQuery();
        response = RestAssured
                .given(updatePetByIdRequestSpec(petId))
                .queryParams(Map.of(
                        "name", "jason",
                        "status", "available"
                        ))

                .when()
                .post()

                .then()
                .extract().response();

        pet = response.as(Pet.class);
    }

    @Test
    @DisplayName("Given valid data return response code 200")
    void updatePetById_ValidData_ReturnCode200() {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Given valid input data check data in return JSON")
    void updatePetById_ValidData_NameIsJason() {
        MatcherAssert.assertThat(pet.getName(), Matchers.is("jason"));
    }

    @Test
    @DisplayName("Given valid input data check data in return JSON")
    void updatePetById_ValidData_IdIs10() {
        MatcherAssert.assertThat(pet.getId(), Matchers.is(7041));
    }

    @Test
    @DisplayName("Given valid input data check data in return JSON")
    void updatePetById_ValidData_StatusIsAvailable() {
        MatcherAssert.assertThat(pet.getStatus(), Matchers.is("available"));
    }


}
