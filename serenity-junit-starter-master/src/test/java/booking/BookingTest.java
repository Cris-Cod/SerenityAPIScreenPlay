package booking;


import io.restassured.path.json.JsonPath;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import starter.models.booking.CreateTokenData;
import starter.questions.ResponseCode;
import starter.tasks.CreateTokenBooking;



import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SerenityJUnit5Extension.class)
public class BookingTest {

    private static final String baseUrl = "https://restful-booker.herokuapp.com/";
    private static String token;



    @Test
    public void createTokenTest(){

        Actor user = Actor.named("user")
                .whoCan(CallAnApi.at(baseUrl));

        CreateTokenData dataInfo = new CreateTokenData();
        dataInfo.setUsername("admin");
        dataInfo.setPassword("password123");


        user.attemptsTo(
                CreateTokenBooking.data(dataInfo)
        );

        user.should(
                seeThat("El codigo de respuesta", ResponseCode.was(), equalTo(200))
        );
        String response = SerenityRest.lastResponse().asString();
        JsonPath js = new JsonPath(response);
        token = js.get("token");
        System.out.println(token);
    }



}
