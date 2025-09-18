package unhappyPath.petEnpoint;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojos.ApiError;

import java.util.Map;

public class PostAPetInvalidTest {

  private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
  private static final String POST_PET_PATH = "/pet";

  @Test
  @DisplayName("Unhappy Path: Empty body returns 500 status code")
  void postPet_EmptyBody_Returns400() {
    Response response = RestAssured
      .given()
      .baseUri(BASE_URI)
      .basePath(POST_PET_PATH)
      .headers(Map.of("Accept", "application/json",
        "Content-Type", "application/json"))
      .body("{}")
      .when()
      .post()
      .then()
      .extract().response();

    MatcherAssert.assertThat(response.statusCode(), Matchers.is(500));
  }

  //Missing validation
  @Test
  @DisplayName("Unhappy Path: Missing required fields returns 422 Validation exception")
  void postPet_MissingName_Returns422() {
    String invalidBody = """
      {
        "id": 15,
        "category": {"id": 1, "name": "Dogs"},
        "photoUrls": null,
      }
      """;

    Response response = RestAssured
      .given()
      .baseUri(BASE_URI)
      .basePath(POST_PET_PATH)
      .headers(Map.of("Accept", "application/json",
        "Content-Type", "application/json"))
      .body(invalidBody)
      .when()
      .post()
      .then()
      .extract().response();

    MatcherAssert.assertThat(response.statusCode(), Matchers.is(422));

    ApiError error = response.as(ApiError.class);
    MatcherAssert.assertThat(error.getMessage(), Matchers.containsString("Validation exception"));
  }

  @Test
  @DisplayName("Unhappy Path: Missing status field returns 400")
  void postPet_MissingStatusField_Returns400() {
    String invalidBody = """
      {
        "id": 15,
        "category": {"id": 1, "name": "Dogs"},
        "photoUrls": null,
      }
      """;

    Response response = RestAssured
      .given()
      .baseUri(BASE_URI)
      .basePath(POST_PET_PATH)
      .headers(Map.of("Accept", "application/json",
        "Content-Type", "application/json"))
      .body(invalidBody)
      .when()
      .post()
      .then()
      .extract().response();

    MatcherAssert.assertThat(response.statusCode(), Matchers.is(400));
  }
}
