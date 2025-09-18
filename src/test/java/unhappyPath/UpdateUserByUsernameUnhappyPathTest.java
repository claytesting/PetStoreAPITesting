package unhappyPath;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static utils.PetShopAPI.updateUserByUsernameRequestSpec;

public class UpdateUserByUsernameUnhappyPathTest {
    private static Response response;

    static void setup(String bodyString, String username) {

        response = RestAssured
                .given(updateUserByUsernameRequestSpec(username))
                .body(bodyString)
                .when()
                .put()

                .then()
                .extract().response();

    }

    @Test
    @DisplayName("Given a valid username but invalid JSON body return 400 status code")
    void updateUserByUsername_GivenInValidJson_ReturnCode400() {
        setup("""
                        {
                          "id": "hello",
                          "username": "theUser",
                          "firstName": 1234,
                          "lastName": 1423,
                          "email": 1243,
                          "password": 12341,
                          "phone": 241234,
                          "userStatus": "hello"
                        }
                        """, "theUser");
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(400));
    }

    @Test
    @DisplayName("Given an invalid username and valid JSON body return 404 status code")
    void updateUserByUsername_GivenInValidUsername_ReturnCode404() {
        setup("""
                        {
                          "id": 10,
                          "username": "usercoolmccoolface",
                          "firstName": "John",
                          "lastName": "James",
                          "email": "john@email.com",
                          "password": "12345",
                          "phone": "12345",
                          "userStatus": 1
                        }
                        """, "usercoolmccoolface");
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(400));
    }
}
