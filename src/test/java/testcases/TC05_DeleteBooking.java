package testcases;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static utilities.Utility.*;
import static io.restassured.RestAssured.given;

public class TC05_DeleteBooking extends TestBase{

    @Test(priority = 1, description = "Delete Booking by only one ID With Valid Data")
    public void Delete_Booking_ByBookingID_WithValidData_P() {
        Response response = given().log().all()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .cookie("token",token) // Add your token variable and its value here
                .when().delete("/booking/" + Response_bookingID)
                .then().log().all().statusCode(201).extract().response();

        // Handle the response as needed, e.g., validate the response content, status code, etc.
        System.out.println("token=" + token);
    }


}
