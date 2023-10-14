package testcases;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.restassured.RestAssured;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestBase {

    public static String token=null;
    public static int Response_bookingID=0;

    // extend report
    protected static ExtentSparkReporter htmlReporter;
    protected static ExtentReports extent;
    protected static ExtentTest test;
    private static String PROJECT_NAME = null;

    // define Suite Elements
    static Properties prop;
    static FileInputStream readProperty;

    @BeforeSuite
    public void defineSuiteElements() throws IOException {

        // initialize the HtmlReporter
        htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/testReport.html");

        //initialize ExtentReports and attach the HtmlReporter
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        setProjectDetails();

        // initialize test
        test = extent.createTest(PROJECT_NAME + " Test Automation Project");

        //configuration items to change the look and fee add content, manage tests etc
        htmlReporter.config().setDocumentTitle(PROJECT_NAME + " Test Automation Report");
        htmlReporter.config().setReportName(PROJECT_NAME + " Test Report");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
    }
    private void setProjectDetails() throws IOException {
        // TODO: Step1: define object of properties file
        readProperty = new FileInputStream(
                System.getProperty("user.dir") + "/src/test/resources/properties/environment.properties");
        prop = new Properties();
        prop.load(readProperty);

        // define project name from properties file
        PROJECT_NAME = prop.getProperty("projectName");
    }

    @BeforeTest
    public void setup(){
        RestAssured.baseURI="https://restful-booker.herokuapp.com";
    }

    @AfterSuite
    public void tearDown() throws IOException {
        extent.flush();
    }
    @AfterMethod
    public void getResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, result.getName() + " failed with the following error: " + result.getThrowable());
            Reporter.log("Failed to perform "+result.getName(), 10, true);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, result.getName());
            Reporter.log("Successfully perform "+result.getName(), 10, true);
        } else {
            test.log(Status.SKIP, result.getName());
            Reporter.log("Skip "+result.getName(), 10, true);
        }
    }

}
