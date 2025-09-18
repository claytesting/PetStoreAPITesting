package happyPath.petEndpoint;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;
import static utils.PetShopAPI.*;

public class GetPetByIdHappyPathTest {

    private static Response response;

    private static int validPetId;

    private static final String NAME = "doggie";
    private static final String STATUS = "available";

    @BeforeEach
    void setup() {
        validPetId = setupPetForQuery();
        response = getPetById(validPetId);
    }

    @Test
    @DisplayName("Get pet with a valid Id returns a pet with that Id")
    void getPetWithId_ReturnsThatPet() {
        MatcherAssert.assertThat(response.jsonPath().get("id"), is(validPetId));
    }

    @Test
    @DisplayName("Get pet with a specific Id and check the status code")
    void getPetWithId_ChecksStatusCode() {
        MatcherAssert.assertThat(response.statusCode(), is(200));
    }

    @Test
    @DisplayName("Validate the name in the response")
    void getPetWithId_validateResponseName() {
        MatcherAssert.assertThat(response.jsonPath().getString("name"), Matchers.containsString(NAME));
    }

    @Test @DisplayName("Validate the status in the response")
    void getPetWithId_validateResponseStatus() {
        MatcherAssert.assertThat(response.jsonPath().getString("status"), Matchers.containsString(STATUS));
    }

    @Nested
    class UnhappyPathDefaultErrorTest {

        @Disabled("Pet Store usually responds 400 disabled for documentation")
        @Test
        @DisplayName("Get pet with unexpected inputs/headers gives default unexpected error")
        void getPetDefaultError_Expect5xx() {
            // Try to cause a server error with unexpected inputs/headers (returns 400)
            String weirdId = "%00";
            Response r = RestAssured
                    .given()
                    .spec(getPetByIdRequestSpec(weirdId))
                    .header("Accept", "application/invalid+type")
                    .when()
                    .log().all()
                    .get()
                    .then()
                    .log().all()
                    .extract()
                    .response();
            MatcherAssert.assertThat(response.statusCode(), allOf(greaterThanOrEqualTo(500), lessThan(600)));
        }
    }
}
