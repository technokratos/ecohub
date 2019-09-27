package org.ecohub.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Denis B. Kulikov<br/>
 * date: 27.09.2019:20:15<br/>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receiver {
    private Long id;

    private ReceiverType type;
    private double load;

    private Location location;
    private List<String> trashTypes;
}
