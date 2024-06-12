package Sreenplay;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import starter.models.users.Datum;
import starter.models.users.RegisterUserInfo;
import starter.questions.GetUsersQuestions;
import starter.questions.ResponseCode;
import starter.tasks.GetUsers;
import starter.tasks.RegisterUser;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SerenityJUnit5Extension.class)
public class SerenityInitialTest {

    private static final String restUrl = "https://reqres.in/";
    @Test
    public void getUsers(){
        Actor usuario = Actor.named("usurio")
                .whoCan(CallAnApi.at(restUrl));

        usuario.attemptsTo(
                GetUsers.fromPage(1)
        );

        //assertThat(SerenityRest.lastResponse().statusCode()).isEqualTo(200);
        usuario.should(
                seeThat("El codigo de respuesta", ResponseCode.was(), equalTo(200))
        );

        Datum user = new GetUsersQuestions().answeredBy(usuario).getData().stream().filter(x -> x.getId() == 1
        ).findFirst().orElse(null);

        usuario.should(
                seeThat("usuario no es nulo", actor -> user, notNullValue())
        );

        usuario.should(
                seeThat("el email del usuario", actor -> user.getEmail(), equalTo("george.bluth@reqres.in")),
                seeThat("el avatar del usuario", actor -> user.getAvatar(), equalTo("https://reqres.in/img/faces/1-image.jpg"))
        );

    }

    @Test
    public void registerUsersTest(){
        Actor usuario = Actor.named("usurio")
                .whoCan(CallAnApi.at(restUrl));

        String registerUsuario = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"pistol\"\n" +
                "}";

        usuario.attemptsTo(
                RegisterUser.withInfo(registerUsuario)
        );

        //assertThat(SerenityRest.lastResponse().statusCode()).isEqualTo(200);
        usuario.should(
                seeThat("El codigo de respuesta", ResponseCode.was(), equalTo(200))
        );


    }

    @Test
    public void registerUsersTestModels(){
        Actor usuario = Actor.named("usurio")
                .whoCan(CallAnApi.at(restUrl));

        RegisterUserInfo registerUserInfo = new RegisterUserInfo();
        registerUserInfo.setEmail("eve.holt@reqres.in");
        registerUserInfo.setPassword("pistol");

        usuario.attemptsTo(
                RegisterUser.withInfo(registerUserInfo)
        );

        //assertThat(SerenityRest.lastResponse().statusCode()).isEqualTo(200);
        usuario.should(
                seeThat("El codigo de respuesta", ResponseCode.was(), equalTo(200))
        );


    }

}
