package testcases;

import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;


import static org.hamcrest.Matchers.*;


@Epic("Create Token Functionality")
@Story("Create Token")
@Feature("Create Token Feature")
public class TC01_CreateToken extends TestBase {

    // TODO: positive scenario
    // given ->> request data
    // when ->>  action method
    // then ->>  assertion
    @Description("Create Token with Valid Data")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 1, description = "Create Token with Valid Data")
    public void createTokenWithValidData_P() {
        JSONObject loginData = new JSONObject();

        loginData.put("username", "admin");
        loginData.put("password", "password123");

        Response response = given().log().all()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(loginData.toString())
                .when().post("/auth")
                .then().log().all().statusCode(200).extract().response();
        token = response.jsonPath().getString("token");
        Assert.assertTrue(token.length() >= 6);
        Assert.assertFalse(checkNull(response));
        // check schema , check status code , check status message , check nullable values , check as expected , check data type and undefined , check Uniqueness
        System.out.println("Token Value is " + token);
    }
    @Test(priority = 2, description = "Check schema validation as the property Token ")
    public void checkResponseHasTokenKey() {
        JSONObject loginData = new JSONObject();
        loginData.put("username", "admin");
        loginData.put("password", "password123");

        // Send a POST request and get the response
        Response response = given().log().all()
                .contentType(ContentType.JSON)  // Set the Content-Type header to JSON
                .body(loginData.toString())  // Set the request body
                .when()
                .post("/auth")
                .then().log().all()
                .statusCode(200)  // Assuming 200 is the expected status code
                .extract().response();

        // Check that the response body has the "token" key
        // response.then().body("$", hasKey("token"));
        // Hard assertion: Check that the response body has the "token" key
        assertThat(response.jsonPath().getMap("$"), hasKey("token"));
    }




    private boolean checkNull(Response response){
        return response.jsonPath().getString("token").isEmpty();
    }

    @Test(priority = 3, description = "Create Token with Invalid Username")
    public void createTokenWithInvalidData_N() {
        JSONObject loginData = new JSONObject();

        loginData.put("username", "admin123");
        loginData.put("password", "password123");

        Response response = given().log().all()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(loginData.toString())
                .when().post("/auth")
                .then().log().all().statusCode(200).extract().response();
        Assert.assertTrue(response.jsonPath().getString("reason").equals("Bad credentials"));
    }
    @Test(priority = 4, description = "Create Token with Invalid Password")
    public void createTokenWithInvalidPassword_N() {
        JSONObject loginData = new JSONObject();

        loginData.put("username", "admin");
        loginData.put("password", "khkhkj");

        Response response = given().log().all()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(loginData.toString())
                .when().post("/auth")
                .then().log().all().statusCode(200).extract().response();
        Assert.assertTrue(response.jsonPath().getString("reason").equals("Bad credentials"));
    }
    @Test(priority = 5,description = "Check Request with Invalid method")
    public void checkRequestWithInvalidMethod(){
        JSONObject loginData = new JSONObject();

        loginData.put("username", "admin");
        loginData.put("password", "password123");

        Response response = given().log().all()//.auth().basic("","")
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(loginData.toString())
                .when().put("/auth")
                .then().log().all().statusCode(405).extract().response();
    }
    @Test(priority = 6,description = "Check Request with Invalid path")
    public void checkRequestWithInvalidPAth(){
        JSONObject loginData = new JSONObject();

        loginData.put("username", "admin");
        loginData.put("password", "password123");

        Response response = given().log().all()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(loginData.toString())
                .when().post("/autH")
                .then().log().all().statusCode(404).extract().response();
    }

    @Test(priority = 7,description = "Check Request with Invalid Query Parameters")
    public void checkRequestWithInvalidQueryParameter(){}

    @Test(priority = 8, description = "Check Request With Invalid Authorization")
    public void checkRequestWithInvalidAuth(){}
}