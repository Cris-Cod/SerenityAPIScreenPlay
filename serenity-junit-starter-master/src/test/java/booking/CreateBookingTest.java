package booking;


import io.restassured.path.json.JsonPath;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import starter.models.booking.BookingDates;
import starter.models.booking.CreateBookingData;
import starter.questions.ResponseCode;
import starter.tasks.CreateBookingTask;
import starter.tasks.GetBookingTask;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom(value = "src/test/resources/createBooking.csv")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateBookingTest {

    private static final String baseUrl = "https://restful-booker.herokuapp.com/";
    private static int id;
    private static String firstname;
    private static String lastaname;
    private static int totalprice;
    private static boolean depositpaid;
    private static String checkin;
    private static String checkout;
    private static String additionalneeds;


    @Test
    @Order(1)
    public void CreateBookingTest(){


        CreateBookingData bookingData = new CreateBookingData();
        bookingData.setFirstname(firstname);
        bookingData.setLastname(lastaname);
        bookingData.setTotalprice(totalprice);
        bookingData.setDepositpaid(depositpaid);

        BookingDates dates = new BookingDates();
        dates.setCheckin(checkin);
        dates.setCheckout(checkout);
        bookingData.setBookingdates(dates);

        bookingData.setAdditionalneeds(additionalneeds);

        Actor user = Actor.named("user")
                .whoCan(CallAnApi.at(baseUrl));

        user.attemptsTo(
                CreateBookingTask.data(bookingData)
        );

        user.should(
                seeThat("El codigo de respuesta", ResponseCode.was(), equalTo(200))
        );
        String response = SerenityRest.lastResponse().asString();
        System.out.println(response);
        JsonPath js = new JsonPath(response);
        String name = js.get("booking.firstname");
        id = js.get("bookingid");
        System.out.println(name);
        System.out.println(id);
        Assert.assertEquals(name, bookingData.getFirstname());
    }

    @Test
    @Order(2)
    public void getBookingTest(){
        Actor user = Actor.named("user")
                .whoCan(CallAnApi.at(baseUrl));

        user.attemptsTo(
                GetBookingTask.idBooking(id)
        );

        user.should(
                seeThat("El codigo de respuesta", ResponseCode.was(), equalTo(200))
        );
    }

}
