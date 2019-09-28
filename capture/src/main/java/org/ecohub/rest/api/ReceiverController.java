
package org.ecohub.rest.api;


import org.ecohub.rest.api.data.Area;
import org.ecohub.rest.model.Location;
import org.ecohub.rest.model.Receiver;
import org.ecohub.rest.model.TrashOperation;
import org.ecohub.rest.service.GeoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Get a location point in a certain area;
 * Conquer a location point;
 * Show your score;
 *
 * @startuml
 * client -> server : /request \nTrashOperationRequest
 * server -> client : TrashStatus
 * client -> receiver : put trash
 * receiver -> server : /confirmByReceiver \nTrashOperationConfirm
 * client -> server : /statusOperation \nOperationStatusRequest
 * server -> client : TrashStatus
 * === Without confirmation ==
 * client -> server : /request \nTrashOperationRequest
 * server -> client : TrashStatus
 * client -> receiver : put trash
 *
 * @enduml
 */
@Component
@RestController
public class ReceiverController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GeoService geoService;


    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public List<TrashOperation> getHistory(Long clientId) {
        return geoService.getHistory(clientId);
    }


    @RequestMapping(value = "/findReceiver", method = RequestMethod.POST)
    public List<Receiver> findReceivers(@Valid @RequestBody Area area) {
        logger.debug("Get location with area : {}", area);
        return geoService.getReceivers(area);
    }

    @RequestMapping(value = "/findReceiver/{fromLong}/{toLong}/{fromLat}/{toLat}", method = RequestMethod.GET)
    public List<Receiver> getReceiver(@PathVariable Double fromLong,
                                      @PathVariable Double toLong,
                                      @PathVariable Double fromLat,
                                      @PathVariable Double toLat) {
        Area area = new Area();
        area.setFrom(new Location(fromLong, fromLat));
        area.setTo(new Location(toLong, toLat));
        logger.debug("Get location with area : {}", area);
        return geoService.getReceivers(area);
    }




}