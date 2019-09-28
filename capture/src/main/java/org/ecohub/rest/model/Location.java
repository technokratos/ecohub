package org.ecohub.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.lucene.util.SloppyMath;

/**
 * @author Denis B. Kulikov<br/>
 * date: 23.08.2019:19:38<br/>
 */
@Data
@AllArgsConstructor
public class Location {
    private final double longitude;
    private final double latitude;

    public Location() {
        longitude = 0;
        latitude = 0;
    }

    public boolean between(Location fromPoint, Location toPoint) {
        return longitude>= fromPoint.longitude &&
                latitude >= fromPoint.latitude &&
                longitude<= toPoint.longitude &&
                latitude <= toPoint.latitude;
    }

    public boolean near(Location basePoint, int conquerRadius) {
        double dist = SloppyMath.haversinMeters(latitude, longitude, basePoint.latitude, basePoint.longitude);
        return  dist <= conquerRadius;
    }

    public Location plus(double v) {
        return new Location(longitude + v, latitude + v);
    }

    public double dist(Location next) {
        double dLong = next.getLongitude() - longitude;
        double dLat = next.getLatitude() - latitude;
        return Math.sqrt(dLong * dLong + dLat * dLat);
    }
}
