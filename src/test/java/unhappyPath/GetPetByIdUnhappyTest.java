package unhappyPath;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.lessThan;
import static utils.PetShopAPI.*;

public class GetPetByIdUnhappyTest {
    private static Response response;

    private static final String invalidPetId = "abcd";
    public static final int negativeId = -1234;
    public static final int outOfBoundsId = 1000000000;

    @Test
    @DisplayName("Get pet with an invalid Id and check the status code")
    void getPetWithInvalidId_ChecksStatusCode() {
        response = getPetById(invalidPetId);
        MatcherAssert.assertThat(response.statusCode(), is(400));
    }

    @Nested
    class InvalidIdTests{
        @BeforeEach
        void invalidIdTestSetup() {
            response = getPetById(invalidPetId);
        }

        @Test
        @DisplayName("Get pet with an invalid Id and check response contains Invalid ID supplied")
        void getPetWithInvalidId_ChecksResponse() {
            MatcherAssert.assertThat(response.asString(), containsString("Invalid ID supplied"));
        }

        @Test
        @DisplayName("Get pet with an invalid Id and check response contains Invalid ID supplied")
        void getPetWithInvalidId_ChecksStatusCode() {
            MatcherAssert.assertThat(response.statusCode(), is(400));
        }
    }

    @Nested
    class NegativeIdTests {
        @BeforeEach
        void negativeIdTestSetup() {
            response = getPetById(negativeId);
        }

        @Test
        @DisplayName("Get pet with an negative Id and check response contains Invalid ID supplied")
        void getPetWithNegativeId_ChecksResponse() {
            MatcherAssert.assertThat(response.asString(), containsString("Invalid ID supplied"));
        }

        @Test
        @DisplayName("Get pet with a negative Id and check the status code")
        void getPetWithNegativeId_ChecksStatusCode() {
            MatcherAssert.assertThat(response.statusCode(), is(400));
        }
    }

    @Nested
    class idDoesNotExistTests {
        @BeforeEach
        void idDoesNotExistTestsSetup() {
            response = getPetById(outOfBoundsId);
        }

        @Test
        @DisplayName("Get pet with an Id that doesn't exist and check the response contains Pet not found")
        void getPetWithIdThatDoesNotExist_ChecksResponse() {
            MatcherAssert.assertThat(response.asString(), containsString("Pet not found"));
        }

        @Test
        @DisplayName("Get pet with an Id that doesn't exist and check the status code")
        void getPetWithIdThatDoesNotExist_ChecksStatusCode() {
            MatcherAssert.assertThat(response.statusCode(), is(404));
        }
    }

    @Nested
    class UnhappyPathDefaultErrorTest {
        @Disabled("Pet Store usually responds 400, disabled for documentation")
        @Test
        @DisplayName("Get pet with unexpected inputs/headers gives default unexpected error")
        void getPetDefaultError_Expect5xx() {
            // Try to cause a server error with unexpected inputs/headers (returns 400)
            getPetById_DefaultError();
            MatcherAssert.assertThat(response.statusCode(), allOf(greaterThanOrEqualTo(500), lessThan(600)));
        }
    }
}
