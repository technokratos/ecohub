package org.ecohub.rest.service.impl;

import org.ecohub.rest.model.Area;
import org.ecohub.rest.model.Receiver;
import org.ecohub.rest.model.TrashOperation;
import org.ecohub.rest.service.GeoService;
import org.ecohub.rest.service.conditional.SingleCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Denis B. Kulikov<br/>
 * date: 23.08.2019:19:43<br/>
 */
@Service
@Conditional(SingleCondition.class)
public class MemoryGeoServiceImpl implements GeoService {

    private static Logger logger = LoggerFactory.getLogger(MemoryGeoServiceImpl.class);

    @Autowired
    @Qualifier("initReceiverCollection")
    private List<Receiver> receivers;

    private Map<String, List<TrashOperation>> clientOperationMap;

    @Override
    public List<Receiver> getReceivers(Area area) {
        return receivers.stream()
                .filter(receiver -> receiver.getLocation().between(area.getFrom(), area.getTo()))//todo it isn't best
                .collect(Collectors.toList());
    }

    @Override
    public List<TrashOperation> getHistory(String clientId) {
        return clientOperationMap.get(clientId);
    }

    @Override
    public void addOperation(String clientId, TrashOperation trashOperation) {
        List<TrashOperation> trashOperations = clientOperationMap.computeIfAbsent(clientId, s -> new ArrayList<>());
        trashOperations.add(trashOperation);
    }


}
