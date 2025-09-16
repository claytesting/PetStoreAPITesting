package Utils;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.nio.file.Path;

public class UploadPetImageRequest {

  public static HttpRequest uploadImageRequestSuccess() throws URISyntaxException, FileNotFoundException {

    return HttpRequest.newBuilder()
      .uri(new URI("https://petstore3.swagger.io/api/v3/pet/10/uploadImage"))
      .header("accept", "application/json")
      .header("Content-Type", "application/octet-stream")
      .POST(HttpRequest.BodyPublishers.ofFile(Path.of("src/test/resources/test-image.png")))
      .build();
  }
}