package org.ecohub.rest.service.impl;

import org.ecohub.rest.api.data.Area;
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

import java.util.*;
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

    private Map<Long, List<TrashOperation>> clientOperationMap = new HashMap<>();

    @Override
    public List<Receiver> getReceivers(Area area) {
        return receivers.stream()
                .filter(receiver -> receiver.getLocation().between(area.getFrom(), area.getTo()))//todo it isn't best
                .collect(Collectors.toList());
    }

    @Override
    public List<TrashOperation> getHistory(Long clientId) {
        return clientOperationMap.getOrDefault(clientId, Collections.emptyList());
    }

    @Override
    public void addOperation(Long clientId, TrashOperation trashOperation) {
        List<TrashOperation> trashOperations = clientOperationMap.computeIfAbsent(clientId, s -> new ArrayList<>());
        trashOperations.add(trashOperation);
    }

    @Override
    public Receiver getReceiverById(Long trashId) {
        return receivers.stream().filter(receiver -> Objects.equals(trashId,receiver.getId())).findFirst().orElseGet(() -> null);
    }


}
