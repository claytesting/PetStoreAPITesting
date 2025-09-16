package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class PetShopAPI {
    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";
    private static final String POST_BY_ID_PATH = "/pet/{petId}";

    public static RequestSpecBuilder defaultRequestSpec(String path) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setBasePath(path);
    }

    public static RequestSpecification updatePetByIdRequestSpec() {
        return defaultRequestSpec(POST_BY_ID_PATH)
                .addHeaders(Map.of("Accept", "application/json"))
                .addPathParam("petId", "10")
                .build();
    }
}
