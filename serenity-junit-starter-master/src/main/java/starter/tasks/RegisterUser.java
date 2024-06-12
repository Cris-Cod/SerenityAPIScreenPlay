package starter.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.serenitybdd.screenplay.rest.interactions.RestInteraction;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RegisterUser implements Task {

    private final Object userInfo;

    public RegisterUser(Object userInfo) {
        this.userInfo = userInfo;
    }

    public static Performable withInfo(Object userInfo){
        return instrumented(RegisterUser.class, userInfo);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("api/register").with(requestSpecification ->
                        requestSpecification
                                .log().all()
                                .contentType(ContentType.JSON)
                                .body(userInfo))
        );
        SerenityRest.lastResponse().then().log().all();
    }
}
