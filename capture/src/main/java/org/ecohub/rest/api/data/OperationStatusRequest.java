package org.ecohub.rest.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Denis B. Kulikov<br/>
 * date: 28.09.2019:7:45<br/>
 */
@Data
@AllArgsConstructor
public class OperationStatusRequest {
    private final Long  clientId;
    private final Long receiverId;
}
