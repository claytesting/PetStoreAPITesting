package unhappyPath;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static utils.PetShopAPI.getUserByUsernameNoPathParamRequestSpec;
import static utils.PetShopAPI.getUserByUsernameRequestSpec;

public class GetUserByUsernameUnhappyTest {

    private static Response response;

    static void setup(RequestSpecification spec) {
        response = RestAssured
                .given(spec)
                .when()
                .get()

                .then()
                .extract().response();
    }

    @Test
    @DisplayName("Given a valid Username as query param return 405 code")
    void getUserByUsername_ValidUsername_ReturnCode405() {
        setup(getUserByUsernameNoPathParamRequestSpec()
                .queryParam("username", "user1"));

        MatcherAssert.assertThat(response.statusCode(), Matchers.is(405));
    }

    @Test
    @DisplayName("Given a username that doesn't exist return 404 code")
    void getUserByUsername_UsernameDoesNotExist_ReturnCode404() {
        setup(getUserByUsernameRequestSpec()
                .pathParam("username", "usercoolmccoolface"));

        MatcherAssert.assertThat(response.statusCode(), Matchers.is(404));
    }

    @Test
    @DisplayName("Given an invalid username return 400 code")
    void getUserByUsername_ValidUsername_ReturnCode200() {
        setup(getUserByUsernameRequestSpec()
                .pathParam("username", "1"));

        MatcherAssert.assertThat(response.statusCode(), Matchers.is(400));
    }
}
