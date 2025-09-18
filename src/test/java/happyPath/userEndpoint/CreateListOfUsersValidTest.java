package happyPath.userEndpoint;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojos.User;
import utils.PetShopAPI;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CreateListOfUsersValidTest {

    @Test
    @DisplayName("Post user list with valid JSON array returns 200")
    void postValidUserList_Returns200() {

        User user1 = new User();
        user1.setId(5267);
        user1.setUsername("toasting123");
        user1.setFirstName("Toast");
        user1.setLastName("Bread");
        user1.setEmail("toastingbread@bread.com");
        user1.setPassword("ToasterForBr3ad");
        user1.setPhone("20567436");
        user1.setUserStatus(1);

        User user2 = new User();
        user2.setId(2542);
        user2.setUsername("croissant321");
        user2.setFirstName("Croissant");
        user2.setLastName("de Chocolat");
        user2.setEmail("croissantde@chocolat.com");
        user2.setPassword("Croissant!3920");
        user2.setPhone("02850743853454");
        user2.setUserStatus(1);

        User user3 = new User();
        user3.setId(25034);
        user3.setUsername("berriees3243");
        user3.setFirstName("Straw");
        user3.setLastName("Berry");
        user3.setEmail("strawberry@fruitsmail.com");
        user3.setPassword("S*raw*Bry2");
        user3.setPhone("250346502786");
        user3.setUserStatus(1);

        List<User> userList = List.of(user1, user2, user3);

        Response response = PetShopAPI.createUsersList(userList);
        assertThat(response.statusCode(), is(200));
    }
}
