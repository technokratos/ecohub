package org.ecohub.rest.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author Denis B. Kulikov<br/>
 * date: 28.09.2019:0:58<br/>
 */
@Data
@AllArgsConstructor
public class TrashOperationСonfirm {
    private final Long boxId;
    private final String type;
    private final Double weight;
}
