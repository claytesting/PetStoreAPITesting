import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojos.Pet;

import java.util.Map;

public class PostAPetTest {

  private static Response response;
  private static Pet petResponse;
  private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
  private static final String POST_PET_PATH = "/pet";

  private static final String VALID_REQUEST_BODY = """
    {
      "id": 14,
      "name": "Benny",
      "category": {
        "id": 2,
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
    """;

  @BeforeAll
  static void setup() {
    response = RestAssured
      .given()
      .baseUri(BASE_URI)
      .basePath(POST_PET_PATH)
      .headers(Map.of("Accept", "application/json",
        "Content-Type", "application/json"))
      .body(VALID_REQUEST_BODY)
      .when()
      .post()
      .then()
      .extract().response();

    petResponse = response.as(Pet.class);
  }

  @Test
  @DisplayName("Response code is 200")
  void responseCodeIs200() {
    MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
  }

  @Test
  @DisplayName("Response contains correct pet ID")
  void responseContainsCorrectPetId() {
    MatcherAssert.assertThat(petResponse.getId(), Matchers.is(14));
  }

  @Test
  @DisplayName("Response contains correct name")
  void responseContainsCorrectName() {
    MatcherAssert.assertThat(petResponse.getName(), Matchers.is("Benny"));
  }

  @Test
  @DisplayName("Response contains correct category")
  void responseContainsCorrectCategory() {
    MatcherAssert.assertThat(petResponse.getCategory().getId(), Matchers.is(2));
    MatcherAssert.assertThat(petResponse.getCategory().getName(), Matchers.is("Dogs"));
  }

  @Test
  @DisplayName("Response contains correct photoUrls")
  void responseContainsCorrectPhotoUrls() {
    MatcherAssert.assertThat(petResponse.getPhotoUrls(), Matchers.hasItem("string"));
  }

  @Test
  @DisplayName("Response contains correct tags")
  void responseContainsCorrectTags() {
    MatcherAssert.assertThat(petResponse.getTags().get(0).getId(), Matchers.is(0));
    MatcherAssert.assertThat(petResponse.getTags().get(0).getName(), Matchers.is("string"));
  }

  @Test
  @DisplayName("Response contains correct status")
  void responseContainsCorrectStatus() {
    MatcherAssert.assertThat(petResponse.getStatus(), Matchers.is("available"));
  }
}
