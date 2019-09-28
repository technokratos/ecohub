package org.ecohub.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Denis B. Kulikov<br/>
 * date: 28.09.2019:11:52<br/>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Box {
    private Long id;
    private double load;
    private List<String> trashTypes;
}
