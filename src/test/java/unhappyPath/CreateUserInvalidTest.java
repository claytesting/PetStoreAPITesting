package unhappyPath;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojos.User;
import utils.PetShopAPI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CreateUserInvalidTest {
    private static Response response;

    @Test
    @DisplayName("Post request for creating user with missing username returns 400")
    void postCreateUserWithMissingUsername_Returns400() {
        User user = new User();
        user.setId(101);
        user.setFirstName("Lemon");
        user.setLastName("Tart");
        user.setEmail("lemontart@bakeryemail.com");
        user.setPassword("Lemon!1");
        user.setPhone("025647806020");
        user.setUserStatus(1);

        response = PetShopAPI.createUser(user);
        assertThat(response.statusCode(), is(400));
    }

    @Test // email is accepted as String with no checks on email regex
    @DisplayName("Post user with invalid email format returns 400")
    void postCreateUserWithInvalidEmailFormat_Returns400() {
        User user = new User();
        user.setId(12);
        user.setUsername("eggMuffin");
        user.setFirstName("Egg");
        user.setLastName("Smith");
        user.setEmail("eggMuffin.com"); // Invalid format
        user.setPassword("BreAk*Fast3");
        user.setPhone("02983578325");
        user.setUserStatus(1);

        response = PetShopAPI.createUser(user);
        assertThat(response.statusCode(), is(400));
    }

    @Test  // overwrites account details instead of giving a conflict error
    @DisplayName("Post user with existing username returns 409")
    void postCreateUserWithExistingUsername_Returns409() {
        User originalUser = new User();
        originalUser.setId(7251);
        originalUser.setUsername("cookieDough");
        originalUser.setFirstName("Cookie");
        originalUser.setLastName("Dough");
        originalUser.setEmail("cookie.dough@icecreamail.com");
        originalUser.setPassword("CookieDoughyIceCream3456835746");
        originalUser.setPhone("370258930-745789");
        originalUser.setUserStatus(1);

        response = PetShopAPI.createUser(originalUser);
        assertThat(response.statusCode(), is(200));

        User newUserWithSameUsername = new User();
        newUserWithSameUsername.setId(7411);
        newUserWithSameUsername.setUsername("cookieDough");
        newUserWithSameUsername.setFirstName("Chocolate");
        newUserWithSameUsername.setLastName("Strawberry");
        newUserWithSameUsername.setEmail("chocystrawberry@dessertmail.com");
        newUserWithSameUsername.setPassword("hAppyDessert1*");
        newUserWithSameUsername.setPhone("20356352-5");
        newUserWithSameUsername.setUserStatus(1);

        response = PetShopAPI.createUser(newUserWithSameUsername);
        assertThat(response.statusCode(), is(409));

        // Get request for cookieDough to see whether original cookieDough details has been overwritten instead
        response = RestAssured
                .given(PetShopAPI.getUserByUsernameRequestSpec()
                        .pathParam("username", "cookieDough"))
                .when()
                .get()
                .then()
                .extract()
                .response();

        User actualUser = response.as(User.class);

        assertThat(response.statusCode(), is(200));
        assertThat(actualUser.getFirstName(), is("Chocolate"));
        assertThat(actualUser.getUsername(), is("cookieDough"));
    }
}
