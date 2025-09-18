package happyPath.userEndpoint;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class CreateListOfUsersValidTest {
    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
    private static Response response;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    private Response postCreateListOfUserRequest(String body) {
        return RestAssured
                .given()
                .basePath("/user/createWithList")
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post()
                .then()
                .extract().response();
    }

    @Test
    @DisplayName("Post user list with valid JSON array returns 200")
    void postValidUserList_Returns200() {
        String accountList = """
            [
              {
                "id": 5267,
                "username": "toasting123",
                "firstName": "Toast",
                "lastName": "Bread",
                "email": "toastingbread@bread.com",
                "password": "ToasterForBr3ad",
                "phone": "20567436",
                "userStatus": 1
              },
              {
                "id": 2542,
                "username": "croissant321",
                "firstName": "Croissant",
                "lastName": "de Chocolat",
                "email": "croissantde@chocolat.com",
                "password": "Croissant!3920",
                "phone": "02850743853454",
                "userStatus": 1
              },
              {
                "id": 25034,
                "username": "berriees3243",
                "firstName": "Straw",
                "lastName": "Berry",
                "email": "strawberry@fruitsmail.com",
                "password": "S*raw*Bry2",
                "phone": "250346502786",
                "userStatus": 1
              }
            ]
        """;
        response = postCreateListOfUserRequest(accountList);
        assertThat(response.statusCode(), is(200));
    }
}
