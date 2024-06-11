package Sreenplay;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import org.junit.Test;
import org.junit.runner.RunWith;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import starter.questions.ResponseCode;
import starter.tasks.GetUsers;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SerenityRunner.class)
public class SerenityInitialTest {

    private static final String restUrl = "https://reqres.in/";
    @Test
    public void getUsers(){
        Actor user = Actor.named("User")
                .whoCan(CallAnApi.at(restUrl));

        user.attemptsTo(
                GetUsers.fromPage(2)
        );

        //assertThat(SerenityRest.lastResponse().statusCode()).isEqualTo(200);
        user.should(
                seeThat("El codigo de respuesta", ResponseCode.was(), equalTo(200))
        );
    }

}
