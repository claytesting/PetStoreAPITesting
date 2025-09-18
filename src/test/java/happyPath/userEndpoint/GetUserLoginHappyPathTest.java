package happyPath.userEndpoint;

import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;
import static org.hamcrest.Matchers.*;
import static utils.PetShopAPI.*;


public class GetUserLoginHappyPathTest {
    private static Response response;

    @BeforeEach
    void setup() {
        response = getUserLogin(validUsername, validPassword);
    }

    @Test
    @DisplayName("Get user/login with valid credentials returns 200 status code")
    void getUserLogin_Returns200() {
        MatcherAssert.assertThat(response.statusCode(), is(200));
    }

    @Test
    @DisplayName("Get user/login with valid credentials response starts with Logged in user session")
    void getUserLogin_Message() {
        MatcherAssert.assertThat(response.asString(), startsWith("Logged in user session"));
    }
}
