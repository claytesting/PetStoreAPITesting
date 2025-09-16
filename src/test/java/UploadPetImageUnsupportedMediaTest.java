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

public class UploadPetImageUnsupportedMediaTest {
  private static HttpResponse<String> response;
  private static JsonNode jsonBody;

  @BeforeAll
  static void setup() {
    try {
      HttpClient client = HttpClient.newHttpClient();

      HttpRequest request = UploadPetImageRequest.uploadImageRequestUnsupportedMediaType();

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
  @DisplayName("Given unsupported media type return response code 415")
  void uploadImageByPetId_UnsupportedMedia_ReturnCode415() {
    MatcherAssert.assertThat(response.statusCode(), Matchers.is(415));
  }

  @Test
  @DisplayName("Response contains correct message")
  void uploadImageByPetId_ResponseContainsErrorMessage() {
    MatcherAssert.assertThat(jsonBody.get("message").asText(), Matchers.is("HTTP 415 Unsupported Media Type"));
  }
}