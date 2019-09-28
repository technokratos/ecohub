package org.ecohub.rest.api;

import org.apache.commons.lang3.tuple.Pair;
import org.ecohub.rest.api.data.Area;
import org.ecohub.rest.api.data.Route;
import org.ecohub.rest.model.Location;
import org.ecohub.rest.model.Receiver;
import org.ecohub.rest.route.RouteService;
import org.ecohub.rest.service.GeoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Denis B. Kulikov<br/>
 * date: 28.09.2019:13:35<br/>
 */
@Component
@RestController
public class RouteController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GeoService geoService;

    @Autowired
    private RouteService routeService;

    @RequestMapping(value = "/insertInRoute", method = RequestMethod.POST    )
    public Route findRoute(@RequestBody Route route) {
        if (route == null || route.getPoints().size() == 1) {
            logger.warn("Empty params in query");
            throw new IllegalStateException("Empty params in query");
        }
        double minLong = route.getPoints().stream().min((o1, o2) -> (int) Math.signum(o1.getLongitude() - o2.getLongitude())).get().getLongitude();
        double minLat = route.getPoints().stream().min((o1, o2) -> (int) Math.signum(o1.getLongitude() - o2.getLongitude())).get().getLatitude();

        double maxLong = route.getPoints().stream().max((o1, o2) -> (int) Math.signum(o1.getLongitude() - o2.getLongitude())).get().getLongitude();
        double maxLat = route.getPoints().stream().max((o1, o2) -> (int) Math.signum(o1.getLongitude() - o2.getLongitude())).get().getLatitude();

        double dLong = maxLong - minLong;
        double dLat = maxLat - minLat;
        double radius = Math.sqrt(dLong * dLong + dLat * dLat);
        double midLong = (maxLong + minLong)/2;
        double midLat = (maxLat + minLat)/2;
        Location from = new Location(midLong - radius, midLat - radius);
        Location to= new Location(midLong + radius, midLat + radius);
        Area area = new Area();
        area.setFrom(from);
        area.setTo(to);
        List<Receiver> receivers = geoService.getReceivers(area);
        if (receivers == null) {
            throw new IllegalStateException("Not found receivers");
        }
        if (receivers.isEmpty()) {
            logger.info("Not found recevier for route" + route);
            return route;
        }

        List<Pair<Route, Double>> routeWithDelta = findRoutesWithDelta(route, receivers);

        Optional<Pair<Route, Double>> min = routeWithDelta.stream().min((o1, o2) -> (int) (o1.getRight() - o2.getRight()));
        if (min.isPresent()) {
            return insertPolylines(min.get().getLeft());
        } else {
            throw new IllegalStateException("Impossible");
        }

    }

    private Route insertPolylines(Route route) {
        List<Location> polylines = new ArrayList<>();
        for (int i = 0; i < route.getPoints().size() - 1; i++) {
            Location current = route.getPoints().get(i);
            Location next = route.getPoints().get(i+1);
            List<Location> subPolylines = routeService.getRoute(current, next);
            if (i< route.getPoints().size() - 1) {
                //remove last to exclude duplicate
                subPolylines.remove(subPolylines.size() -1);
            }
            polylines.addAll(subPolylines);
        }
        route.setPolylines(polylines);
        return route;
    }

    private List<Pair<Route, Double>> findRoutesWithDelta(Route route, List<Receiver> receivers) {
        return receivers.stream().map(receiver -> insertIntoRoute(route, receiver)).collect(Collectors.toList());

    }

    private Pair<Route, Double> insertIntoRoute(Route route, Receiver receiver) {

        Location challenger = receiver.getLocation();
        int indexMinDelta = 0;
        double minDelta = Double.MAX_VALUE;
        for (int i = 0; i < route.getPoints().size() -1; i++) {
            Location current = route.getPoints().get(i);
            Location next = route.getPoints().get(i + 1);
            double edge = current.dist(next);
            double edgeA = current.dist(challenger);
            double edgeB = next.dist(challenger);
            double delta = (edgeA + edgeB) - edge;
            if (delta < minDelta) {
                minDelta = delta;
                indexMinDelta = i;
            }
        }
        //insert after found location
        if (indexMinDelta != route.getPoints().size() - 1) {
            indexMinDelta ++;
        }
        ArrayList<Location> locations = new ArrayList<>(route.getPoints());
        final List<String> titles;
        if (route.getTitles().size() < route.getPoints().size() ) {
            titles = IntStream.range('A', 'Z')
                    .limit(route.getPoints().size())
                    .mapToObj(value -> (char) value)
                    .map(character -> challenger.toString())
                    .collect(Collectors.toList());

        } else {
            titles = route.getTitles();
        }
        locations.add(indexMinDelta, challenger);
        titles.add(indexMinDelta, "BOX");
        return Pair.of(new Route(locations, Collections.emptyList(), titles), minDelta);

    }
}
