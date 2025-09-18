package utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class PetShopAPI {
    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
    private static final String POST_PET = "/pet";

    private static final String GET_USER_LOGIN_PATH = "/user/login";
    private static final String GET_USER_LOGOUT_PATH = "/user/logout";

    private static final String GET_USER_PATH = "/user/{username}";
    private static final String USER_PATH = "/user";
    private static final String UPDATE_USER_PATH = "/user/{username}";
    private static final String PET_ID_PATH = "/pet/{petId}";



    public static final String validUsername = "theUser";
    public static final String validPassword = "12345";

    public static RequestSpecBuilder defaultRequestSpec(String path) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setBasePath(path);
    }

    public static RequestSpecification updatePetByIdRequestSpec(Integer petId) {
        return defaultRequestSpec(PET_ID_PATH)
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

    public static RequestSpecification getUserByUsernameRequestSpec() {
        return defaultRequestSpec(GET_USER_PATH)
                .addHeaders(Map.of("Accept", "application/json"))
                .build();
    }

    public static RequestSpecification getUserByUsernameNoPathParamRequestSpec() {
        return defaultRequestSpec(USER_PATH)
                .addHeaders(Map.of("Accept", "application/json"))
                .build();
    }

    public static RequestSpecification getPetByIdRequestSpec(Object petId) {
        return defaultRequestSpec(PET_ID_PATH)
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
                        "username", username))
                .addHeaders(Map.of(
                        "Accept", "application/json"
                ))

                .setContentType("application/json")

                .build();
    }

    public static RequestSpecification deletePetByIdRequestSpec(Object petId) {
        return defaultRequestSpec(PET_ID_PATH)
                .addPathParams(Map.of(
                        "petId", petId
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

    public static void getPetById_DefaultError() {
        String weirdId = "%00";
        Response r = RestAssured
                .given()
                .spec(getPetByIdRequestSpec(weirdId))
                .header("Accept", "application/invalid+type")
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

    public static RequestSpecification getUserLoginRequestSpec() {
        return defaultRequestSpec(GET_USER_LOGIN_PATH)
                .addHeaders(Map.of(
                        "Accept", "application/json"
                ))
                .build();
    }

    public static Response getUserLogin(String username, String password) {
        return RestAssured
                .given().spec(getUserLoginRequestSpec())
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .log().all()
                .get()
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static RequestSpecification getUserLogin_DefaultErrorSpec() {
        return defaultRequestSpec(GET_USER_LOGIN_PATH)
                .addHeaders(Map.of(
                        "Accept", "invalid+type"
                ))
                .build();
    }

    public static Response getUserLogin_DefaultError() {
        return RestAssured
                .given().spec(getUserLogin_DefaultErrorSpec())
                .queryParam("username", "%00")
                .queryParam("password", "%00")
                .when()
                .log().all()
                .get()
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static RequestSpecification getUserLogoutRequestSpec() {
        return defaultRequestSpec(GET_USER_LOGOUT_PATH)
                .addHeaders(Map.of(
                        "Accept", "application/json"
                ))
                .build();
    }

    public static Response getUserLogout() {
        return RestAssured
                .given().spec(getUserLogoutRequestSpec())
                .when()
                .get()
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static RequestSpecification getUserLogout_DefaultErrorSpec() {
        return defaultRequestSpec(GET_USER_LOGOUT_PATH)
                .addHeaders(Map.of(
                        "Accept", "invalid+type",
                        "q", "%%%"
                ))
                .build();
    }

    public static Response getUserLogout_DefaultError() {
        return RestAssured
                .given().spec(getUserLogout_DefaultErrorSpec())
                .when()
                .log().all()
                .get()
                .then()
                .log().all()
                .extract()
                .response();
    }

}
