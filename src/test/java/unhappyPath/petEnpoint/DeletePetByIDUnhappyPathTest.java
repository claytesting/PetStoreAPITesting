package unhappyPath.petEnpoint;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static utils.PetShopAPI.deletePetByIdRequestSpec;

public class DeletePetByIDUnhappyPathTest {

    private static Response response;

    static void setup(Object petId) {
        response = RestAssured
                .given(deletePetByIdRequestSpec(petId))
                .when()
                .delete()

                .then()
                .extract().response();
    }

    @Test
    @DisplayName("Given invalid ID String return response code 400")
    void deletePetByID_GivenInValidIDString_ReturnCode400() {
        setup("hello");
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(400));
    }

    @Test
    @DisplayName("Given invalid ID Boolean return response code 400")
    void deletePetByID_GivenInValidIDBoolean_ReturnCode400() {
        setup(true);
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(400));
    }

    @Test
    @DisplayName("Given invalid ID Negative return response code 400")
    void deletePetByID_GivenInValidIDNegative_ReturnCode400() {
        setup(-83);
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(400));
    }

    @Test
    @DisplayName("Given an ID that does not exist return response code 404")
    void deletePetByID_GivenIdDoesNotExist_ReturnCode404() {
        setup(1000000);
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(400));
    }
}
