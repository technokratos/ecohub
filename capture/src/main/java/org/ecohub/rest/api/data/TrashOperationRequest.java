package org.ecohub.rest.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.ecohub.rest.model.Location;

/**
 * @author Denis B. Kulikov<br/>
 * date: 28.09.2019:0:57<br/>
 */
@Data
@AllArgsConstructor
public class TrashOperationRequest {
    private final Long  clientId;
    private final Long receiverId;
    private final Location location;
    private final String type;

}
