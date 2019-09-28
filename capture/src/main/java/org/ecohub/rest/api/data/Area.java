package org.ecohub.rest.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ecohub.rest.api.validation.AreaAnnotation;
import org.ecohub.rest.model.Location;
import org.ecohub.rest.model.ReceiverType;

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
