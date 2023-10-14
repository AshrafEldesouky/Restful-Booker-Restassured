package testcases;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static utilities.Utility.*;
import static utilities.Utility.generateRandomBookingDates;

public class TC03_UpdateBooking extends TestBase {
    //This class include Update request ( Put  and Patch Methods )

    @Test(priority = 1,description = "Update Put Booking With Valid Data")
    public void Update_PUT_BookingWithValidData_P(){
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

        Response response = given().log().all()
//               .header("","")
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(bookingData.toString())
                .cookie("token", token) // Add your token variable and its value here
                .when().put("/booking/"+Response_bookingID)
                .then().log().all().statusCode(200).extract().response();

    }
    @Test(priority = 1,description = "Update Patch Booking With Valid Data")
    public void Update_Patch_BookingWithValidData_P(){
        JSONObject bookingData = new JSONObject();
        JSONObject bookingDates = new JSONObject();
        bookingData.put("firstname",generateRandomFirstName());
        bookingData.put("lastname", generateRandomLastName());
        Response response = given().log().all()
//               .header("","")
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(bookingData.toString())
                .cookie("token", token) // Add your token variable and its value here
                .when().patch("/booking/"+Response_bookingID)
                .then().log().all().statusCode(200).extract().response();

    }
}
