import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.testng.Assert.assertEquals;

public class TekArchApi {
    String token = null;
    ApiClient api = new ApiClient();

    @Test(priority = 1)
    public void loginTest() {
        RestAssured.baseURI = "https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net/";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        String payload = "{\"username\": \"mithun@ta.com\", \"password\": \"mithun\"}";
        Response res = api.postReq("login", payload, headers);
        String actualUserId = res.jsonPath().getString("[0].userid");
        assertEquals(actualUserId, "taGX4XQQfXD3Z76sI1Xn");
        token = res.jsonPath().getString("[0].token");
        System.out.println("Token: " + token);
    }

    @Test(priority = 2, dependsOnMethods = "loginTest")
    public void getUsersTest() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("token", token);
        Response res = api.getReq("getdata", headers);
        assertEquals(res.getStatusCode(), 200);
        res.prettyPrint();
    }

    @Test(priority = 3, dependsOnMethods = "loginTest")
    public void addUsersTest() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("token", token);
        String payload = "{\"accountno\": \"67983726-TA\", \"departmentno\": \"7\", \"salary\": \"68799\", \"pincode\": \"689843\"}";
        Response res = api.postReq("addData", payload, headers);
        assertEquals(res.getStatusCode(), 201);
        res.prettyPrint();
    }
}
