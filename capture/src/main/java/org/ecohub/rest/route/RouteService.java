package org.ecohub.rest.route;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.ecohub.rest.model.Location;
import org.ecohub.rest.route.api.RouteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Denis B. Kulikov<br/>
 * date: 28.09.2019:16:35<br/>
 */
@Service
public class RouteService {
    private static Logger logger = LoggerFactory.getLogger(RouteService.class);
    private static final String URL_TEMPLATE = "https://route.api.here.com/routing/7.2/calculateroute.json" +
            "?app_id=5e1we9cQyN3Fw9DgKj0N" +
            "&app_code=ErXbohenf21dJzCSdDioMw" +
            "&waypoint0=geo!{FROM_LAT},{FROM_LONG}&waypoint1=geo!{TO_LAT},{TO_LONG}" +
            "&mode=fastest;car";

    private RestTemplate restTemplate = new RestTemplate();

    public List<Location> getRoute(Location from, Location to) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String route = getRoute(from.getLatitude(), from.getLongitude(), to.getLatitude(), to.getLongitude());
        IOUtils.toInputStream(route,  Charset.defaultCharset());
        try {
            RouteResponse routeResponse = mapper.readValue(new StringReader(route), RouteResponse.class);
            RouteResponse.Route detailRoute = routeResponse.getResponse().getRoute().get(0);
            RouteResponse.Leg leg = detailRoute.getLeg().get(0);
            return leg.getManeuver().stream()
                    .map(RouteResponse.Maneuver::getPosition)
                    .map(p -> new Location(p.getLongitude(), p.getLatitude())).collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("Error in find route" + from + to, e);
        }
        return Collections.emptyList();
    }

    private String getRoute(double fromLat, double fromLong, double toLat, double toLong) {
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        // Request to return JSON format
        headers.setContentType(MediaType.APPLICATION_JSON);
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

