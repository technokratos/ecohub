package org.ecohub.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Denis B. Kulikov<br/>
 * date: 28.09.2019:10:58<br/>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrashClient {

    private Long id;
    private Double balance;

}
