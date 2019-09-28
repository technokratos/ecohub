package org.ecohub.rest.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.ecohub.rest.model.Location;

import java.util.List;

/**
 * route: {
 * 	points: [Location],
 * 	polylines : [Location]
 * }
 * @author Denis B. Kulikov<br/>
 * date: 28.09.2019:11:18<br/>
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Route {
    List<Location> points;
    List<Location> polylines;
    List<String> titles;
}
