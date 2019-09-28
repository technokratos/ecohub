package org.ecohub.rest.route.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Denis B. Kulikov<br/>
 * date: 28.09.2019:16:09<br/>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponse {
    private Response response;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        List<Route> route;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Route {
        private List<Leg> leg;
        private Summary summary;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Leg {
        private List<Maneuver> maneuver;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Maneuver {
        private Position position;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Position {
        private double latitude;
        private double longitude;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Summary {
        Long distance;
        Long trafficTime;
        Long baseTime;

    }
}
