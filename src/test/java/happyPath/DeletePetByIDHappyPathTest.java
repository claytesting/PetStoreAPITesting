package happyPath;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static utils.PetShopAPI.*;

public class DeletePetByIDHappyPathTest {

    private static Response response;

    @BeforeAll
    static void setup() {
        int petId = setupPetForQuery();
        response = RestAssured
                .given(deletePetByIdRequestSpec(petId))
                .when()
                .delete()

                .then()
                .extract().response();
    }

    @Test
    @DisplayName("Given valid ID return response code 200")
    void deletePetByID_GivenValidID_ReturnCode200() {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Given valid ID return body Pet deleted")
    void deletePetByID_GivenValidID_ReturnPetDeleted() {
        MatcherAssert.assertThat(response.asString(), Matchers.is("Pet deleted"));
    }

}
