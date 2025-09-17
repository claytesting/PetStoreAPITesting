import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.hamcrest.Matchers.*;

public class GetPetByIdTest {

    private static Response response;
    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
    private static final String PATH = "/pet/{petId}";

    private static final int validPetId = 10;
    private static final String invalidPetId = "abcd";
    public static final int negativeId = -1234;
    private static final String NAME = "";
    private static final String STATUS = "available";

    private static RequestSpecBuilder getBaseSpecBuilder(String path) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setBasePath(path)
                .addHeaders(Map.of(
                        "Accept", "application/json"
                ));
    }

    public static RequestSpecification getPetByIdRequestSpec(Object petId) {
        return getBaseSpecBuilder(PATH)
                .addPathParams(Map.of(
                        "petId", petId
                ))
                .build();
    }

    public static void getPetById(Object petId) {
        response =
                RestAssured
                        .given().spec(getPetByIdRequestSpec(petId))

                        .when()
                        .log().all()
                        .get()
                        .then()
                        .log().all()
                        .extract()
                        .response();
    }


    @Nested
    class HappyPathTests {
        @BeforeEach
        public void beforeEach() {
            getPetById(validPetId);
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

        @Test
        @DisplayName("Validate the status in the response")
        void getPetWithId_validateResponseStatus() {
            MatcherAssert.assertThat(response.jsonPath().getString("status"), Matchers.containsString(STATUS));
        }
    }

    @Nested
    class UnhappyPath400Test {

        @Test
        @DisplayName("Get pet with an invalid Id and check the status code")
        void getPetWithInvalidId_ChecksStatusCode() {
            getPetById(invalidPetId);
            MatcherAssert.assertThat(response.statusCode(), is(400));
        }

        @Test
        @DisplayName("Get pet with a negative Id and check the status code")
        void getPetWithNegativeId_ChecksStatusCode() {
            getPetById(negativeId);
            MatcherAssert.assertThat(response.statusCode(), is(400));
        }
    }

    @Nested
    class UnhappyPath404Test {

        @BeforeEach
        public void beforeEach() {
            getPetById(1000000000);
        }
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
