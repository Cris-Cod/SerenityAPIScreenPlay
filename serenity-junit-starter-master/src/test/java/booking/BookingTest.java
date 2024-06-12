package booking;


import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
public class BookingTest {

    private static final String baseUrl = "https://restful-booker.herokuapp.com/";

    @Test
    public void createTokenTest(){
        Actor user = Actor.named("user")
                .whoCan(CallAnApi.at(baseUrl));

        user.attemptsTo(

        );
    }
}
