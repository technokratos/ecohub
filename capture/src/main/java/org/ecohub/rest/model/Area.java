package org.ecohub.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ecohub.rest.api.validation.AreaAnnotation;

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
