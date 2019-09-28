package org.ecohub.rest.service;

import org.ecohub.rest.api.data.Area;
import org.ecohub.rest.model.Receiver;
import org.ecohub.rest.model.TrashClient;
import org.ecohub.rest.model.TrashOperation;

import java.util.List;
import java.util.Optional;

/**
 * @author Denis B. Kulikov<br/>
 * date: 24.08.2019:19:40<br/>
 */
public interface GeoService {
    List<Receiver> getReceivers(Area area);

    List<TrashOperation> getHistory(Long clientId);

    void addOperation(Long clientId, TrashOperation trashOperation);

    Receiver getReceiverById(Long trashId);

    TrashClient getClientById(Long id);

    Optional<Receiver> getReceiverByBoxId(Long boxId);
}
