package happyPath;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojos.Pet;
import pojos.User;

import static utils.PetShopAPI.updatePetByJSONRequestSpec;
import static utils.PetShopAPI.updateUserByUsernameRequestSpec;

public class UpdateUserByUsernameHappyPathTest {

    private static Response response;
    private static User user;

    @BeforeAll
    static void setup() {

        response = RestAssured
                .given(updateUserByUsernameRequestSpec("theUser"))
                .body("""
                        {
                          "id": 10,
                          "username": "theUser",
                          "firstName": "John",
                          "lastName": "James",
                          "email": "john@email.com",
                          "password": "12345",
                          "phone": "12345",
                          "userStatus": 1
                        }
                        """)
                .when()
                .put()

                .then()
                .log().all()
                .extract().response();
//        user = response.as(User.class);

    }

    @Test
    @DisplayName("Given a valid user name and JSON body return 200 status code")
    void updateUserByUsername_GivenValidData_ReturnCode200() {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Given a valid user name and JSON body return correct id")
    void updateUserByUsername_GivenValidData_ReturnCorrectID() {
        MatcherAssert.assertThat(response.jsonPath().getInt("id"), Matchers.is(10));
    }

    @Test
    @DisplayName("Given a valid user name and JSON body return correct username")
    void updateUserByUsername_GivenValidData_ReturnCorrectUsername() {
        MatcherAssert.assertThat(response.jsonPath().getString("username"), Matchers.is("theUser"));
    }

    @Test
    @DisplayName("Given a valid user name and JSON body return correct first name")
    void updateUserByUsername_GivenValidData_ReturnCorrectFirstName() {
        MatcherAssert.assertThat(response.jsonPath().getString("firstName"), Matchers.is("John"));
    }

    @Test
    @DisplayName("Given a valid user name and JSON body return correct LastName")
    void updateUserByUsername_GivenValidData_ReturnCorrectLastName() {
        MatcherAssert.assertThat(response.jsonPath().getString("lastName"), Matchers.is("James"));
    }

    @Test
    @DisplayName("Given a valid user name and JSON body return correct email")
    void updateUserByUsername_GivenValidData_ReturnCorrectEmail() {
        MatcherAssert.assertThat(response.jsonPath().getString("email"), Matchers.is("john@email.com"));
    }

    @Test
    @DisplayName("Given a valid user name and JSON body return correct password")
    void updateUserByUsername_GivenValidData_ReturnCorrectPassword() {
        MatcherAssert.assertThat(response.jsonPath().getString("password"), Matchers.is("XXXXXXXXXXX"));
    }

    @Test
    @DisplayName("Given a valid user name and JSON body return correct phone number")
    void updateUserByUsername_GivenValidData_ReturnCorrectPhoneNumber() {
        MatcherAssert.assertThat(response.jsonPath().getString("phone"), Matchers.is("12345"));
    }

    @Test
    @DisplayName("Given a valid user name and JSON body return correct status")
    void updateUserByUsername_GivenValidData_ReturnCorrectUserStatus() {
        MatcherAssert.assertThat(response.jsonPath().getInt("userStatus"), Matchers.is(1));
    }


}
