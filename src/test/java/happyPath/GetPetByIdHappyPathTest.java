package happyPath;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import pojos.Pet;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static utils.PetShopAPI.*;

public class GetPetByIdHappyPathTest {

    private static Response response;
    private static Pet pet;
    private static int validPetId;

    private static final String NAME = "doggie";
    private static final String STATUS = "available";

    private static final int categoryId = 1;
    private static final String categoryName = "Dogs";

    private static final String photoUrl = "string";

    private static final int numOfTags = 1;
    private static final int firstTagId = 0;
    private static final String firstTagName = "string";

    @BeforeEach
    void setup() {
        validPetId = setupPetForQuery();
        response = getPetById(validPetId);
        pet = response.as(Pet.class);
    }

    @Test
    @DisplayName("Get pet with a valid Id returns a pet with that Id")
    void getPetWithId_ReturnsThatPet() {
        MatcherAssert.assertThat(pet.getId(), is(validPetId));
    }

    @Test
    @DisplayName("Get pet with a specific Id and check the status code")
    void getPetWithId_ChecksStatusCode() {
        MatcherAssert.assertThat(response.statusCode(), is(200));
    }

    @Test
    @DisplayName("Validate the name in the response")
    void getPetWithId_validateResponseName() {
        MatcherAssert.assertThat(pet.getName(), Matchers.containsString(NAME));
    }

    @Test
    @DisplayName("Validate the category.id in the response")
    void getPetWithId_validateResponseCategoryId() {
        MatcherAssert.assertThat(pet.getCategory().getId(), is(categoryId));
    }

    @Test
    @DisplayName("Validate the category.name in the response")
    void getPetWithId_validateResponseCategoryName() {
        MatcherAssert.assertThat(pet.getCategory().getName(), is(categoryName));
    }

    @Test
    @DisplayName("Validate photoUrls in the response")
    void getPetWithId_validateResponsePhotoUrls() {
        MatcherAssert.assertThat(pet.getPhotoUrls(), hasItem(photoUrl));
    }

    @Test
    @DisplayName("Validate the number of tags in the response")
    void getPetWithId_validateResponseNumberOfTags() {
        MatcherAssert.assertThat(pet.getTags().size(), is(numOfTags));
    }

    @Test
    @DisplayName("Validate the id of the first tag in the response")
    void getPetWithId_validateResponseTagsId() {
        MatcherAssert.assertThat("first tag id", pet.getTags().get(0).getId(), is(firstTagId));
    }

    @Test
    @DisplayName("Validate the name of the first tag in the response")
    void getPetWithId_validateResponseTagsName() {
        MatcherAssert.assertThat(pet.getTags().get(0).getName(), is(firstTagName));
    }

    @Test @DisplayName("Validate the status in the response")
    void getPetWithId_validateResponseStatus() {
        MatcherAssert.assertThat(pet.getStatus(), Matchers.containsString(STATUS));
    }
}
