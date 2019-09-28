package org.ecohub.rest.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Denis B. Kulikov<br/>
 * date: 28.09.2019:19:53<br/>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Routes {
    private List<Route> routes;
}
