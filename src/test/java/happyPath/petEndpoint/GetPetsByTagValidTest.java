package happyPath.petEndpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojos.Pet;
import pojos.TagsItem;

import java.util.List;

public class GetPetsByTagValidTest {

  private static Response response;
  private static List<Pet> pets;
  private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
  private static final String GET_BY_TAGS_PATH = "/pet/findByTags";

  @BeforeAll
  static void setup() {
    response = RestAssured
      .given()
      .baseUri(BASE_URI)
      .basePath("/pet/findByTags")
      .queryParam("tags", "tag2")
      .accept("application/json")
      .when()
      .get()
      .then()
      .extract().response();

    String body = response.asString();
    ObjectMapper mapper = new ObjectMapper();

    try {
      if (body.trim().startsWith("[")) {
        pets = mapper.readValue(body, new TypeReference<>() {
        });
      } else {
        Pet pet = mapper.readValue(body, Pet.class);
        pets = List.of(pet);
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse response: " + body, e);
    }
  }

  @Test
  @DisplayName("Response code is 200")
  void responseCodeIs200() {
    MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
  }

  @Test
  @DisplayName("Response contains non-empty pet list")
  void responseContainsNonEmptyPetList() {
    MatcherAssert.assertThat(pets.size(), Matchers.greaterThan(0));
  }

  @Test
  @DisplayName("Each pet has required fields")
  void eachPetHasRequiredFields() {
    pets.forEach(pet -> {
      MatcherAssert.assertThat(pet.getId(), Matchers.notNullValue());
      MatcherAssert.assertThat(pet.getCategory(), Matchers.notNullValue());
      MatcherAssert.assertThat(pet.getStatus(), Matchers.notNullValue());
    });
  }

  @Test
  @DisplayName("Each pet contains tag2 in tags list")
  void eachPetContainsTag2() {
    pets.forEach(pet -> {
      List<String> tagNames = pet.getTags().stream().map(TagsItem::getName).toList();
      MatcherAssert.assertThat(tagNames, Matchers.hasItem("tag2"));
    });
  }
}
