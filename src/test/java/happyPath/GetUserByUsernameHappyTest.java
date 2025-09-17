package happyPath;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojos.User;

import static utils.PetShopAPI.*;

public class GetUserByUsernameHappyTest {

    private static Response response;
    private static User user;

    @BeforeAll
    static void setup() {
        response = RestAssured
                .given(getUserByUsernameRequestSpec())
                .pathParam("username", "user1")

                .when()
                .get()

                .then()
                .extract().response();

        user = response.as(User.class);
    }

    @Test
    @DisplayName("Given a valid Username return 200 code")
    void getUserByUsername_ValidUsername_ReturnCode200() {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Given a valid Username return correct username")
    void getUserByUsername_ValidUsername_ReturnCorrectUsername() {
        MatcherAssert.assertThat(user.getUsername(), Matchers.is("user1"));
    }

    @Test
    @DisplayName("Given a valid Username return correct first name")
    void getUserByUsername_ValidUsername_ReturnCorrectFirstName() {
        MatcherAssert.assertThat(user.getFirstName(), Matchers.is("first name 1"));
    }

    @Test
    @DisplayName("Given a valid Username return correct last name")
    void getUserByUsername_ValidUsername_ReturnCorrectLastName() {
        MatcherAssert.assertThat(user.getLastName(), Matchers.is("last name 1"));
    }

    @Test
    @DisplayName("Given a valid Username return correct email")
    void getUserByUsername_ValidUsername_ReturnCorrectEmail() {
        MatcherAssert.assertThat(user.getEmail(), Matchers.is("email1@test.com"));
    }

    @Test
    @DisplayName("Given a valid Username return correct Password")
    void getUserByUsername_ValidUsername_ReturnCorrectPassword() {
        MatcherAssert.assertThat(user.getPassword(), Matchers.is("XXXXXXXXXXX"));
    }

    @Test
    @DisplayName("Given a valid Username return correct Phone")
    void getUserByUsername_ValidUsername_ReturnCorrectPhone() {
        MatcherAssert.assertThat(user.getPhone(), Matchers.is("123-456-7890"));
    }

    @Test
    @DisplayName("Given a valid Username return correct Status")
    void getUserByUsername_ValidUsername_ReturnCorrectStatus() {
        MatcherAssert.assertThat(user.getUserStatus(), Matchers.is(1));
    }
}
