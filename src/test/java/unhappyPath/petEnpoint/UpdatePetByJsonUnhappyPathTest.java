package unhappyPath.petEnpoint;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static utils.PetShopAPI.updatePetByJSONRequestSpec;

public class UpdatePetByJsonUnhappyPathTest {
    private static Response response;

    static void setup(String bodyString) {
        response = RestAssured
                .given(updatePetByJSONRequestSpec())
                .body(bodyString)
                .when()
                .put()

                .then()
                .extract().response();
    }

    @Test
    @DisplayName("Given an ID that doesn't exist return a 404 code")
    void updatePetByJson_GivenIdDoesNotExist_ReturnCode404() {
        setup("""
                {
                    "id": 100000000000,
                    "name": "doggie",
                    "category": {
                        "id": 1,
                        "name": "Dogs"
                    },
                    "photoUrls": [
                        "string"
                    ],
                    "tags": [
                        {
                            "id": 0,
                            "name": "string"
                        }
                    ],
                    "status": "available"
                }""");
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(404));
    }

    @Test
    @DisplayName("Given an invalid ID return a 400 code")
    void updatePetByJson_GivenInvalidID_ReturnCode400() {
        setup("""
                {
                    "id": "hello",
                    "name": "doggie",
                    "category": {
                        "id": 1,
                        "name": "Dogs"
                    },
                    "photoUrls": [
                        "string"
                    ],
                    "tags": [
                        {
                            "id": 0,
                            "name": "string"
                        }
                    ],
                    "status": "available"
                }""");
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(400));
    }

    @Test
    @DisplayName("Given an empty JSON return a 400 code")
    void updatePetByJson_GivenEmptyJSON_ReturnCode400() {
        setup(" ");
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(400));
    }

    @Test
    @DisplayName("Given a JSON with incorrect data types return a 422 code")
    void updatePetByJson_GivenJsonWithIncorrectDataTypes_ReturnCode422() {
        setup("""
                {
                    "id": 7041,
                    "name": 1234,
                    "category": {
                        "id": "stringy",
                        "name": 1234
                    },
                    "photoUrls": [
                        "string"
                    ],
                    "tags": [
                        {
                            "id": "stringy",
                            "name": 21345
                        }
                    ],
                    "status": 21234
                }""");
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(422));
    }
}
