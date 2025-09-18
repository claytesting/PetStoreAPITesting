package unhappyPath.petEndpoint;

import utils.UploadPetImageRequest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UploadPetImageInvalidIdTest {
  private static HttpResponse<String> response;

  @BeforeAll
  static void setup() {
    try {
      HttpClient client = HttpClient.newHttpClient();

      HttpRequest request = UploadPetImageRequest.uploadImageRequestInvalidId();

      response = client.send(request, HttpResponse.BodyHandlers.ofString());

      System.out.println("Status: " + response.statusCode());
      System.out.println("Body: " + response.body());
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Test
  @DisplayName("Given invalid Id return response code 404")
  void uploadImageByPetId_NoFileData_ReturnCode404() {
    MatcherAssert.assertThat(response.statusCode(), Matchers.is(404));
  }

}
