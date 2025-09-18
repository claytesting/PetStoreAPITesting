import Utils.UploadPetImageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojos.ApiError;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static utils.PetShopAPI.setupPetForQuery;

public class UploadPetImageUnsupportedMediaTest {
  private static HttpResponse<String> response;
  private static ApiError errorResponse;

  @BeforeAll
  static void setup() {
    Integer petId = setupPetForQuery();
    try {
      HttpClient client = HttpClient.newHttpClient();

      HttpRequest request = UploadPetImageRequest.uploadImageRequestUnsupportedMediaType(petId);

      response = client.send(request, HttpResponse.BodyHandlers.ofString());

      System.out.println("Status: " + response.statusCode());
      System.out.println("Body: " + response.body());

      ObjectMapper mapper = new ObjectMapper();
      errorResponse = mapper.readValue(response.body(), ApiError.class);
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
  @DisplayName("Given unsupported media type return response message")
  void uploadImageByPetId_UnsupportedMedia_ReturnsMessage() {
    MatcherAssert.assertThat(errorResponse.getMessage(), Matchers.is("HTTP 415 Unsupported Media Type"));
  }

  @Test
  @DisplayName("Response contains correct error code")
  void uploadImageByPetId_ResponseContainsErrorCode() {
    MatcherAssert.assertThat(errorResponse.getCode(), Matchers.is(415));
  }
}
