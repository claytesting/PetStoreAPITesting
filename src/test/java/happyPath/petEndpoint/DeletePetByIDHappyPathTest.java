package happyPath.petEndpoint;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojos.Pet;

import static utils.PetShopAPI.*;

public class DeletePetByIDHappyPathTest {

    private static Response response1;
    private static Response response2;

    @BeforeAll
    static void setup() {
        int petId = setupPetForQuery();
        response1 = RestAssured
                .given(deletePetByIdRequestSpec(petId))
                .when()
                .delete()

                .then()
                .extract().response();

        response2 = getPetById(petId);
    }

    @Test
    @DisplayName("Given valid ID return response code 200")
    void deletePetByID_GivenValidID_ReturnCode200() {
        MatcherAssert.assertThat(response1.statusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Given valid ID return body Pet deleted")
    void deletePetByID_GivenValidID_ReturnPetDeleted() {
        MatcherAssert.assertThat(response1.asString(), Matchers.is("Pet deleted"));
    }

    @Test
    @DisplayName("Given valid Id check pet actually deleted")
    void deletePetByID_GivenValidID_CheckPetDeleted() {
        Pet pet = response2.as(Pet.class);
        MatcherAssert.assertThat(pet.toString().isEmpty(), Matchers.is(true));
    }
}
