package unhappyPath.petEnpoint;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GetPetsByTagInvalidTest {

  private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";

  @Test
  @DisplayName("Invalid tag value returns 400")
  void invalidTagReturns400() {
    Response response = RestAssured
      .given()
      .baseUri(BASE_URI)
      .basePath("/pet/findByTags")
      .queryParam("tags", -2)
      .accept("application/json")
      .when()
      .get()
      .then()
      .extract().response();

    MatcherAssert.assertThat(response.statusCode(), Matchers.is(400));
  }

  @Test
  @DisplayName("Empty tag parameter returns 400")
  void emptyTagReturns400() {
    Response response = RestAssured
      .given()
      .baseUri(BASE_URI)
      .basePath("/pet/findByTags")
      .queryParam("tags", "")
      .accept("application/json")
      .when()
      .get()
      .then()
      .extract().response();

    MatcherAssert.assertThat(response.statusCode(), Matchers.is(400));
  }

  @Test
  @DisplayName("Missing tag parameter returns unexpected error (default)")
  void missingTagReturnsUnexpectedError() {
    Response response = RestAssured
      .given()
      .baseUri(BASE_URI)
      .basePath("/pet/findByTags")
      .accept("application/json")
      .when()
      .get()
      .then()
      .extract().response();

    MatcherAssert.assertThat(response.statusCode(), Matchers.not(200));
  }
}
