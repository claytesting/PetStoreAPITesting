package happyPath.userEndpoint;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojos.User;
import utils.PetShopAPI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CreateUserValidTest {

    @Test
    @DisplayName("Login to an account then post with valid user account body returns 200")
    void getLoginThenPostCreateValidUser_Returns200() {
        String loginUsername = "user1"; // from source code in UserData.java class
        String loginPassword = "XXXXXXXXXXX"; // from source code and is default password for those usernames

        Response loginResponse = PetShopAPI.loginUser(loginUsername, loginPassword);
        assertThat(loginResponse.statusCode(), is(200));

        User user = new User();
        user.setId(4537);
        user.setUsername("theApple");
        user.setFirstName("Apple");
        user.setLastName("Banana");
        user.setEmail("apple.banana@abemail.com");
        user.setPassword("I-Am-*Appl$*");
        user.setPhone("021326833146");
        user.setUserStatus(1); // user status is 1-registered, 2-active, 3-closed

        Response response = PetShopAPI.createUser(user);
        System.out.println("Create user status code: " + response.statusCode());
        System.out.println("Create user response: " + response.body().asString());
        assertThat(response.statusCode(), is(200));
    }
}
