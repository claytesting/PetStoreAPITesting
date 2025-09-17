package happyPath;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojos.Pet;

import static utils.PetShopAPI.updatePetByJSONRequestSpec;

public class UpdatePetByJsonHappyPathTest {

    private static Response response;
    private static Pet pet;

    @BeforeAll
    static void setup() {
        response = RestAssured
                .given(updatePetByJSONRequestSpec())
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
                .put()

                .then()
                .log().all()
                .extract().response();
        pet = response.as(Pet.class);

    }

    @Test
    @DisplayName("Given valid data return response code 200")
    void updatePetByJson_GivenValidData_ReturnCode200() {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Given valid data return valid return ID")
    void updatePetByJson_GivenValidData_ReturnJsonContainsCorrectID() {
        MatcherAssert.assertThat(pet.getId(), Matchers.is(7041));
    }

    @Test
    @DisplayName("Given valid data return valid return Name")
    void updatePetByJson_GivenValidData_ReturnJsonContainsCorrectName() {
        MatcherAssert.assertThat(pet.getName(), Matchers.is("doggie"));
    }

    @Test
    @DisplayName("Given valid data return valid return Status")
    void updatePetByJson_GivenValidData_ReturnJsonContainsCorrectStatus() {
        MatcherAssert.assertThat(pet.getStatus(), Matchers.is("available"));
    }

    @Test
    @DisplayName("Given valid data return valid return PhotoUrls")
    void updatePetByJson_GivenValidData_ReturnJsonContainsCorrectPhotoURLs() {
        MatcherAssert.assertThat(pet.getPhotoUrls().size(), Matchers.is(1));
    }

    @Test
    @DisplayName("Given valid data return valid return Categories")
    void updatePetByJson_GivenValidData_ReturnJsonContainsCorrectCategories() {
        MatcherAssert.assertThat(pet.getCategory().getId(), Matchers.is(1));
        MatcherAssert.assertThat(pet.getCategory().getName(), Matchers.is("Dogs"));
    }

    @Test
    @DisplayName("Given valid data return valid return Categories")
    void updatePetByJson_GivenValidData_ReturnJsonContainsCorrectTags() {
        MatcherAssert.assertThat(pet.getTags().getFirst().getId(), Matchers.is(0));
        MatcherAssert.assertThat(pet.getTags().getFirst().getName(), Matchers.is("string"));
        MatcherAssert.assertThat(pet.getTags().size(), Matchers.is(1));
    }


}


