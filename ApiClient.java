package restutils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

public class ApiClient {
    public Response postReq(String endpoint, String payload, Map<String, String> headers) {
        return RestAssured
                .given()
                .headers(headers)
                .body(payload)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    public Response getReq(String endpoint, Map<String, String> headers) {
        return RestAssured
                .given()
                .headers(headers)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }
}
