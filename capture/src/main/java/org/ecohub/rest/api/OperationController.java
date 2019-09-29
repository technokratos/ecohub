
package org.ecohub.rest.api;


import org.ecohub.rest.api.data.OperationStatusRequest;
import org.ecohub.rest.api.data.TrashOperationRequest;
import org.ecohub.rest.api.data.TrashOperationСonfirm;
import org.ecohub.rest.api.data.TrashStatusResponse;
import org.ecohub.rest.model.Receiver;
import org.ecohub.rest.model.TrashClient;
import org.ecohub.rest.model.TrashOperation;
import org.ecohub.rest.model.TrashStatus;
import org.ecohub.rest.service.GeoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Component
@RestController
public class OperationController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //todo clear with timeout
    private Map<Long, TrashOperation> clientOperationMap = new ConcurrentHashMap<>();
    private Map<Long, TrashOperation> currentOperationInReceiverMap = new ConcurrentHashMap<>();

    @Autowired
    private GeoService geoService;

    /**
     * 40 008,55 кь = 360
     * 1 m - ?
     *
     * @param request
     * @return
     */

    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public TrashStatusResponse request(TrashOperationRequest request) {
        Receiver receiverById = geoService.getReceiverById(request.getReceiverId());
        Long clientId = request.getClientId();

        if (receiverById == null) {
            throw new IllegalStateException("Not found receiver");
//            Location location = request.getLocation();
//            Location from  = location.plus(-R15M);
//            Location to = location.plus(R15M);
//            List<Receiver> receivers = geoService.getReceivers(new Area(from, to, null, null));
//            if (receivers.isEmpty()) {
//                throw new IllegalStateException("Not found receiver");
//            }
//            receiverById = receivers.get(0);
        }

        TrashOperation trashOperation = new TrashOperation(null, clientId, receiverById.getId(), ZonedDateTime.now(), TrashStatus.UN_CONFIRMED, receiverById.getLocation(), 0.0, request.getType());

        if (receiverById.isCanConfirm()) {
            clientOperationMap.put(receiverById.getId(), trashOperation);
            currentOperationInReceiverMap.put(request.getClientId(), trashOperation);
        } else {
            trashOperation.setStatus(TrashStatus.IN_BOX);
        }

        return new TrashStatusResponse(trashOperation.getStatus());

    }


    @RequestMapping(value = "/confirmByReceiver", method = RequestMethod.POST)
    public TrashStatusResponse confirm(TrashOperationСonfirm confirmer) {

        Optional<Receiver> receiverById = geoService.getReceiverByBoxId(confirmer.getBoxId());
        if (receiverById.isPresent()) {
            TrashOperation trashOperation = currentOperationInReceiverMap.remove(receiverById.get().getId());
            if (trashOperation != null) {
                trashOperation.setType(confirmer.getType());
                trashOperation.setStatus(TrashStatus.IN_BOX);
                geoService.addOperation(trashOperation.getClientId(), trashOperation);
                return new TrashStatusResponse(TrashStatus.IN_BOX);
            } else {
                return new TrashStatusResponse(TrashStatus.UNKNOWN);
            }
        } else {
            throw new IllegalStateException("Not found Receiver");
        }
    }

    @RequestMapping(value = "/statusOperation", method = RequestMethod.POST)
    public TrashStatusResponse requestConfirm(OperationStatusRequest statusRequest) {
        TrashOperation trashOperation = null;
        if (statusRequest.getClientId() != null) {
            trashOperation = clientOperationMap.get(statusRequest.getClientId());
        } else if (statusRequest.getReceiverId() != null) {
            Receiver receiverById = geoService.getReceiverById(statusRequest.getReceiverId());
            if (receiverById != null) {
                trashOperation = clientOperationMap.get(receiverById.getId());
                if (trashOperation != null) {
                    trashOperation.setStatus(TrashStatus.IN_BOX);
                    TrashClient clientById = geoService.getClientById(trashOperation.getClientId());
                    clientById.setBalance(clientById.getBalance() + trashOperation.getWeight());
                }
            }
            //todo store in db and in cache


        }
        if (trashOperation != null) {
            return new TrashStatusResponse(trashOperation.getStatus());
        } else {
            return new TrashStatusResponse(TrashStatus.UNKNOWN);
        }
    }

}