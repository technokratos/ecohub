package org.ecohub.rest.api.data;

import org.ecohub.rest.api.validation.AreaAnnotation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ecohub.rest.model.Location;
import org.ecohub.rest.model.ReceiverType;

/**
 * @author Denis B. Kulikov<br/>
 * date: 25.08.2019:0:02<br/>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@AreaAnnotation(message = "'From' should be lesser then 'To'")
public class Area {
    private Location from;
    private Location to;
    ReceiverType receiverType;
    private String trashType;
}
