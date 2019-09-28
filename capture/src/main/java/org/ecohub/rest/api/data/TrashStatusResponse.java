package org.ecohub.rest.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.ecohub.rest.model.TrashStatus;

/**
 * @author Denis B. Kulikov<br/>
 * date: 28.09.2019:11:19<br/>
 */
@Data
@AllArgsConstructor
public class TrashStatusResponse {
    private final TrashStatus status;
}
