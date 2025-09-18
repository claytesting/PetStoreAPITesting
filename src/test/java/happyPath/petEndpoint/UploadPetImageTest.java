package happyPath.petEndpoint;

import Utils.UploadPetImageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojos.Pet;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static utils.PetShopAPI.setupPetForQuery;

public class UploadPetImageTest {
  private static HttpResponse<String> response;
  private static Pet petResponse;

  @BeforeAll
  static void setup() {
    Integer petId = setupPetForQuery();
    try {
      HttpClient client = HttpClient.newHttpClient();

      HttpRequest request = UploadPetImageRequest.uploadImageRequestSuccess(petId);

      response = client.send(request, HttpResponse.BodyHandlers.ofString());

      System.out.println("Status: " + response.statusCode());
      System.out.println("Body: " + response.body());

      ObjectMapper mapper = new ObjectMapper();
      petResponse = mapper.readValue(response.body(), Pet.class);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Test
  @DisplayName("Given valid data return response code 200")
  void uploadImageByPetId_ValidData_ReturnCode200() {
    MatcherAssert.assertThat(response.statusCode(), Matchers.is(200));
  }

  @Test
  @DisplayName("Response contains correct pet ID")
  void uploadImageByPetId_ResponseContainsPetId() {
    MatcherAssert.assertThat(petResponse.getId(), Matchers.is(7041));
  }

  @Test
  @DisplayName("Response contains non-empty photoUrls")
  void uploadImageByPetId_ResponseContainsPhotoUrls() {
    MatcherAssert.assertThat(
      petResponse.getPhotoUrls() != null && !petResponse.getPhotoUrls().isEmpty(), Matchers.is(true)
    );
  }

  @Test
  @DisplayName("Response contains tag name playful")
  void uploadImageByPetId_ResponseContainsTagPlayful() {
    MatcherAssert.assertThat(petResponse.getTags().getFirst().getName(), Matchers.is("string"));
  }


  @Test
  @DisplayName("Response contains non-empty photoUrls")
  void uploadImageByPetId_ResponseContainsStatusAvailable() {
    MatcherAssert.assertThat(petResponse.getStatus(), Matchers.is("available")
    );
  }
}
