package org.ecohub.rest;
 
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class SimplestGetExample {


    /**
     *
     * APP ID
     * 5e1we9cQyN3Fw9DgKj0N
     * APP CODE
     * ErXbohenf21dJzCSdDioMw
     *
     *
     * js key 1wcU5X7XGrnflY3kVyyf
     *
     * app_id
     * A 20 bytes Base64 URL-safe encoded string used for the authentication of the client application.
     *
     * You must include an app_id with every request. To get an app_id assigned to you, please see Acquiring Credentials.
     *
     * app_code
     * A 20 bytes Base64 URL-safe encoded string used for the authentication of the client application.
     *
     * Y
     *
     *
     */
//    ../routing/7.2/getroute.{format}?routeId=<ROUTEID>&<parameter>=<value>...
    //static final String URL_EMPLOYEES = "https://route.api.here.com/routing/7.2/getroute.json?app_id=5e1we9cQyN3Fw9DgKj0N&app_code=ErXbohenf21dJzCSdDioMw";
    static final String URL_TEMPLATE = "https://route.api.here.com/routing/7.2/calculateroute.json" +
            "?app_id=5e1we9cQyN3Fw9DgKj0N" +
            "&app_code=ErXbohenf21dJzCSdDioMw" +
            "&waypoint0=geo!{FROM_LAT},{FROM_LONG}&waypoint1=geo!{TO_LAT},{TO_LONG}" +
            "&mode=fastest;car";
 
    static final String URL_EMPLOYEES_XML = "http://localhost:8080/employees.xml";
    static final String URL_EMPLOYEES_JSON = "http://localhost:8080/employees.json";
    private static RestTemplate restTemplate;

    public static void main(String[] args) {

        restTemplate = new RestTemplate();
 
        // Send request with GET method and default Headers.
        //String result = restTemplate.getForObject(URL_EMPLOYEES, String.class);


        /**
         * 52.5,13.4
         * &waypoint1=geo!52.5,13.45
         */
        double fromLat = 52.5;
        double fromLong = 13.4;
        double toLat = 52.5;
        double toLong = 13.45;
        String result1 = getRoute(fromLat, fromLong, toLat, toLong);

        System.out.println(result1);
    }

    private static String getRoute(double fromLat, double fromLong, double toLat, double toLong) {
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        // Request to return JSON format
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HttpEntity<String>: To get result as String.
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String routeURL = URL_TEMPLATE
                .replace("{FROM_LAT}", Double.toString(fromLat))
                .replace("{FROM_LONG}", Double.toString(fromLong))
                .replace("{TO_LAT}", Double.toString(toLat))
                .replace("{TO_LONG}", Double.toString(toLong));

        ResponseEntity<String> response = restTemplate.exchange(routeURL, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

}