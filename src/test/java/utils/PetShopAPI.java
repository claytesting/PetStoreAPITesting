package utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class PetShopAPI {
    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
    private static final String POST_BY_ID_PATH = "/pet/{petId}";
    private static final String POST_PET = "/pet";
    private static final String GET_PET_PATH = "/pet/{petId}";
    private static final String UPDATE_USER_PATH = "/user/{username}";


    public static RequestSpecBuilder defaultRequestSpec(String path) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setBasePath(path);
    }

    public static RequestSpecification updatePetByIdRequestSpec(Integer petId) {
        return defaultRequestSpec(POST_BY_ID_PATH)
                .addHeaders(Map.of("Accept", "application/json"))
                .addPathParam("petId", petId.toString())
                .build();
    }

    public static RequestSpecification updatePetByJSONRequestSpec() {
        return defaultRequestSpec(POST_PET)
                .addHeaders(Map.of("Accept", "application/json"))
                .setContentType("application/json")
                .build();
    }

    public static RequestSpecification getPetByIdRequestSpec(Object petId) {
        return defaultRequestSpec(GET_PET_PATH)
                .addPathParams(Map.of(
                        "petId", petId
                ))
                .addHeaders(Map.of(
                        "Accept", "application/json"
                ))
                .build();
    }

    public static RequestSpecification updateUserByUsernameRequestSpec(Object username) {
        return defaultRequestSpec(UPDATE_USER_PATH)
                .addPathParams(Map.of(
                        "username", username
                ))
                .addHeaders(Map.of(
                        "Accept", "application/json"
                ))
                .setContentType("application/json")
                .build();
    }



    public static Response getPetById(Object petId) {
        return RestAssured
                .given().spec(getPetByIdRequestSpec(petId))

                .when()
                .log().all()
                .get()
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static Integer setupPetForQuery() {
        Response newPet = RestAssured
                .given(updatePetByJSONRequestSpec())
                .contentType("application/json")
                .body("""
                        {
                          "id": 7041,
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
                        }
                        """)
                .when()
                .post()
                .then()
                .log().all()
                .extract().response();

        return newPet.jsonPath().getInt("id");
    }
}
