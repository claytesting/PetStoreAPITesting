package unhappyPath;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class UpdatePetByIdUnhappyTest {

    private static Response response;
    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
    private static final String POST_BY_ID_PATH = "/pet/{petId}";

    private static void postNewPet(RequestSpecification spec) {
        response = RestAssured
                .given(spec)
                .baseUri(BASE_URI)
                .basePath(POST_BY_ID_PATH)
                .headers(Map.of("Accept", "application/json"))

                .when()
                .post()

                .then()
                .log().all()
                .extract().response();
    }

    @Test
    @DisplayName("Given ID does not exist return code 404")
    void updatePetById_InvalidID_ReturnCode404() {
        postNewPet(new RequestSpecBuilder()
                .addPathParam("petId", "100000")
                .addQueryParam("name", "jason")
                .build());
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(404));
    }

    @Test
    @DisplayName("Given no status or name return code 400")
    void updatePetById_NoNameOrStatus_ReturnCode400() {
        postNewPet(new RequestSpecBuilder()
                .addPathParam("petId", "10")
                .build());
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(400));
    }

    @Test
    @DisplayName("Given invalid name return code 400")
    void updatePetById_InvalidName_ReturnCode400() {
        postNewPet(new RequestSpecBuilder()
                .addPathParam("petId", "10")
                .addQueryParam("name", 1234)
                .build());
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(400));
    }

    @Test
    @DisplayName("Given status name return code 400")
    void updatePetById_InvalidStatus_ReturnCode400() {
        postNewPet(new RequestSpecBuilder()
                .addPathParam("petId", "10")
                .addQueryParams(Map.of(
                        "name", "jason",
                        "status", 400000000
                ))
                .build());
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(400));
    }


}
