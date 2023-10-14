package utilities;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Calendar;
import java.text.SimpleDateFormat;
public class Utility {

    public static String generateRandomFirstName() {
        String[] firstNames = {
                "Alice",
                "Bob",
                "Carol",
                // ...
        };
        int randomIndex = (int) (Math.random() * firstNames.length);
        return firstNames[randomIndex];
    }

    public static String generateRandomLastName() {
        String[] lastNames = {
                "Smith",
                "Johnson",
                "Williams",
                // ...
        };
        int randomIndex = (int) (Math.random() * lastNames.length);
        return lastNames[randomIndex];
    }
    public static int generateRandomTotalPrice() {
        Random random = new Random();
        int totalPrice = random.nextInt(1000) + 1;
        return totalPrice;
    }
    public static boolean generateRandomDepositPaid() {
        Random random = new Random();
        boolean depositPaid = random.nextBoolean();
        return depositPaid;
    }
    public static String generateRandomBookingDate() {
        Random random = new Random();
        int dayOfYear = random.nextInt(365) + 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String bookingDate = simpleDateFormat.format(calendar.getTime());
        return bookingDate;
    }
    public static String generateRandomadditionalneeds()
    {
        String[] additionalneeds = {"breakfast", "lunch", "dinner"};
        // Create a Random object
        Random random = new Random();
        // Generate a random index to select a meal from additionalneeds array
        int randomIndex = random.nextInt(additionalneeds.length);
        // Print the random meal
        return additionalneeds[randomIndex];
    }
    public static Map<String, LocalDate> generateRandomBookingDates() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        Random random = new Random();
        // Generate a random check-in date
        LocalDate randomCheckinDate = startDate.plusDays(random.nextInt((int) (endDate.toEpochDay() - startDate.toEpochDay() + 1)));
        // Generate a random check-out date, but make sure it is greater than the check-in date
        LocalDate randomCheckoutDate = randomCheckinDate.plusDays(random.nextInt((int) (endDate.toEpochDay() - randomCheckinDate.toEpochDay() + 1)));
        Map<String, LocalDate> Random_bookingDates = new HashMap<>();
        Random_bookingDates.put("Random_checkinDate", randomCheckinDate);
        Random_bookingDates.put("Random_checkoutDate", randomCheckoutDate);
        return Random_bookingDates;
    }
    public static String getSingleJsonData(String jsonFilePath,String jsonField) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();

        FileReader fileReader = new FileReader(jsonFilePath);
        Object obj = jsonParser.parse(fileReader);

        JSONObject jsonObject = (JSONObject) obj;
        return jsonObject.get(jsonField).toString();
    }
    public static String getExcelData(int RowNum, int ColNum, String SheetName) {
        XSSFWorkbook workBook;
        XSSFSheet sheet;
        String projectPath = System.getProperty("user.dir");
        String cellData = null;
        try {
            workBook = new XSSFWorkbook(projectPath + "/src/test/resources/data/DataApitesting.xlsx");
            sheet = workBook.getSheet(SheetName);
            cellData = sheet.getRow(RowNum).getCell(ColNum).getStringCellValue();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }
        return cellData;
    }

}
