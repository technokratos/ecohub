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
    private Long id;
    private Long clientId;
    private Long receiverId;
    private ZonedDateTime time;
    private TrashStatus status;
    private Location location;
    private double weight;
    private String type;
}
