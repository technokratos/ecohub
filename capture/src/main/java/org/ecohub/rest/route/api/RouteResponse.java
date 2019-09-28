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
public class RouteResponse {
    private Response response;

    @Data
    @NoArgsConstructor
    public static class Response {
        Route route;
    }

    @Data
    @NoArgsConstructor
    public static class Route {
        private Leg leg;
        private Summary summary;

    }

    @Data
    @NoArgsConstructor
    public static class Leg {
        private List<Maneuver> maneuver;
    }

    @Data
    @NoArgsConstructor
    public static class Maneuver {
        private Position position;
    }

    @Data
    @AllArgsConstructor
    public static class Position {
        private final double latitude;
        private final double longitude;
    }

    @Data
    @NoArgsConstructor
    public static class Summary {
        Long distance;
        Long trafficTime;
        Long baseTime;

    }
}
