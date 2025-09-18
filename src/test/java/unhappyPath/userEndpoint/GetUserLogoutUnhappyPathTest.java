package unhappyPath.userEndpoint;

import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static utils.PetShopAPI.*;
import static org.hamcrest.Matchers.*;

public class GetUserLogoutUnhappyPathTest {
    private static Response response;

    @BeforeAll
    static void setup(){
        response = getUserLogout_DefaultError();
    }

    @Test
    @DisplayName("Get user login returns 500 status code for malformed headers")
    void logout_UnexpectedError_CheckStatusCode() {
        MatcherAssert.assertThat(response.statusCode(), allOf(greaterThanOrEqualTo(500), lessThan(600)));
    }
}
