package model;

public class GetCreateBookingBody {

    public static String getCreateBookingBody(String firstName,String lastName,int totalPrice
    , boolean depositeID,String checkInDate,String checkOutDate,String additionalneeds_meels){
        return "{\n" +
                "    \"firstname\" : \""+firstName+"\",\n" +
                "    \"lastname\" : \""+lastName+"\",\n" +
                "    \"totalprice\" : "+totalPrice+",\n" +
                "    \"depositpaid\" : "+depositeID+",\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \""+checkInDate+"\",\n" +
                "        \"checkout\" : \""+checkOutDate+"\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \""+additionalneeds_meels+"\"\n" +
                "}";
        }
}
