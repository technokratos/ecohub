package org.ecohub.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecohub.rest.route.api.RouteResponse;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Arrays;

/**
 * @author Denis B. Kulikov<br/>
 * date: 28.09.2019:18:38<br/>
 */
public class ReouteTest {

    @Test
    public void test() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        File file = ResourceUtils.getFile("classpath:here.json");

        RouteResponse response = new RouteResponse(new RouteResponse.Response(
                Arrays.asList(new RouteResponse.Route(
                                Arrays.asList(
                                        new RouteResponse.Leg(Arrays.asList(new RouteResponse.Maneuver(new RouteResponse.Position(1.0, 1.0))))),
                                new RouteResponse.Summary(1L, 2L, 3L)
                        )
                )));
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, response);
        writer.toString();
        RouteResponse routeResponse = mapper.readValue(new FileInputStream(file), RouteResponse.class);
        System.out.println(routeResponse);
    }
}
