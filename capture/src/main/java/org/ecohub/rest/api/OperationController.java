
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
import java.util.concurrent.*;


/**
 *
 * @startuml
 * receiver -> server : /statusOperation
 * server -> receiver : UNKNOWN
 * client -> server : /request \nTrashOperationRequest
 * server -> client : UN_CONFIRMED
 * receiver -> server : /statusOperation
 * server -> receiver : UN_CONFIRMED
 * client -> receiver : put trash
 * receiver -> server : /confirmByReceiver \nTrashOperationConfirm
 * client -> server : /statusOperation \nOperationStatusRequest
 * server -> client : CONFIRMED
 * receiver -> server : /statusOperation
 * server -> receiver : UNKNOWN
 * === Without confirmation ==
 * client -> server : /request \nTrashOperationRequest
 * server -> client : TrashStatus
 * client -> receiver : put trash
 *
 * @enduml
 */
@Component
@RestController
public class OperationController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    //todo clear with timeout
    private Map<Long, TrashOperation> clientOperationMap = new ConcurrentHashMap<>();
    private Map<Long, TrashOperation> receiverOperationMap = new ConcurrentHashMap<>();

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
        logger.info(request.toString());
        Receiver receiverById = geoService.getReceiverById(request.getReceiverId());
        Long clientId = request.getClientId();

        if (receiverById == null) {
            logger.info("Not found receiver {}", request);
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
            logger.info("Wait confirm {}", request);
            clientOperationMap.put(clientId, trashOperation);
            receiverOperationMap.put(request.getReceiverId(), trashOperation);
        } else {
            logger.info("Return status in box {}", request);
            TrashClient clientById = geoService.getClientById(clientId);
            clientById.setBalance(clientById.getBalance() + 0.1);
            trashOperation.setStatus(TrashStatus.IN_BOX);
        }

        return new TrashStatusResponse(trashOperation.getStatus());

    }


    @RequestMapping(value = "/confirmByReceiver", method = RequestMethod.POST)
    public TrashStatusResponse confirm(TrashOperationСonfirm confirmer) {
        logger.info(confirmer.toString());
        Optional<Receiver> receiverById = geoService.getReceiverByBoxId(confirmer.getBoxId());
        if (receiverById.isPresent()) {
            Long receiverId = receiverById.get().getId();
            TrashOperation trashOperation = receiverOperationMap.remove(receiverId);
            if (trashOperation != null) {
                trashOperation.setType(confirmer.getType());
                trashOperation.setStatus(TrashStatus.IN_BOX);
                trashOperation.setWeight(confirmer.getWeight());
                trashOperation.setType(confirmer.getType());
                geoService.addOperation(trashOperation.getClientId(), trashOperation);
                return new TrashStatusResponse(TrashStatus.IN_BOX);
            } else {
                logger.info("Not found trash operation {}", confirmer);
                return new TrashStatusResponse(TrashStatus.UNKNOWN);
            }
        } else {
            logger.info("Not found receiver}", confirmer);
            throw new IllegalStateException("Not found Receiver");
        }
    }
    @RequestMapping(value = "/unConfirmByReceiver", method = RequestMethod.POST)
    public TrashStatusResponse unConfirm(TrashOperationСonfirm confirmer) {
        logger.info(confirmer.toString());
        Optional<Receiver> receiverById = geoService.getReceiverByBoxId(confirmer.getBoxId());
        if (receiverById.isPresent()) {
            Long receiverId = receiverById.get().getId();
            TrashOperation trashOperation = receiverOperationMap.remove(receiverId);
            if (trashOperation != null) {
                clientOperationMap.remove(trashOperation.getClientId());
                return new TrashStatusResponse(trashOperation.getStatus());
            } else {
                logger.info("Not found trash operation {}", confirmer);
                return new TrashStatusResponse(TrashStatus.UNKNOWN);
            }
        } else {
            logger.info("Not found receiver}", confirmer);
            throw new IllegalStateException("Not found Receiver");
        }
    }

    @RequestMapping(value = "/statusOperation", method = RequestMethod.POST)
    public TrashStatusResponse requestStatus(OperationStatusRequest statusRequest) {
        TrashOperation trashOperation = null;
        if (statusRequest.getClientId() != null) {
            trashOperation = clientOperationMap.get(statusRequest.getClientId());
        } else if (statusRequest.getReceiverId() != null) {
            Receiver receiverById = geoService.getReceiverById(statusRequest.getReceiverId());
            if (receiverById != null) {
                trashOperation = receiverOperationMap.get(receiverById.getId());
            }
        }
        if (trashOperation != null) {
            return new TrashStatusResponse(trashOperation.getStatus());
        } else {
            return new TrashStatusResponse(TrashStatus.UNKNOWN);
        }
    }

}