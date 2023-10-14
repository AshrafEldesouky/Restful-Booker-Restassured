package testcases;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import io.restassured.response.Response;
//import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.FileAssert.fail;
import static testcases.TestBase.Response_bookingID;
import static utilities.Utility.*;
import static utilities.Utility.generateRandomBookingDates;

public class TC04_GetBooking {

    //This class include Get Method requests ( GetAll or get by single parameter )
    @Test(priority = 1, description = "Get All Booking ID's With Valid Data")
    public void GetALLBookingWithValidData_P() {
        Response response = given().log().all()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .when().get("/booking")
                .then().log().all().statusCode(200).extract().response();
    }

    @Test(priority = 2, description = "Get Booking by only one ID With Valid Data")
    public void GetBooking_ByBookingID_WithValidData_P() {
        Response response = given().log().all()
                .filter(new AllureRestAssured())
                .header("Content-Type", "application/json")
                .when().get("/booking/" + Response_bookingID)
                .then().log().all().statusCode(200).extract().response();

        System.out.println("Response_bookingID=" + Response_bookingID);
    }

    @Test(priority = 3, description = "CheckTheSchemaHasTheBookingID")
    public void checkTheSchemaHasTheBookingID_P() {

        }

    }

// response.then().assertThat()
//         .body("every { it.containsKey('bookingid') }", is(true));