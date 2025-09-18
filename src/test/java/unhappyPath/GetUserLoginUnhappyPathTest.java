package unhappyPath;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;
import static utils.PetShopAPI.*;

public class GetUserLoginUnhappyPathTest {
    private static Response response;

    @Nested
    class MissingUsernameTests {
        @BeforeEach
        void missingUsernameSetup() {
            response = getUserLogin(null, "password");
        }

        @Test
        @DisplayName("Get user/login with missing username returns 400 status code")
        void getUserLogin_missingUsername_returns400() {
            MatcherAssert.assertThat(response.statusCode(), is(400));
        }

        @Test
        @DisplayName("Get user/login with missing username response contains Invalid username/password supplied")
        void getUserLogin_missingUsername_responseCheck() {
            MatcherAssert.assertThat(response.asString(), containsString("Invalid username/password supplied"));
        }
    }

    @Nested
    class MissingPasswordTests {
        @BeforeEach
        void missingPasswordSetup(){
            response = getUserLogin("invalid-user", null);
        }

        @Test
        @DisplayName("Get user/login with missing password returns 400 status code")
        void getUserLogin_missingPassword_returns400() {
            MatcherAssert.assertThat(response.statusCode(), is(400));
        }

        @Test
        @DisplayName("Get user/login with missing password response contains Invalid username/password supplied")
        void getUserLogin_missingPassword_responseCheck() {
            MatcherAssert.assertThat(response.asString(), containsString("Invalid username/password supplied"));
        }
    }

   @Nested
   class InvalidCredentialsTests{
        @BeforeEach
       void invalidCredentialsSetup(){
            response = getUserLogin("invalid-user", "wrong-password");
        }

       @Test
       @DisplayName("Get user login with invalid credentials returns 400 status code")
       void login_invalidCredentials_returns400() {
           MatcherAssert.assertThat(response.statusCode(), is(400));
       }

       @Test
       @DisplayName("Get user login with invalid credentials response contains Invalid username/password supplied")
       void login_invalidCredentials_responseCheck() {
           MatcherAssert.assertThat(response.asString(), containsString("Invalid username/password supplied"));
       }
   }

    @Test
    @DisplayName("Get user login returns 500 status code for malformed headers")
    void login_unexpectedError_ChecksStatusCode() {
        response = getUserLogin_DefaultError();
        MatcherAssert.assertThat(response.statusCode(), allOf(greaterThanOrEqualTo(500), lessThan(600)));
    }
}
