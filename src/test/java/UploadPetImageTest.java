import Utils.UploadPetImageRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UploadPetImageTest {
  private static HttpResponse<String> response;
  private static JsonNode jsonBody;

  @BeforeAll
  static void setup() {
    try {
      HttpClient client = HttpClient.newHttpClient();

      HttpRequest request = UploadPetImageRequest.uploadImageRequestSuccess();

      response = client.send(request, HttpResponse.BodyHandlers.ofString());

      System.out.println("Status: " + response.statusCode());
      System.out.println("Body: " + response.body());

      ObjectMapper mapper = new ObjectMapper();
      jsonBody = mapper.readTree(response.body());
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
    MatcherAssert.assertThat(jsonBody.get("id").asInt(), Matchers.is(10));
  }

  @Test
  @DisplayName("Response contains non-empty photoUrls")
  void uploadImageByPetId_ResponseContainsPhotoUrls() {
    MatcherAssert.assertThat(
      jsonBody.get("photoUrls").isArray() && !jsonBody.get("photoUrls").isEmpty(),
      Matchers.is(true)
    );
  }

  @Test
  @DisplayName("Response contains tag name playful")
  void uploadImageByPetId_ResponseContainsTagPlayful() {
    MatcherAssert.assertThat(jsonBody.get("tags").get(0).get("name").asText(), Matchers.is(""));
  }


  @Test
  @DisplayName("Response contains non-empty photoUrls")
  void uploadImageByPetId_ResponseContainsStatusAvailable() {
    MatcherAssert.assertThat(jsonBody.get("status").asText(), Matchers.is("available")
    );
  }

}
