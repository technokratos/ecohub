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
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private double latitude;
    private double longitude;

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
}
