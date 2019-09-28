package org.ecohub.rest.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ecohub.rest.model.Receiver;

import java.util.List;

/**
 * @author Denis B. Kulikov<br/>
 * date: 27.09.2019:20:55<br/>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiverCollection {
    private List<Receiver> receivers;
}
