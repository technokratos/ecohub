package org.ecohub.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * @author Denis B. Kulikov<br/>
 * date: 27.09.2019:19:24<br/>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrashOperation {
    private String id;
    private String clientId;
    private ZonedDateTime time;
    private TrashStatus status;
    private Long latitude;
    private Long longitude;
    private double weight;
    private String type;
}
