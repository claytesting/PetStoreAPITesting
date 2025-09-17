package unhappyPath;

import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static utils.PetShopAPI.getPetById;

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

    @Test
    @DisplayName("Get pet with a negative Id and check the status code")
    void getPetWithNegativeId_ChecksStatusCode() {
        response = getPetById(negativeId);
        MatcherAssert.assertThat(response.statusCode(), is(404));
    }

    @Test
    @DisplayName("Get pet with an Id that doesn't exist and check the status code")
    void getPetWithIdThatDoesNotExist_ChecksStatusCode() {
        response = getPetById(outOfBoundsId);
        MatcherAssert.assertThat(response.statusCode(), is(404));
    }
}
