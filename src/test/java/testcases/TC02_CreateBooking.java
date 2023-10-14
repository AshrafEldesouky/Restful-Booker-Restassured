package testcases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.GetCreateBookingBody;
import org.json.JSONObject;
import org.testng.annotations.Test;
import pojo.Booking;
import pojo.Bookingdates;
import utilities.Utility;

import java.io.File;
import java.util.Map;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static model.GetCreateBookingBody.getCreateBookingBody;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static utilities.Utility.*;
import static utilities.Utility.generateRandomLastName;
import io.restassured.module.jsv.JsonSchemaValidator;

public class TC02_CreateBooking extends TestBase{

    String firstName= Utility.getExcelData(1,0,"Sheet1");
    String lastName=generateRandomLastName();
    int totalNumber=111;
    boolean depositeID=true;
    String checkIn="2020-11-11";
    String checkOut="20020-11-11";
    String addtionalNeeds="breakfast";
   @Test(priority = 1,description = "Create Booking With Valid Data")
    public void createBookingWithValidData_P() throws JsonProcessingException {
       // use pojo class method
       Booking booking=new Booking();
       Bookingdates bookingdates=new Bookingdates();

       bookingdates.setCheckin(checkIn);
       bookingdates.setCheckout(checkOut);

       booking.setFirstname(firstName);
       booking.setLastname(lastName);
       booking.setDepositpaid(depositeID);
       booking.setTotalprice(totalNumber);
       booking.setAdditionalneeds(addtionalNeeds);
       booking.setBookingdates(bookingdates);

       ObjectMapper object=new ObjectMapper();

       /// use jsonobject
       JSONObject bookingData = new JSONObject();
       JSONObject bookingDates = new JSONObject();


       bookingData.put("firstname",firstName);
       bookingData.put("lastname", generateRandomLastName());
       bookingData.put("totalprice", generateRandomTotalPrice());
       bookingData.put("depositpaid", generateRandomDepositPaid());
       bookingData.put("bookingdates", bookingDates);
       bookingData.put("additionalneeds", generateRandomadditionalneeds());

      Map<String, LocalDate> RandombookingDates_Map = generateRandomBookingDates();
       bookingDates.put("checkin", RandombookingDates_Map.get("Random_checkinDate"));
       bookingDates.put("checkout", RandombookingDates_Map.get("Random_checkoutDate"));

       Response response = given().log().all()
//               .header("","")
               .filter(new AllureRestAssured())
               .contentType(ContentType.JSON)
//               .body(bookingData.toString())
//               .body(getCreateBookingBody(firstName,lastName,totalNumber,depositeID,checkIn,checkOut,addtionalNeeds))
               .body(object.writeValueAsString(booking))
               .when().post("/booking")
               .then()
               .assertThat()
               .body(JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir")+"/target/classes/createbookingschema.json")))
               .log().all().statusCode(200).extract().response();

       Response_bookingID = response.jsonPath().getInt("bookingid");
       System.out.println("Response_bookingID:"+Response_bookingID);
   }
    @Test(priority = 2, description = "Check schema validation ")
    public void checkResponseHasTokenKey() {
        JSONObject bookingData = new JSONObject();
        JSONObject bookingDates = new JSONObject();

        bookingData.put("firstname",generateRandomFirstName());
        bookingData.put("lastname", generateRandomLastName());
        bookingData.put("totalprice", generateRandomTotalPrice());
        bookingData.put("depositpaid", generateRandomDepositPaid());
        bookingData.put("bookingdates", bookingDates);
        bookingData.put("additionalneeds", generateRandomadditionalneeds());

        Map<String, LocalDate> RandombookingDates_Map = generateRandomBookingDates();
        bookingDates.put("checkin", RandombookingDates_Map.get("Random_checkinDate"));
        bookingDates.put("checkout", RandombookingDates_Map.get("Random_checkoutDate"));

        // Send a POST request and get the response
        Response response = given().log().all()
                .contentType(ContentType.JSON)  // Set the Content-Type header to JSON
                .body(bookingData.toString())  // Set the request body
                .when().post("/booking")
                .then()
                .assertThat()
                .log().all()
                .statusCode(200)  // Assuming 200 is the expected status code
                .extract().response();

        assertThat(response.jsonPath().getMap("$"), hasKey("bookingid"));
        response.then().assertThat().body("booking", hasKey("firstname"));
        response.then().assertThat().body("booking", hasKey("lastname"));
        response.then().assertThat().body("booking", hasKey("totalprice"));
        response.then().assertThat().body("booking", hasKey("depositpaid"));
        response.then().assertThat().body("booking.bookingdates", hasKey("checkin"));
        response.then().assertThat().body("booking.bookingdates", hasKey("checkout"));
        response.then().assertThat().body("booking", hasKey("additionalneeds"));
//        how can i assert that we have 3 or maore  objects  that have the  property firstname
//        import static org.hamcrest.Matchers.*
//// Assuming you have an array of objects under the key "bookings"
//        response.then().assertThat().body("bookings.findAll { it.containsKey('firstname') }",
//                hasItems(hasKey("firstname"), hasKey("lastname"), hasKey("totalprice"), hasKey("...")),
//                hasSize(greaterThanOrEqualTo(3)));



    }

//    man iam not checking nullable am check if the response contains the bookingid
//    how to check the response body has the property bookingid for all objects [ { "bookingid": 120 }, { "bookingid": 237 }, { "bookingid": 226 }, { "bookingid": 155 }]

}
