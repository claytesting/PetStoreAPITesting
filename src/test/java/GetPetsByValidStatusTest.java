import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.PetShopAPI;

import static org.hamcrest.Matchers.*;

public class GetPetsByValidStatusTest {
    private static Response response;

    @Test
    @DisplayName("Get available status pets to return 200 and correct available status field")
    void getAvailablePets_Returns200AndStatusField() {
        response = PetShopAPI.getPetsByStatus("available");
        MatcherAssert.assertThat(response.statusCode(), is(200));
    }

    @Test
    @DisplayName("Get request for pending status pets return 200 and correct status fields")
    void getPendingPets_Returns200AndStatusField() {
        response = PetShopAPI.getPetsByStatus("pending");
        MatcherAssert.assertThat(response.statusCode(), is(200));
    }

    @Test
    @DisplayName("Get request for sold status pets return 200 and correct status fields")
    void getSoldPets_Returns200AndStatusField() {
        response = PetShopAPI.getPetsByStatus("sold");
        MatcherAssert.assertThat(response.statusCode(), is(200));
    }
}
