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

import static utils.PetShopAPI.setupPetForQuery;

public class UploadPetImageNoFileTest {
  private static HttpResponse<String> response;

  @BeforeAll
  static void setup() {
    Integer petId = setupPetForQuery();
    try {
      HttpClient client = HttpClient.newHttpClient();

      HttpRequest request = UploadPetImageRequest.uploadImageRequestWithoutFile(petId);

      response = client.send(request, HttpResponse.BodyHandlers.ofString());

      System.out.println("Status: " + response.statusCode());
      System.out.println("Body: " + response.body());
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Test
  @DisplayName("Given no file return response code 400")
  void uploadImageByPetId_NoFileData_ReturnCode400() {
    MatcherAssert.assertThat(response.statusCode(), Matchers.is(400));
  }

}