package happyPath.userEndpoint;

import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;

import static utils.PetShopAPI.*;
import org.junit.jupiter.api.*;
import static org.hamcrest.Matchers.*;


public class GetUserLogoutHappyPath {
    private static Response response;

    @BeforeAll
    static void logoutSetup() {
        response = getUserLogout();
    }

    @Test
    @DisplayName("Get user logout returns 200 status code")
    void logout_CheckStatusCode() {
        MatcherAssert.assertThat(response.statusCode(), is(200));
    }

    @Test
    @DisplayName("Get user logout response contains User logged out")
    void logout_CheckResponse() {
        MatcherAssert.assertThat(response.asString(), startsWith("User logged out"));
    }
}
